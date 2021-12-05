<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="The name of the task" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="Please enter a task name"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The task group name" prop="jobGroup">
        <el-select
          v-model="queryParams.jobGroup"
          placeholder="Please specify the task group name"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Execution status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Select the execution status"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="The execution time">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Start date"
          end-placeholder="End date"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['monitor:job:remove']"
        >delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:job:remove']"
        >empty</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:job:export']"
        >export</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="jobLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="The log number" width="80" align="center" prop="jobLogId" />
      <el-table-column label="The name of the task" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="The task group name" align="center" prop="jobGroup" :formatter="jobGroupFormat" :show-overflow-tooltip="true" />
      <el-table-column label="Calling the target string" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
      <el-table-column label="Log information" align="center" prop="jobMessage" :show-overflow-tooltip="true" />
      <el-table-column label="Execution status" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="The execution time" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['monitor:job:query']"
          >detailed</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- Scheduling Log Details -->
    <el-dialog title="Scheduling Log Details" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="The log sequence number:">{{ form.jobLogId }}</el-form-item>
            <el-form-item label="The name of the task:">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Task group:">{{ form.jobGroup }}</el-form-item>
            <el-form-item label="The execution time:">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="A method is called:">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Log information:">{{ form.jobMessage }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Execution status:">
              <div v-if="form.status == 0">normal</div>
              <div v-else-if="form.status == 1">failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Exception information:" v-if="form.status == 1">{{ form.exceptionInfo }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">guan closed</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJobLog, delJobLog, exportJobLog, cleanJobLog } from "@/api/monitor/jobLog";

export default {
  name: "JobLog",
  data() {
    return {
      // The mask layer
      loading: true,
      // Select the array
      ids: [],
      // Non-multiple disable
      multiple: true,
      // The total number of article
      total: 0,
      // Schedule log table data
      jobLogList: [],
      // Whether to display the pop-up layer
      open: false,
      // Date range
      dateRange: [],
      // The form parameter
      form: {},
      // Execution state dictionary
      statusOptions: [],
      // Task group name dictionary
      jobGroupOptions: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_job_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_job_group").then(response => {
      this.jobGroupOptions = response.data;
    });
  },
  methods: {
    /** Example Query the scheduling log list */
    getList() {
      this.loading = true;
      listJobLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.jobLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Perform state dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Task group name dictionary translation
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup);
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobLogId);
      this.multiple = !selection.length;
    },
    /** Detailed button operation */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** Delete button operation */
    handleDelete(row) {
      const jobLogIds = this.ids;
      this.$confirm('Confirm whether to delete scheduling logs"' + jobLogIds + '"Data item?', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return delJobLog(jobLogIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        }).catch(function() {});
    },
    /** Clear button operation */
    handleClean() {
      this.$confirm("Determine whether to clear all scheduling log data items?", "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return cleanJobLog();
        }).then(() => {
          this.getList();
          this.msgSuccess("Empty the success");
        }).catch(function() {});
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("Determine whether to export all scheduling log data items?", "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return exportJobLog(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>