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
          <el-form-item label="System module:" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="Please enter the system module"
              clearable
              style="width: 240px;"
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="The operator:" prop="operName">
            <el-input
              v-model="queryParams.operName"
              placeholder="Please enter operator"
              clearable
              style="width: 240px;"
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="type:" prop="businessType">
            <el-select
              v-model="queryParams.businessType"
              placeholder="Operation type"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="state:" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="Operating state"
              clearable
              size="small"
              style="width:240px"
            >
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Operating time:">
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
                v-hasPermi="['monitor:operlog:remove']"
              >delete</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="handleClean"
                v-hasPermi="['monitor:operlog:remove']"
              >empty</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['system:config:export']"
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
            <el-table-column label="The log number" align="center" prop="operId" />
            <el-table-column label="System module" align="center" prop="title" />
            <el-table-column
              label="Operation type"
              align="center"
              prop="businessType"
              :formatter="typeFormat"
            />
            <el-table-column label="Request way" align="center" prop="requestMethod" />
            <el-table-column label="The operator" align="center" prop="operName" />
            <el-table-column
              label="The host"
              align="center"
              prop="operIp"
              width="130"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="Operation place"
              align="center"
              prop="operLocation"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="Operating state" align="center" prop="status" :formatter="statusFormat" />
            <el-table-column label="Operation date" align="center" prop="operTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.operTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleView(scope.row,scope.index)"
                  v-hasPermi="['monitor:operlog:query']"
                >detailed</el-button>
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
    <!-- Operation Log Details -->
    <el-dialog title="Operation Log Details" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Operation module:">{{ form.title }} / {{ typeFormat(form) }}</el-form-item>
            <el-form-item
              label="The login information:"
            >{{ form.operName }} / {{ form.operIp }} / {{ form.operLocation }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Request the address:">{{ form.operUrl }}</el-form-item>
            <el-form-item label="Request way:">{{ form.requestMethod }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Operation method:">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Request parameters:">{{ form.operParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Returns the parameter:">{{ form.jsonResult }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Operating state:">
              <div v-if="form.status === 0">normal</div>
              <div v-else-if="form.status === 1">failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Operating time:">{{ parseTime(form.operTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Exception information:" v-if="form.status === 1">{{ form.errorMsg }}</el-form-item>
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
import {
  list,
  delOperlog,
  cleanOperlog,
  exportOperlog,
} from "@/api/monitor/operlog";

export default {
  name: "Operlog",
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
      // Whether to display the pop-up layer
      open: false,
      // Type data dictionary
      typeOptions: [],
      // Type data dictionary
      statusOptions: [],
      // Date range
      dateRange: [],
      // The form parameter
      form: {},
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        operName: undefined,
        businessType: undefined,
        status: undefined,
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_oper_type").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_common_status").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Querying Login Logs */
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
    // Operation log status dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Operation log type dictionary translation
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.businessType);
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
      this.ids = selection.map((item) => item.operId);
      this.multiple = !selection.length;
    },
    /** Detailed button operation */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** Delete button operation */
    handleDelete(row) {
      const operIds = row.operId || this.ids;
      this.$confirm(
        'Confirm deleting the log number is"' + operIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delOperlog(operIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
    /** Clear button operation */
    handleClean() {
      this.$confirm("Determine whether to delete all operation log data items?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return cleanOperlog();
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
          return exportOperlog(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>

