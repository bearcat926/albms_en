<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="Name of the dictionary" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="Please enter a dictionary name"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="A dictionary type" prop="dictType">
        <el-input
          v-model="queryParams.dictType"
          placeholder="Please enter a dictionary type"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="State of the dictionary"
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
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:dict:add']"
        >new</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:dict:edit']"
        >Modify the</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:dict:remove']"
        >delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:dict:export']"
        >export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-refresh"
          size="mini"
          @click="handleClearCache"
          v-hasPermi="['system:dict:remove']"
        >Clear the cache</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="The dictionary number" align="center" prop="dictId" />
      <el-table-column label="Name of the dictionary" align="center" prop="dictName" :show-overflow-tooltip="true" />
      <el-table-column label="A dictionary type" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/dict/type/data/' + scope.row.dictId" class="link-type">
            <span>{{ scope.row.dictType }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="state" align="center" prop="status" :formatter="statusFormat" />
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
            v-hasPermi="['system:dict:edit']"
          >Modify the</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dict:remove']"
          >delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>20"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- Add or modify parameter configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Name of the dictionary" prop="dictName">
          <el-input v-model="form.dictName" placeholder="Please enter a dictionary name" />
        </el-form-item>
        <el-form-item label="A dictionary type" prop="dictType">
          <el-input v-model="form.dictType" placeholder="Please enter a dictionary type" />
        </el-form-item>
        <el-form-item label="state" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="note" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content"></el-input>
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
import { listType, getType, delType, addType, updateType, exportType, clearCache } from "@/api/system/dict/type";

export default {
  name: "Dict",
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
      // Dictionary tabular data
      typeList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // State data dictionary
      statusOptions: [],
      // Date range
      dateRange: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        dictName: undefined,
        dictType: undefined,
        status: undefined
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        dictName: [
          { required: true, message: "Dictionary names cannot be empty", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "The dictionary type cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Query the dictionary type list */
    getList() {
      this.loading = true;
      listType(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.typeList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Dictionary state dictionary translation
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
        dictId: undefined,
        dictName: undefined,
        dictType: undefined,
        status: "0",
        remark: undefined
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
      this.title = "Adding a dictionary type";
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const dictId = row.dictId || this.ids
      getType(dictId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Changing the dictionary type";
      });
    },
    /** The submit button */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictId != undefined) {
            updateType(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addType(this.form).then(response => {
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
      const dictIds = row.dictId || this.ids;
      this.$confirm('Confirm whether to delete the dictionary"' + dictIds + '"Data item?', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return delType(dictIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        }).catch(function() {});
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Verify that all types of data items are exported?', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return exportType(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** Clear cache button operation */
    handleClearCache() {
      clearCache().then(response => {
        if (response.code === 200) {
          this.msgSuccess("Clean up the success");
        }
      });
    }
  }
};
</script>