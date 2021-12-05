<template>
  <div class="app-container">
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:role:add']"
        >new</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:role:edit']"
        >Modify the</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:role:remove']"
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
    </el-row>-->
    <div class="table_data">
      <div class="flex flex_start aui-padded-t-10 aui-padded-b-10">
        <p class="fw_bold text-black font-size-20">Character set</p>
        <p
          class="text-white bg-purple font-size-14 addnew border-radius aui-margin-l-10"
          @click="handleAdd"
          v-hasPermi="['system:role:add']"
        >new</p>
      </div>
      <div class="bg-white shadow bg-radius">
        <div class="aui-padded-15 aui-padded-b-0">
          <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="90px">
            <el-form-item label="Character name:" prop="roleName">
              <el-input
                v-model="queryParams.roleName"
                placeholder="Enter a role name"
                clearable
                size="small"
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="A status:" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="Please select a"
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
              <el-button type="primary" size="mini" @click="handleQuery" class="find_btn">The query</el-button>
              <el-button size="mini" @click="resetQuery" class="reset_btn">reset</el-button>
            </el-form-item>
            <div class="flex export_box" style="float:right">
              <div class="text-black font-size-14 aui-padded-r-10">A total of{{total}}The data</div>
              <el-button size="mini"   @click="handleExport" v-hasPermi="['system:post:export']">
                <img  src="../../../assets/image/daoru.png" alt />
                <p style="margin-top:2px">The import</p>
              </el-button>
              <el-button size="mini"   @click="handleExport" v-hasPermi="['system:post:export']">
                <img  src="../../../assets/image/daochu.png" alt />
                <p style="margin-top:2px">export</p>
              </el-button>
            </div>
          </el-form>
        </div>
        <el-table
          v-loading="loading"
          :data="roleList"
          @selection-change="handleSelectionChange"
          stripe
        >
          <el-table-column type="selection" width="80" align="center" />
          <el-table-column label="Serial number" prop="roleId" />
          <el-table-column label="Character name" prop="roleName" :show-overflow-tooltip="true" />
          <!-- <el-table-column label="Permissions character" prop="roleKey" :show-overflow-tooltip="true" width="150" /> -->
          <!-- <el-table-column label="According to the order" prop="roleSort" width="100" /> -->
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
          <el-table-column label="Creation time" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="operation" align="center" class-name="small-padding " width="400">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:role:edit']"
                style="color:#5ECC59 !important;font-size:16px"
                title="The editor"
              ></el-button>
              
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                style="color: #E8522A !important;font-size:16px"
                v-hasPermi="['system:role:remove']"
                title="delete"
              ></el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-circle-check"
                @click="handleDataScope(scope.row)"
                style="font-size:16px"
                v-hasPermi="['system:role:edit']"
                title="Data access"
              ></el-button>
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

    <!-- Add or modify role configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Character name" prop="roleName">
          <el-input v-model="form.roleName" placeholder="Enter a role name" />
        </el-form-item>
        <el-form-item label="Permissions character" prop="roleKey">
          <el-input v-model="form.roleKey" placeholder="Please enter permission characters" />
        </el-form-item>
        <el-form-item label="Character sequence" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="state">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Menu permissions">
          <el-tree
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="id"
            empty-text="In the load,Please later"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
        <el-form-item label="note">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">indeed set</el-button>
        <el-button @click="cancel">take eliminate</el-button>
      </div>
    </el-dialog>

    <!-- Assign role data permissions dialog box -->
    <el-dialog :title="title" :visible.sync="openDataScope" width="500px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-form-item label="Character name">
          <el-input v-model="form.roleName" :disabled="true" />
        </el-form-item>
        <el-form-item label="Permissions character">
          <el-input v-model="form.roleKey" :disabled="true" />
        </el-form-item>
        <el-form-item label="competence">
          <el-select v-model="form.dataScope">
            <el-option
              v-for="item in dataScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Data access" v-show="form.dataScope == 2">
          <el-tree
            :data="deptOptions"
            show-checkbox
            default-expand-all
            ref="dept"
            node-key="id"
            empty-text="In the load,Please later"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDataScope">indeed set</el-button>
        <el-button @click="cancelDataScope">take eliminate</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRole,
  getRole,
  delRole,
  addRole,
  updateRole,
  exportRole,
  dataScope,
  changeRoleStatus,
} from "@/api/system/role";
import {
  treeselect as menuTreeselect,
  roleMenuTreeselect,
} from "@/api/system/menu";
import {
  treeselect as deptTreeselect,
  roleDeptTreeselect,
} from "@/api/system/dept";

export default {
  name: "Role",
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
      // Role table data
      roleList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // Whether to display the pop-up layer(Data access)
      openDataScope: false,
      // Date range
      dateRange: [],
      // State data dictionary
      statusOptions: [],
      // Data range option
      dataScopeOptions: [
        {
          value: "1",
          label: "All data permissions",
        },
        {
          value: "2",
          label: "Custom data permissions",
        },
        {
          value: "3",
          label: "Data permission of the department",
        },
        {
          value: "4",
          label: "Department and the following data rights",
        },
        {
          value: "5",
          label: "Only personal data permission",
        },
      ],
      // Menu list
      menuOptions: [],
      // Department list
      deptOptions: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        roleName: undefined,
        roleKey: undefined,
        status: undefined,
      },
      // The form parameter
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },
      // Form validation
      rules: {
        roleName: [
          { required: true, message: "The role name cannot be empty", trigger: "blur" },
        ],
        roleKey: [
          { required: true, message: "Permission characters cannot be empty", trigger: "blur" },
        ],
        roleSort: [
          { required: true, message: "The role order cannot be empty", trigger: "blur" },
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
    /** Querying the Role List */
    getList() {
      this.loading = true;
      listRole(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.roleList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** Query the menu tree structure */
    getMenuTreeselect() {
      menuTreeselect().then((response) => {
        this.menuOptions = response.data;
      });
    },
    /** Query the department tree structure */
    getDeptTreeselect() {
      deptTreeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // All menu node data
    getMenuAllCheckedKeys() {
      // The currently selected menu node
      let checkedKeys = this.$refs.menu.getHalfCheckedKeys();
      // A half-selected menu node
      let halfCheckedKeys = this.$refs.menu.getCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    // Data of all department nodes
    getDeptAllCheckedKeys() {
      // The currently selected department node
      let checkedKeys = this.$refs.dept.getHalfCheckedKeys();
      // A half-selected department node
      let halfCheckedKeys = this.$refs.dept.getCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** According to the characterIDQuery the menu tree structure */
    getRoleMenuTreeselect(roleId) {
      roleMenuTreeselect(roleId).then((response) => {
        this.menuOptions = response.menus;
        this.$refs.menu.setCheckedKeys(response.checkedKeys);
      });
    },
    /** According to the characterIDQuery the department tree structure */
    getRoleDeptTreeselect(roleId) {
      roleDeptTreeselect(roleId).then((response) => {
        this.deptOptions = response.depts;
        this.$refs.dept.setCheckedKeys(response.checkedKeys);
      });
    },
    // Changing role Status
    handleStatusChange(row) {
      let text = row.status === "0" ? "To enable the" : "disable";
      this.$confirm(
        'Confirm to"' + text + '""' + row.roleName + '"Role??',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return changeRoleStatus(row.roleId, row.status);
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
    // Cancel button(Data access)
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // Reset the form
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.form = {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        deptIds: [],
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
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.roleId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.getMenuTreeselect();
      this.open = true;
      this.title = "Adding roles";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const roleId = row.roleId || this.ids;
      this.$nextTick(() => {
        this.getRoleMenuTreeselect(roleId);
      });
      getRole(roleId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify the role";
      });
    },
    /** Operation of assigning data rights */
    handleDataScope(row) {
      this.reset();
      this.$nextTick(() => {
        this.getRoleDeptTreeselect(row.roleId);
      });
      getRole(row.roleId).then((response) => {
        this.form = response.data;
        this.openDataScope = true;
        this.title = "Assigning data Rights";
      });
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then((response) => {
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
    /** The submit button(Data access) */
    submitDataScope: function () {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then((response) => {
          if (response.code === 200) {
            this.msgSuccess("Modify the success");
            this.openDataScope = false;
            this.getList();
          }
        });
      }
    },
    /** Delete button operation */
    handleDelete(row) {
      const roleIds = row.roleId || this.ids;
      this.$confirm(
        'Confirm whether to delete the role"' + roleIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delRole(roleIds);
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
      this.$confirm("Verify that all role data items are exported?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return exportRole(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
<style>
</style>