<template>
  <div class="app-container">
    <div class>
      <chart />
    </div>
    <div class="flex flex_start title_box aui-padded-b-15 aui-padded-t-15">
      <p class="font-size-20">Department Management</p>
      <div
        class="addnew bg-theme text-white aui-margin-l-10 border-radius font-size-14"
        @click="handleAdd"
        v-hasPermi="['system:dept:add']"
      >new</div>
    </div>
    <div class="table_data">
      <div class="bg-white bg_shadow bg-radius">
        <div class="aui-padded-15 aui-padded-t-10 aui-padded-b-0">
          <el-form :inline="true">
            <el-form-item label="Department name">
              <el-input
                v-model="queryParams.deptName"
                placeholder="Please enter a department name"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="state">
              <el-select v-model="queryParams.status" placeholder="Department of state" clearable size="small">
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <!-- <el-button
          class="filter-item"
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
              >search</el-button>-->
              <el-button type="primary" size="medium" @click="handleQuery">The query</el-button>
              <el-button size="medium" @click="resetQuery">reset</el-button>
              <!-- <el-button
          class="filter-item"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:dept:add']"
              >new</el-button>-->
            </el-form-item>
          </el-form>
        </div>
        <el-table
          v-loading="loading"
          :data="deptList"
          row-key="deptId"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          stripe
        >
          <el-table-column prop="deptName" label="Department name" width="260"></el-table-column>
          <el-table-column prop="orderNum" label="The sorting" width="200"></el-table-column>
          <el-table-column prop="status" label="state" :formatter="statusFormat" width="100"></el-table-column>
          <el-table-column label="Creation time" align="left" prop="createTime" width="200">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="operation" align="left" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:dept:edit']"
                style="color:#5ECC59 !important;font-size:16px"
                title="The editor"
              ></el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-plus"
                @click="handleAdd(scope.row)"
                v-hasPermi="['system:dept:add']"
                style="color: #4860FB  !important;font-size:16px"
                title="Add the department"
              ></el-button>
              <el-button
                v-if="scope.row.parentId != 0"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:dept:remove']"
                style="color: #E8522A !important;font-size:16px"
                title="delete"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- Add or modify departments dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="37%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="Superior departments:" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="deptOptions"
                :normalizer="normalizer"
                placeholder="Select superior department"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department name:" prop="deptName">
              <el-input v-model="form.deptName" placeholder="Please enter a department name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item class label="According to the sorting:" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="Please enter the serial number" oninput="value=value.replace(/^\.+|[^\d.]/g,'')"></el-input>
            </el-form-item>
           
          </el-col>
          <el-col :span="12">
            <el-form-item label="head:" prop="leader">
              <el-input v-model="form.leader" placeholder="Please enter the person in charge" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Contact phone number:" prop="phone">
              <el-input v-model="form.phone" placeholder="Please enter your contact number" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="email:" prop="email">
              <el-input v-model="form.email" placeholder="Please enter email address" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department of state:">
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
  </div>
</template>

<script>
import chart from "../../user/chart/index";
import {
  listDept,
  getDept,
  delDept,
  addDept,
  updateDept,
  listDeptExcludeChild,
} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  components: { Treeselect, chart },
  data() {
    return {
      // The mask layer
      loading: true,
      // Table tree data
      deptList: [],
      // Department tree option
      deptOptions: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // State data dictionary
      statusOptions: [],
      // Query parameters
      queryParams: {
        deptName: undefined,
        status: undefined,
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        parentId: [
          { required: true, message: "The upper department cannot be empty", trigger: "blur" },
        ],
        deptName: [
          { required: true, message: "The department name cannot be empty", trigger: "blur" },
        ],
        orderNum: [
          { required: true, message: "Menu order cannot be empty", trigger: "blur" },
        ],
        email: [
          {
            type: "email",
            message: "'Please enter the correct email address",
            trigger: ["blur", "change"],
          },
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter the correct mobile phone number",
            trigger: "blur",
          },
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
    /** Reset button operation */
    resetQuery() {
      this.queryParams={
        deptName: undefined,
        status: undefined,
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Querying the Department List */
    getList() {
      console.log(this.queryParams)
      this.loading = true;
      listDept(this.queryParams).then((response) => {
        this.deptList = this.handleTree(response.data, "deptId");
        this.loading = false;
      });
    },
    /** Transform departmental data structures */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children,
      };
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
        deptId: undefined,
        parentId: undefined,
        deptName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        status: "0",
      };
      this.resetForm("form");
    },
    /** Search button operation */
    handleQuery() {
      

      this.getList();
    },
    /** New Button Operation */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "Add the department";
      listDept().then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      getDept(row.deptId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify the department";
      });
      listDeptExcludeChild(row.deptId).then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.deptId != undefined) {
            updateDept(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDept(this.form).then((response) => {
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
      this.$confirm(
        'Confirm whether to delete the name is"' + row.deptName + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delDept(row.deptId);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
  },
};
</script>
<style>
.el-button--primary {
  background-color: #4860fb !important;
  border-color: #4860fb !important;
}
</style>