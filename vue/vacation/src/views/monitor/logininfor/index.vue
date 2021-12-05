<template>
  <div class="app-container">
    <div class="table_data">
      <div class="bg-white bg-radius bg_shadow aui-padded-t-10">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="100px"
          class="aui-padded-l-10 aui-padded-r-10"
        >
          <el-form-item label="The login address:" prop="ipaddr">
            <el-input
              v-model="queryParams.ipaddr"
              placeholder="Please enter your login address"
              clearable
              style="width: 200px;"
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="The user name:" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="Please enter a user name"
              clearable
              style="width: 200px;"
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="state:" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="The login status"
              clearable
              size="small"
              style="width: 200px"
            >
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="The login time:">
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

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['monitor:logininfor:remove']"
              >delete</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="handleClean"
                v-hasPermi="['monitor:logininfor:remove']"
              >empty</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['system:logininfor:export']"
              >export</el-button>
            </el-col>
          </el-row>
        </el-form>
        <div class="aui-margin-t-15">
          <el-table
            v-loading="loading"
            :data="list"
            @selection-change="handleSelectionChange"
            stripe
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="Access number" align="center" prop="infoId" />
            <el-table-column label="The user name" align="center" prop="userName" />
            <el-table-column
              label="The login address"
              align="center"
              prop="ipaddr"
              width="130"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="The login site"
              align="center"
              prop="loginLocation"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="The browser" align="center" prop="browser" />
            <el-table-column label="The operating system" align="center" prop="os" />
            <el-table-column label="The login status" align="center" prop="status" :formatter="statusFormat" />
            <el-table-column label="Operational information" align="center" prop="msg" />
            <el-table-column label="Login date" align="center" prop="loginTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.loginTime) }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import {
  list,
  delLogininfor,
  cleanLogininfor,
  exportLogininfor,
} from "@/api/monitor/logininfor";

export default {
  name: "Logininfor",
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
      // Tabular data
      list: [],
      // State data dictionary
      statusOptions: [],
      // Date range
      dateRange: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ipaddr: undefined,
        userName: undefined,
        status: undefined,
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_common_status").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Example Query the login log list */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Login status dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
      this.ids = selection.map((item) => item.infoId);
      this.multiple = !selection.length;
    },
    /** Delete button operation */
    handleDelete(row) {
      const infoIds = row.infoId || this.ids;
      this.$confirm(
        'Confirm whether to delete the access number"' + infoIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delLogininfor(infoIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
    /** Clear button operation */
    handleClean() {
      this.$confirm("Determine whether to delete all login log data items?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return cleanLogininfor();
        })
        .then(() => {
          this.getList();
          this.msgSuccess("Empty the success");
        })
        .catch(function () {});
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("Whether to export all operation log data items?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return exportLogininfor(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>

