<template>
  <div class="app-container">
    <el-form :inline="true">
      <el-form-item label="The name of the menu">
        <el-input
          v-model="queryParams.menuName"
          placeholder="Please enter a menu name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state">
        <el-select v-model="queryParams.status" placeholder="State of the menu" clearable size="small">
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
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:menu:add']">new</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="loading"
      :data="menuList"
      row-key="menuId"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="menuName" label="The name of the menu" :show-overflow-tooltip="true" width="160"></el-table-column>
      <el-table-column prop="icon" label="icon" align="center" width="100">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="The sorting" width="60"></el-table-column>
      <el-table-column prop="perms" label="Permission to identify" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="component" label="The component path" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="status" label="state" :formatter="statusFormat" width="80"></el-table-column>
      <el-table-column label="Creation time" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" 
            type="text" 
            icon="el-icon-edit" 
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:menu:edit']"
          >Modify the</el-button>
          <el-button 
            size="mini" 
            type="text" 
            icon="el-icon-plus" 
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
          >new</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
          >delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add or modify menu dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="The higher the menu">
              <treeselect
                v-model="form.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="Select superior menu"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="The menu type" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">directory</el-radio>
                <el-radio label="C">The menu</el-radio>
                <el-radio label="F">button</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-if="form.menuType != 'F'" label="The menu icon">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" />
                <el-input slot="reference" v-model="form.icon" placeholder="Click on the select icon" readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The name of the menu" prop="menuName">
              <el-input v-model="form.menuName" placeholder="Please enter a menu name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="According to the sorting" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'F'" label="Whether the link out">
              <el-radio-group v-model="form.isFrame">
                <el-radio label="0">is</el-radio>
                <el-radio label="1">no</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'F'" label="The routing address" prop="path">
              <el-input v-model="form.path" placeholder="Please enter the routing address" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item label="The component path" prop="component">
              <el-input v-model="form.component" placeholder="Please enter the component path" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'M'" label="Permission to identify">
              <el-input v-model="form.perms" placeholder="Please permission identifier" maxlength="150" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'F'" label="According to the state">
              <el-radio-group v-model="form.visible">
                <el-radio
                  v-for="dict in visibleOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'F'" label="State of the menu">
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
import { listMenu, getMenu, delMenu, addMenu, updateMenu } from "@/api/system/menu";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Menu",
  components: { Treeselect, IconSelect },
  data() {
    return {
      // The mask layer
      loading: true,
      // Menu table tree data
      menuList: [],
      // Menu tree options
      menuOptions: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // Display status data dictionary
      visibleOptions: [],
      // Menu state data dictionary
      statusOptions: [],
      // Query parameters
      queryParams: {
        menuName: undefined,
        visible: undefined
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        menuName: [
          { required: true, message: "Menu name cannot be empty", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "Menu order cannot be empty", trigger: "blur" }
        ],
        path: [
          { required: true, message: "The routing address cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_show_hide").then(response => {
      this.visibleOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    // Choose the icon
    selected(name) {
      this.form.icon = name;
    },
    /** Query menu list */
    getList() {
      this.loading = true;
      listMenu(this.queryParams).then(response => {
        this.menuList = this.handleTree(response.data, "menuId");
        this.loading = false;
      });
    },
    /** Transform menu data structures */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      };
    },
    /** Query the menu drop-down tree structure */
    getTreeselect() {
      listMenu().then(response => {
        this.menuOptions = [];
        const menu = { menuId: 0, menuName: 'The main categories', children: [] };
        menu.children = this.handleTree(response.data, "menuId");
        this.menuOptions.push(menu);
      });
    },
    // Display status dictionary translations
    visibleFormat(row, column) {
      if (row.menuType == "F") {
        return "";
      }
      return this.selectDictLabel(this.visibleOptions, row.visible);
    },
    // Menu state dictionary translation
    statusFormat(row, column) {
      if (row.menuType == "F") {
        return "";
      }
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
        menuId: undefined,
        parentId: 0,
        menuName: undefined,
        icon: undefined,
        menuType: "M",
        orderNum: undefined,
        isFrame: "1",
        visible: "0",
        status: "0"
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
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.menuId;
      }
      this.open = true;
      this.title = "Add menu";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      getMenu(row.menuId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify the menu";
      });
    },
    /** The submit button */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.menuId != undefined) {
            updateMenu(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addMenu(this.form).then(response => {
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
      this.$confirm('Confirm whether to delete the name is"' + row.menuName + '"Data item?', "warning", {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning"
        }).then(function() {
          return delMenu(row.menuId);
        }).then(() => {
          this.getList();
          this.msgSuccess("Delete the success");
        }).catch(function() {});
    }
  }
};
</script>