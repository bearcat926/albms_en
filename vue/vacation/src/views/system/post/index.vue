<template>
  <div class="app-container">
    <div class=" table_data">
      <div class="bg-white bg-radius bg_shadow aui-padded-t-10">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px" class="aui-padded-l-10 aui-padded-r-10">
        <el-form-item label="Post code:" prop="postCode">
          <el-input
            v-model="queryParams.postCode"
            placeholder="Please enter the job code"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="Post the name:" prop="postName">
          <el-input
            v-model="queryParams.postName"
            placeholder="Please enter the job title"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="state:" prop="status">
          <el-select v-model="queryParams.status" placeholder="Post status" clearable size="small">
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
     

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:post:add']"
          >new</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:post:edit']"
          >Modify the</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:post:remove']"
          >delete</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:post:export']"
          >export</el-button>
        </el-col>
      </el-row>
       </el-form>
        <div class="aui-margin-t-15">
          <el-table
            v-loading="loading"
            :data="postList"
            @selection-change="handleSelectionChange"
            stripe
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="Jobs Numbers" align="center" prop="postId" />
            <el-table-column label="Post code" align="center" prop="postCode" />
            <el-table-column label="Post the name" align="center" prop="postName" />
            <el-table-column label="Post sorting" align="center" prop="postSort" />
            <el-table-column label="state" align="center" prop="status" :formatter="statusFormat" />
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
                  v-hasPermi="['system:post:edit']"
                >Modify the</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:post:remove']"
                >delete</el-button>
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

    <!-- Add or modify positions dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Post the name" prop="postName">
          <el-input v-model="form.postName" placeholder="Please enter the job title" />
        </el-form-item>
        <el-form-item label="Post code" prop="postCode">
          <el-input v-model="form.postCode" placeholder="Please enter a code name" />
        </el-form-item>
        <el-form-item label="Job order" prop="postSort">
          <el-input-number v-model="form.postSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="Post status" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
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
  listPost,
  getPost,
  delPost,
  addPost,
  updatePost,
  exportPost,
} from "@/api/system/post";

export default {
  name: "Post",
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
      // Job table data
      postList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // State data dictionary
      statusOptions: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postCode: undefined,
        postName: undefined,
        status: undefined,
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        postName: [
          { required: true, message: "The post name cannot be empty", trigger: "blur" },
        ],
        postCode: [
          { required: true, message: "The post code cannot be empty", trigger: "blur" },
        ],
        postSort: [
          { required: true, message: "The post order cannot be empty", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Query job listings */
    getList() {
      this.loading = true;
      listPost(this.queryParams).then((response) => {
        this.postList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Job status dictionary translation
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
        postId: undefined,
        postCode: undefined,
        postName: undefined,
        postSort: 0,
        status: "0",
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.postId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Adding jobs";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const postId = row.postId || this.ids;
      getPost(postId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Change jobs";
      });
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.postId != undefined) {
            updatePost(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPost(this.form).then((response) => {
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
      const postIds = row.postId || this.ids;
      this.$confirm(
        'Is it confirmed that the post number is deleted"' + postIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delPost(postIds);
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
      this.$confirm("Verify that all post data items are exported?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return exportPost(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
<style scoped>
body {
  background-color: #fff !important;
}
</style>