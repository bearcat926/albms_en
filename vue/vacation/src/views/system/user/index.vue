<template>
  <div class="app-container">
    <div class>
      <chart />
    </div>
    <el-row :gutter="20" class="aui-margin-t-15">
      <!--Department of data-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="Please enter a department name"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--User data-->
      <el-col :span="20" :xs="24">
        <div class="flex flex_start title_box aui-padded-b-15">
          <p class="font-size-20">User Management</p>
          <div
            class="addnew bg-theme text-white aui-margin-l-10 border-radius font-size-14"
            @click="handleAdd"
            v-hasPermi="['system:user:add']"
          >new</div>
          <div
            class="addnew delnew text-white aui-margin-l-10 border-radius font-size-14"
            @click="handleDelete"
            v-hasPermi="['system:user:remove']"
          >delete</div>
        </div>
        <div class="table_data">
          <div class="bg-white bg_shadow bg-radius">
            <div class="aui-padded-15 aui-padded-t-10 aui-padded-b-0">
              <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
                <el-row>
                  <el-col :span="20">
                    <el-form-item label="The user name:" prop="userName">
                      <el-input
                        v-model="queryParams.userName"
                        placeholder="Please enter a user name"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="Mobile phone number:" prop="phonenumber">
                      <el-input
                        v-model="queryParams.phonenumber"
                        placeholder="Please enter your mobile phone number"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item label="jobs:">

                      <el-select v-model="queryParams.postId"  placeholder="Please select a" style="width:100%"  clearable filterable>
                        <el-option
                          v-for="item in postOptions"
                          :key="item.postId"
                          :label="item.postName"
                          :value="item.postId"
                          :disabled="item.status == 1"

                        ></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="Creation time:">
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
                      <el-button type="primary" size="medium" @click="handleQuery">The query</el-button>
                      <el-button size="medium" @click="resetQuery">reset</el-button>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <!-- <el-button
                      type="primary"
                      icon="el-icon-plus"
                      size="mini"
                      @click="handleAdd"
                      v-hasPermi="['system:user:add']"
                    >new</el-button>
                    <el-button
                      type="success"
                      icon="el-icon-edit"
                      size="mini"
                      :disabled="single"
                      @click="handleUpdate"
                      v-hasPermi="['system:user:edit']"
                    >Modify the</el-button>
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      :disabled="multiple"
                      @click="handleDelete"
                      v-hasPermi="['system:user:remove']"
                    >delete</el-button>-->
                    <!-- <el-button
                      type="info"
                      icon="el-icon-upload2"
                      size="mini"
                      @click="handleImport"
                      v-hasPermi="['system:user:import']"
                    >The import</el-button>
                    <el-button
                      type="warning"
                      icon="el-icon-download"
                      size="mini"
                      @click="handleExport"
                      v-hasPermi="['system:user:export']"
                    >export</el-button>-->

                    <div class>
                      <div class="flex export_box" style="float:right">
                        <div class="text-black font-size-14 aui-padded-r-10">A total of{{total}}The data</div>
                        <el-button
                          size="mini"
                          @click="handleImport"
                          v-hasPermi="['system:user:import']"
                        >
                          <img src="../../../assets/image/daoru.png" alt />
                          <p style="margin-top:2px">The import</p>
                        </el-button>
                        <el-button
                          size="mini"
                          @click="handleExport"
                          v-hasPermi="['system:user:export']"
                        >
                          <img src="../../../assets/image/daochu.png" alt />
                          <p style="margin-top:2px">export</p>
                        </el-button>
                      </div>
                    </div>
                  </el-col>
                </el-row>
              </el-form>
            </div>
            <el-table
              v-loading="loading"
              :data="userList"
              @selection-change="handleSelectionChange"
              stripe
            >
              <el-table-column type="selection" width="80" align="center" />
              <el-table-column label="The user work number" align="center" prop="userCode" />
              <el-table-column
                label="The user name"
                align="center"
                prop="userName"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                label="The user nickname"
                align="center"
                prop="nickName"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                label="department"
                align="center"
                prop="dept.deptName"
                :show-overflow-tooltip="true"
              />
              <el-table-column label="Mobile phone number" align="center" prop="phonenumber" width="120" />
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
              <el-table-column label="Creation time" align="center" prop="createTime" width="160">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="operation"
                align="center"
                width="180"
                class-name="small-padding fixed-width"
              >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUpdate(scope.row)"
                    v-hasPermi="['system:user:edit']"
                    style="color:#5ECC59 !important;font-size:16px"
                    title="The editor"
                  ></el-button>
                  <el-button
                    v-if="scope.row.userId !== 1"
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['system:user:remove']"
                    style="color: #E8522A !important;font-size:16px"
                    title="delete"
                  ></el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-key"
                    @click="handleResetPwd(scope.row)"
                    v-hasPermi="['system:user:resetPwd']"
                    style="font-size:16px"
                    title="To reset your password"
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <pagination
            v-show="total>10"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </div>
      </el-col>
    </el-row>

    <!-- Add or modify parameter configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="40%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="The user nickname:" prop="nickName">
              <el-input v-model="form.nickName" placeholder="Please enter a user nickname" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The user work number:" prop="userCode">
              <el-input v-model="form.userCode" placeholder="Automatic system allocation" :disabled="true"/>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Attribution department:" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="Select a department"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Mobile phone number:" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="Please enter your mobile phone number" maxlength="11" />
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="email:" prop="email">
              <el-input v-model="form.email" placeholder="Please enter email address" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="The user name:" prop="userName">
              <el-input v-model="form.userName" placeholder="Please enter a user name" />
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="The user password:" prop="password">
              <el-input v-model="form.password" placeholder="Please enter the user password" type="password" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="User's gender:">
              <el-select v-model="form.sex" placeholder="Please select a" style="width:100%">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>

          <el-col :span="12">
            <el-form-item label="jobs:">
              <el-select v-model="postIds"  placeholder="Please select a" style="width:100%"  @change="postSel">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="role:">
              <el-select v-model="form.roleIds" multiple placeholder="Please select a" style="width:100%">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="state:">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="note:">
              <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">indeed set</el-button>
        <el-button @click="cancel">take eliminate</el-button>
      </div>
    </el-dialog>

    <!-- User import dialog box -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          Drag the file here,or
          <em>Click on the upload</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />Whether to update existing user data
          <el-link type="info" style="font-size:12px" @click="importTemplate">Download the template</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">prompt:Import only“xls”or“xlsx”Format file!</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">indeed set</el-button>
        <el-button @click="upload.open = false">take eliminate</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chart from "../../user/chart/index";
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  exportUser,
  resetUserPwd,
  changeUserStatus,
  importTemplate,
} from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  components: { Treeselect, chart },
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
      // User table data
      userList: null,
      // Pop-up layer title
      title: "",
      // Department tree option
      deptOptions: undefined,
      // Whether to display the pop-up layer
      open: false,
      // Department name
      deptName: undefined,
      // The default password
      initPassword: undefined,
      // Date range
      dateRange: [],
      // State data dictionary
      statusOptions: [],
      // Gender status dictionary
      sexOptions: [],
      // Job options
      postOptions: [],
      // Role options
      roleOptions: [],
      // The form parameter
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },
      // User Import Parameters
      upload: {
        // Whether to display the pop-up layer(The user to import)
        open: false,
        // Pop-up layer title(The user to import)
        title: "",
        // Whether to disable upload
        isUploading: false,
        // Whether to update existing user data
        updateSupport: 0,
        // Set the upload request header
        headers: { Authorization: "Bearer " + getToken() },
        // Upload address
        url: process.env.VUE_APP_BASE_API + "/system/user/importData",
      },
      postIds:undefined,
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
        postId:undefined,
        beginTime:undefined,
        endTime:undefined

      },
      // Form validation
      rules: {
        userName: [
          { required: true, message: "The user name cannot be empty", trigger: "blur" },
        ],
        // userCode: [
        //   { required: true, message: "The id cannot be empty", trigger: "blur" },
        // ],
        nickName: [
          { required: true, message: "User nicknames cannot be empty", trigger: "blur" },
        ],
        deptId: [
          { required: true, message: "The owning department cannot be empty", trigger: "blur" },
        ],
        password: [
          { required: true, message: "The user password cannot be empty", trigger: "blur" },
        ],
        email: [
          { required: true, message: "The email address cannot be empty", trigger: "blur" },
          {
            type: "email",
            message: "'Please enter the correct email address",
            trigger: ["blur", "change"],
          },
        ],
        phonenumber: [
          { required: true, message: "The mobile phone number cannot be empty", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter the correct mobile phone number",
            trigger: "blur",
          },
        ],
      },
    };
  },
  watch: {

    // Filter the department tree by name
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then((response) => {
      this.sexOptions = response.data;
    });
    this.getConfigKey("sys.user.initPassword").then((response) => {
      this.initPassword = response.msg;
    });
     getUser().then((response) => {
        this.postOptions = response.posts;
      });
  },
  methods: {
     postSel(id){
      this.form.postIds=[]
      this.form.postIds.push(id)


    },
    /** Querying a User List */
    getList() {
      this.loading = true;
      listUser(this.queryParams).then(
        (response) => {
          // console.log(response, )
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** Query the department drop-down tree structure */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // Screening of node
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // Node Click event
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // User Status Modification
    handleStatusChange(row) {
      let text = row.status === "0" ? "To enable the" : "disable";
      this.$confirm(
        'Confirm to"' + text + '""' + row.userName + '"The user??',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return changeUserStatus(row.userId, row.status);
        })
        .then(() => {
          this.msgSuccess(text + "successful");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Reset the form
    reset() {
      this.form = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        userCode:"",
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: [],
      };
      this.resetForm("form");
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.beginTime = this.dateRange[0]
      this.queryParams.endTime = this.dateRange[1]

      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.dateRange = [];
      this.queryParams= {
        pageNum: 1,
        pageSize: 20,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
        postId:undefined,
        beginTime:undefined,
        endTime:undefined

      },
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      getUser().then((response) => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "Add user";
        this.form.password = this.initPassword;
      });
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then((response) => {
        this.postIds=response.postIds[0];
        this.form = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "Modify the user";
        this.form.password = "";
      });
    },
    /** Reset password button operation */
    handleResetPwd(row) {
      this.$prompt('Please enter the"' + row.userName + '"The new password', "prompt", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("Modify the success,The new password is:" + value);
            }
          });
        })
        .catch(() => {});
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUser(this.form).then((response) => {
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
      const userIds = row.userId || this.ids;
      this.$confirm(
        'Confirm whether to delete the user"' + userIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delUser(userIds);
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
      this.$confirm("Whether to confirm that all user data items are exported?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return exportUser(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** Import button operation */
    handleImport() {
      this.upload.title = "The user to import";
      this.upload.open = true;
    },
    /** Downloading a Template */
    importTemplate() {
      importTemplate().then((response) => {
        this.download(response.msg);
      });
    },
    // File upload processing
    handleFileUploadProgress(event, file, fileList) {
      console.log(file)
      this.upload.isUploading = true;
    },
    // The file is successfully uploaded. Procedure
    handleFileSuccess(response, file, fileList) {
      console.log(file)
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "Import the results", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // Submit and upload files
    submitFileForm() {
      this.$refs.upload.submit();
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
