<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="The name of the task" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="Please enter a task name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The task group name" prop="jobGroup">
        <el-select v-model="queryParams.jobGroup" placeholder="Select a task group name" clearable size="small">
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Task status" prop="status">
        <el-select v-model="queryParams.status" placeholder="Select task status" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['monitor:job:add']"
        >new</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['monitor:job:edit']"
        >Modify the</el-button>
      </el-col>
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
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:job:export']"
        >export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-s-operation"
          size="mini"
          @click="handleJobLog"
          v-hasPermi="['monitor:job:query']"
        >The log</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Task number" align="center" prop="jobId" />
      <el-table-column label="The name of the task" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="The task group name" align="center" prop="jobGroup" :formatter="jobGroupFormat" />
      <el-table-column label="Calling the target string" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
      <el-table-column label="cronExecute expression" align="center" prop="cronExpression" :show-overflow-tooltip="true" />
      <el-table-column label="state" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handleRun(scope.row)"
            v-hasPermi="['monitor:job:edit']"
          >Perform a</el-button>
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

    <!-- Add or modify the scheduled task dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="The name of the task" prop="jobName">
              <el-input v-model="form.jobName" placeholder="Please enter a task name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Task group" prop="jobGroup">
              <el-select v-model="form.jobGroup" placeholder="Please select a">
                <el-option
                  v-for="dict in jobGroupOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="invokeTarget">
              <span slot="label">
                A method is called
                <el-tooltip placement="top">
                  <div slot="content">
                    BeanInvoke the sample:ryTask.ryParams('ry')
                    <br />ClassClass call example:com.ruoyi.quartz.task.RyTask.ryParams('ry')
                    <br />Parameters that:Support string,Boolean type,Long integer,floating-point,The integer
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </span>
              <el-input v-model="form.invokeTarget" placeholder="Please enter the call target string" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="cronexpression" prop="cronExpression">
              <el-input v-model="form.cronExpression" placeholder="Please enter thecronExecute expression" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Whether concurrent" prop="concurrent">
              <el-radio-group v-model="form.concurrent" size="small">
                <el-radio-button label="0">allow</el-radio-button>
                <el-radio-button label="1">ban</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Wrong strategy" prop="misfirePolicy">
              <el-radio-group v-model="form.misfirePolicy" size="small">
                <el-radio-button label="1">Executed immediately</el-radio-button>
                <el-radio-button label="2">Perform a</el-radio-button>
                <el-radio-button label="3">Give up to perform</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="state">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">indeed set</el-button>
        <el-button @click="cancel">take eliminate</el-button>
      </div>
    </el-dialog>

    <!-- Task Log Details -->
    <el-dialog title="The task detail" :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Task number:">{{ form.jobId }}</el-form-item>
            <el-form-item label="The name of the task:">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Task group:">{{ jobGroupFormat(form) }}</el-form-item>
            <el-form-item label="Creation time:">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="cronexpression:">{{ form.cronExpression }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Next execution time:">{{ parseTime(form.nextValidTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Calling the target method:">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Task status:">
              <div v-if="form.status == 0">normal</div>
              <div v-else-if="form.status == 1">failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Whether concurrent:">
              <div v-if="form.concurrent == 0">allow</div>
              <div v-else-if="form.concurrent == 1">ban</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Execution strategy:">
              <div v-if="form.misfirePolicy == 0">The default policy</div>
              <div v-else-if="form.misfirePolicy == 1">Executed immediately</div>
              <div v-else-if="form.misfirePolicy == 2">Perform a</div>
              <div v-else-if="form.misfirePolicy == 3">Give up to perform</div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openView = false">guan closed</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, exportJob, runJob, changeJobStatus } from "@/api/monitor/job";

export default {
  name: "Job",
  data() {
    return {
      // The mask layer
      loading: true,
      // Select the array
      ids: [],
      // Non-single disable
      single: true,
      // Non-multiple disable
      multiple: true,
      // The total number of article
      total: 0,
      // Scheduled task table data
      jobList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // Whether to display the detail pop-up layer
      openView: false,
      // Task group name dictionary
      jobGroupOptions: [],
      // State dictionary
      statusOptions: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        jobName: [
          { required: true, message: "The task name cannot be empty", trigger: "blur" }
        ],
        invokeTarget: [
          { required: true, message: "The call target string cannot be empty", trigger: "blur" }
        ],
        cronExpression: [
          { required: true, message: "cronExecution expressions cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_job_group").then(response => {
      this.jobGroupOptions = response.data;
    });
    this.getDicts("sys_job_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Example Query the scheduled task list */
    getList() {
      this.loading = true;
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Task group name dictionary translation
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup);
    },
    // State dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Reset the form
    reset() {
      this.form = {
        jobId: undefined,
        jobName: undefined,
        jobGroup: undefined,
        invokeTarget: undefined,
        cronExpression: undefined,
        misfirePolicy: 1,
        concurrent: 1,
        status: "0"
      };
      this.resetForm("form");
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // Task Status Modification
    handleStatusChange(row) {
      let text = row.status === "0" ? "To enable the" : "disable";
      this.$confirm('Confirm to"' + text + '""' + row.jobName + '"The task??', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return changeJobStatus(row.jobId, row.status);
        }).then(() => {
          this.msgSuccess(text + "successful");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    /* Execute immediately */
    handleRun(row) {
      this.$confirm('Make sure to do it immediately"' + row.jobName + '"The task??', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return runJob(row.jobId, row.jobGroup);
        }).then(() => {
          this.msgSuccess("Execute successfully");
        }).catch(function() {});
    },
    /** Task Details */
    handleView(row) {
      getJob(row.jobId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
    /** Query the task log list */
    handleJobLog() {
      this.$router.push("/job/log");
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add tasks";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids;
      getJob(jobId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify the task";
      });
    },
    /** The submit button */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != undefined) {
            updateJob(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJob(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("New success");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** Delete button operation */
    handleDelete(row) {
      const jobIds = row.jobId || this.ids;
      this.$confirm('Confirm whether to delete the scheduled task"' + jobIds + '"Data item?', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return delJob(jobIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        }).catch(function() {});
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("Whether to export all scheduled task data items?", "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return exportJob(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>