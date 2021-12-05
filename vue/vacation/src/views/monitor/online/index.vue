<template>
  <div class="app-container">
    <div class="table_data">
      <div class="bg-white bg-radius bg_shadow aui-padded-t-10">
        <el-form :model="queryParams" ref="queryForm" :inline="true" class="aui-padded-l-10">
          <el-form-item label="The login address" prop="ipaddr">
            <el-input
              v-model="queryParams.ipaddr"
              placeholder="Please enter your login address"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="The user name" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="Please enter a user name"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
          </el-form-item>
        </el-form>
        <div class="aui-margin-t-15">
          <el-table
            v-loading="loading"
            :data="list.slice((pageNum-1)*pageSize,pageNum*pageSize)"
            style="width: 100%;"
          >
            <el-table-column label="The serial number" type="index" align="center">
              <template slot-scope="scope">
                <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="The session id"
              align="center"
              prop="tokenId"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="The login name"
              align="center"
              prop="userName"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="Department name" align="center" prop="deptName" />
            <el-table-column label="The host" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
            <el-table-column
              label="The login site"
              align="center"
              prop="loginLocation"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="The browser" align="center" prop="browser" />
            <el-table-column label="The operating system" align="center" prop="os" />
            <el-table-column label="The login time" align="center" prop="loginTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.loginTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleForceLogout(scope.row)"
                  v-hasPermi="['monitor:online:forceLogout']"
                >Strong back</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

      <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" />
    </div>
  </div>
</template>

<script>
import { list, forceLogout } from "@/api/monitor/online";

export default {
  name: "Online",
  data() {
    return {
      // The mask layer
      loading: true,
      // The total number of article
      total: 0,
      // Tabular data
      list: [],
      pageNum: 1,
      pageSize: 10,
      // Query parameters
      queryParams: {
        ipaddr: undefined,
        userName: undefined,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Example Query the login log list */
    getList() {
      this.loading = true;
      list(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** Search button operation */
    handleQuery() {
      this.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Strong back button operation */
    handleForceLogout(row) {
      this.$confirm(
        'Confirm the strong rollback name is"' + row.userName + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return forceLogout(row.tokenId);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("Strong back successfully");
        })
        .catch(function () {});
    },
  },
};
</script>

