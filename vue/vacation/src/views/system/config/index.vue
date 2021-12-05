<template>
  <div class="app-container">
    <div class="table_data">
      <div class="bg-white bg-radius bg_shadow aui-padded-t-10 aui-padded-r-10">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          label-width="100px"
          class="aui-padded-l-10"
        >
          <el-form-item label="The parameter name:" prop="configName">
            <el-input
              v-model="queryParams.configName"
              placeholder="Please enter a parameter name"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="Parameters of the key name:" prop="configKey">
            <el-input
              v-model="queryParams.configKey"
              placeholder="Please enter a parameter key name"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="The built-in:" prop="configType">
            <el-select v-model="queryParams.configType" placeholder="The built-in" clearable size="small">
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Creation time">
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
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['system:config:add']"
              >new</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['system:config:edit']"
              >Modify the</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['system:config:remove']"
              >delete</el-button>
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
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-refresh"
                size="mini"
                @click="handleClearCache"
                v-hasPermi="['system:config:remove']"
              >Clear the cache</el-button>
            </el-col>
          </el-row>
        </el-form>
        <div class="aui-margin-t-15">
          <el-table
            v-loading="loading"
            :data="configList"
            @selection-change="handleSelectionChange"
            stripe
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="Parameter the primary key" align="center" prop="configId" />
            <el-table-column
              label="The parameter name"
              align="center"
              prop="configName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="Parameters of the key name"
              align="center"
              prop="configKey"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="Parameters of the key value" align="center" prop="configValue" show-tooltip-when-overflow />
            <el-table-column label="The built-in" align="center" prop="configType" :formatter="typeFormat" />
            <el-table-column label="note" align="center" prop="remark" :show-overflow-tooltip="true" />
            <el-table-column label="Creation time" align="center" prop="createTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:config:edit']"
                >Modify the</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:config:remove']"
                >delete</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

      <pagination
        v-show="total>20"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- Add or modify parameter configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="The parameter name" prop="configName">
          <el-input v-model="form.configName" placeholder="Please enter a parameter name" />
        </el-form-item>
        <el-form-item label="Parameters of the key name" prop="configKey">
          <el-input v-model="form.configKey" placeholder="Please enter a parameter key name" />
        </el-form-item>
        <el-form-item label="Parameters of the key value" prop="configValue">
          <el-input v-model="form.configValue" placeholder="Please enter a parameter key value" />
        </el-form-item>
        <el-form-item label="The built-in" prop="configType">
          <el-radio-group v-model="form.configType">
            <el-radio
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="note" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">indeed set</el-button>
        <el-button @click="cancel">take eliminate</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listConfig,
  getConfig,
  delConfig,
  addConfig,
  updateConfig,
  exportConfig,
  clearCache,
} from "@/api/system/config";

export default {
  name: "Config",
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
      // Parameter table data
      configList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // Type data dictionary
      typeOptions: [],
      // Date range
      dateRange: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        configName: undefined,
        configKey: undefined,
        configType: undefined,
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        configName: [
          { required: true, message: "Parameter names cannot be empty", trigger: "blur" },
        ],
        configKey: [
          { required: true, message: "Parameter keys cannot be empty", trigger: "blur" },
        ],
        configValue: [
          { required: true, message: "Parameter keys cannot be empty", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yes_no").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** Querying the Parameter List */
    getList() {
      this.loading = true;
      listConfig(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.configList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Parameter system built-in dictionary translation
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.configType);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Reset the form
    reset() {
      this.form = {
        configId: undefined,
        configName: undefined,
        configKey: undefined,
        configValue: undefined,
        configType: "Y",
        remark: undefined,
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add parameters";
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.configId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const configId = row.configId || this.ids;
      getConfig(configId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify the parameters";
      });
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.configId != undefined) {
            updateConfig(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addConfig(this.form).then((response) => {
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
      const configIds = row.configId || this.ids;
      this.$confirm(
        'Confirm whether to delete parameter number is"' + configIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delConfig(configIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("Verify that all parameter data items are exported?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return exportConfig(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** Clear cache button operation */
    handleClearCache() {
      clearCache().then((response) => {
        if (response.code === 200) {
          this.msgSuccess("Clean up the success");
        }
      });
    },
  },
};
</script>
