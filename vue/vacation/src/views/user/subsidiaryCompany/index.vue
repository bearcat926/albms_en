<template>
  <div class="app-container">
    <div class>
      <chart />
    </div>
    <div class="title_box aui-padded-t-15">
      <p class="font-size-20">Subsidiary management</p>
      <div
        class="addnew bg-theme text-white aui-margin-l-10 border-radius font-size-14"
        @click="handleAdd"
      >new</div>
    </div>
    <div class="table_data">
      <div class="bg-white bg_shadow bg-radius">
        <div class="aui-padded-15 aui-padded-t-10 aui-padded-b-0">
          <el-form :model="queryParams" ref="queryForm" :inline="true">
            <el-row>
              <el-col :span="20">
                <el-form-item label-width="130px" label="Name of Subsidiary/code:" prop="deptName">
                  <el-input
                    v-model="queryParams.deptName"
                    placeholder="Please enter a subsidiary name or code"
                    clearable
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label-width="150px" label="head/head电话:" prop="leader">
                  <el-input
                    v-model="queryParams.leader"
                    placeholder="Please enter the name or phone number of the responsible person"
                    clearable
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="area:">
                  <el-cascader
                  style="width: 240px"
                    :options="options"
                    :props="props"
                    v-model="selectedOptions"
                    @change="handleChange"
                    clearable
                    filterable
                  ></el-cascader>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" size="medium" @click="handleQuery">The query</el-button>
                  <el-button size="medium" @click="resetQuery">reset</el-button>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <div class>
                  <div class="flex export_box" style="float:right">
                    <div class="text-black font-size-14 aui-padded-r-10">A total of{{total}}The data</div>
                    <el-button size="mini" @click="handleImport">
                      <img src="../../../assets/image/daoru.png" alt />
                      <p style="margin-top:2px">The import</p>
                    </el-button>
                    <el-button size="mini" @click="handleExport" v-hasPermi="['system:dept:subsidiaryCompanyListExport']">
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
          :data="allSubsidiaryCompany"
          @selection-change="handleSelectionChange"
          :cell-class-name="getCellClass"
          header-cell-class-name="headerColor"
          stripe
        >
          <el-table-column type="selection" width="80" align="center" />
          <el-table-column label="code" align="center" prop="deptCode" width="120"/>
          <el-table-column label="The name of the company" align="center" prop="deptName" :show-overflow-tooltip="true" />
          <el-table-column label="head" align="center" prop="leader" :show-overflow-tooltip="true" />
          <el-table-column
            label="Responsible person telephone"
            align="center"
            prop="leaderPhone"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="Number of stores" align="center" width="120">
            <template slot-scope="scope">
              <span
              v-if="scope.row.shopNum>0"
                class="text-theme udline"
                @click="checkShop(scope.row)"
              >{{scope.row.shopNum}}</span>
               <span
               v-else
                class="text-black "
              >{{scope.row.shopNum}}</span>
            </template>
          </el-table-column>
          <el-table-column label="area" :show-overflow-tooltip="true">
            <template
              slot-scope="scope"
            >{{ scope.row.province }} {{ scope.row.city }} {{ scope.row.district }}</template>
          </el-table-column>
          <el-table-column label="Detailed address" align="center" prop="address" width="160">
            <template slot-scope="scope">
              <span>{{ scope.row.address }}</span>
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
                @click="handleShow(scope.row)"
                v-hasPermi="['system:dept:subsidiaryCompanyInfo']"
                title="Check the details"
              >
                <img src="../../../assets/image/chaxun.png" alt />
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                style="color:#5ECC59 !important;font-size:16px"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:dept:edit']"
                title="The editor"
              ></el-button>
              <el-button
                v-if="scope.row.userId !== 1"
                size="mini"
                type="text"
                icon="el-icon-delete"
                style="color: #E8522A !important;font-size:16px"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:dept:remove']"
                title="delete"
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
        @pagination="subsidiaryCompanyList"
      />
    </div>

    <!-- Store the data -->
    <el-dialog class="table_data" :title="titleShop" :visible.sync="open1" width="65%" append-to-body>
      <el-table :data="shopData" header-cell-class-name="headerColor" stripe>
        <el-table-column label="code" align="left" prop="deptCode" />
        <el-table-column label="The name of the store" align="left" prop="deptName" :show-overflow-tooltip="true" />
        <el-table-column label="Store type" align="left" prop="deptType" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{ scope.row.deptType == '1' ? 'stores':'Its franchisees' }}</template>
        </el-table-column>

        <el-table-column label="The manager" align="left" prop="leader" :show-overflow-tooltip="true" />
        <el-table-column
          label="The manager phone"
          align="left"
          prop="phone"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="Store the phone" align="left" prop="deptPhone"  />
        <el-table-column label="area" :show-overflow-tooltip="true" width='200'>
          <template
            slot-scope="scope"
          >
          <div style="width:100%">
          {{ scope.row.province }} {{ scope.row.city }} {{ scope.row.district }}</div></template>
        </el-table-column>
        <el-table-column
          label="Detailed address"
          :show-overflow-tooltip="true"
          align="left"
          prop="address"
          width="160"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.address }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- Add or modify parameter configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="40%" append-to-body>
      <div class="info_box" v-if="title == 'Subsidiary information'">
        <div class="text-grayer flex aui-padded-b-10">
          <div>Basic information</div>
          <div class="line_dash"></div>
        </div>

        <el-row>
          <el-col :span="12">
            <div class="info_index">
              <p>code:</p>
              <span v-text="form.deptCode"></span>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="info_index">
              <p>The name of the:</p>
              <span v-text="form.deptName"></span>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="info_index">
              <p>head:</p>
              <span v-text="form.leader"></span>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="info_index">
              <p>Responsible person telephone:</p>
              <span v-text="form.leaderPhone"></span>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="info_index">
              <p>In the area:</p>
              <span>{{form.province}}{{form.city}}{{form.district}}</span>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="info_index">
              <p>Detailed address:</p>
              <span v-text="form.address"></span>
            </div>
          </el-col>
        </el-row>
        <div class="text-grayer flex aui-padded-b-10">
          <div>Data statistics</div>
          <div class="line_dash"></div>
        </div>
        <el-row>
          <el-col :span="12">
            <div class="info_index">
              <p>Add the time:</p>
              <span v-text="form.createDate"></span>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="info_index">
              <p>stores:</p>
              <span v-text="form.totalNum"></span>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="info_index">
              <p>members:</p>
              <span v-text="form.memberNum"></span>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="info_index">
              <p>Associate member:</p>
              <span v-text="form.associateMemberNum"></span>
            </div>
          </el-col>
        </el-row>
        <div class="text-grayer flex aui-padded-b-10">
          <div>stores</div>
          <div class="line_dash"></div>
        </div>
        <el-row justify="start" type="flex" :gutter="20">
          <el-col>
            <span class="shopName" v-for="(item,i) in form.shopList" :key="i">{{item.deptName}}</span>
          </el-col>
        </el-row>
      </div>
      <el-form v-if="title != 'Subsidiary information'" ref="form" :model="form" :rules="rules" label-width="120px">
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
            <el-form-item label="code:" prop="deptCode">
              <el-input v-model="form.deptCode" placeholder="Please enter the subsidiary code" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The name of the:" prop="deptName">
              <el-input v-model="form.deptName" placeholder="Please enter a subsidiary name" />

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="head:" prop="leader">
              <el-input v-model="form.leader" placeholder="Please enter the responsible person name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Responsible person telephone:" prop="leaderPhone">
              <el-input v-model="form.phone" placeholder="Please enter the responsible person telephone number" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="In the area:">
              <el-cascader
                :class="{'cascader' : selectedName != ''} "
                :options="options"
                :props="props"
                v-model="selectedOptions1"
                @change="handleChange1"
                :placeholder="selectedName != '' ? selectedName : 'Please select a'"
                clearable
                filterable
                style="width:100%"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Detailed address:" prop="address">
              <el-input v-model="form.address" placeholder="Please enter your full address" type="text" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item class label="According to the sorting:" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="Please enter the serial number" oninput="value=value.replace(/^\.+|[^\d.]/g,'')"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <span
              class="font-size-14 text-graybc aui-padded-l-10"
              style="line-height:36px"
            >order,Can be used to control subsidiary sorting</span>
          </el-col>
        </el-row>
        <el-row>


        </el-row>
      </el-form>
      <div v-if="title != 'Subsidiary information'" slot="footer" class="dialog-footer">
        <el-button @click="cancel">take eliminate</el-button>
        <el-button type="primary" @click="submitForm">indeed set</el-button>
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
          <el-checkbox v-model="upload.updateSupport" />Whether to update existing subsidiary data
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
import chart from "../chart/index";
import { regionData, CodeToText } from "element-china-area-data";
import { getToken } from "@/utils/auth";
import {
  subsidiaryCompanyList,
  subsidiaryCompanyListExport,
  importTemplate,
  subsidiaryCompanyInfo,
  getShop,
} from "@/api/user/index";
import {
  updateDept,
  getDept,
  addDept,
  delDept,
  listDept,
  treeselect,
} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "subsidiaryCompany",
  components: { Treeselect, chart },
  data() {
    return {
      titleShop: "",
      open1: false,
      shopData:[],
      props: {},
      selectedOptions: [],
      options: regionData,
      selectedName: "",
      selectedOptions1: [], // Modify the value of the selector
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
      // department
      allSubsidiaryCompany: null,
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
        url:
          process.env.VUE_APP_BASE_API +
          "/system/dept/subsidiaryCompanyListImport",
      },
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptName: undefined,
        leader: undefined,
        province: undefined,
        city: undefined,
        district: undefined,
      },
      // Form validation
      rules: {
        orderNum: [
          { required: true, message: "The sort cannot be empty", trigger: "blur" },
        ],
        parentId: [
          { required: true, message: "The upper department cannot be empty", trigger: "blur" },
        ],
        deptCode: [
          { required: true, message: "The subsidiary code cannot be empty", trigger: "blur" },
        ],
        deptName: [
          { required: true, message: "The subsidiary name cannot be empty", trigger: "blur" },
        ],
        leader: [
          { required: true, message: "The person in charge cannot be empty", trigger: "blur" },
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
    this.subsidiaryCompanyList();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then((response) => {
      this.sexOptions = response.data;
    });
    this.getConfigKey("sys.user.initPassword").then((response) => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    checkShop(row) {
      const ids = row.deptId;
      getShop(ids).then((response) => {
        if (response.code == 200) {
          this.shopData = response.data;
          this.titleShop=row.deptName
          this.open1 = true;
        }
      });
    },
    handleChange() {
      var loc = "";
      let params = ["province", "city", "district"];
      if (this.selectedOptions.length > 0) {
        for (let i = 0; i < this.selectedOptions.length; i++) {
          this.queryParams[params[i]] = CodeToText[this.selectedOptions[i]];
          loc += CodeToText[this.selectedOptions[i]];
        }
      } else {
        for (let i = 0; i < params.length; i++) {
          this.queryParams[params[i]] = undefined;
        }
      }
    },
    handleChange1() {
      var loc = "";
      let params = ["province", "city", "district"];
      if (this.selectedOptions1.length > 0) {
        for (let i = 0; i < this.selectedOptions1.length; i++) {
          this.form[params[i]] = CodeToText[this.selectedOptions1[i]];
          loc += CodeToText[this.selectedOptions1[i]];
        }
      } else {
        for (let i = 0; i < params.length; i++) {
          this.form[params[i]] = undefined;
        }
      }
    },
    getCellClass({ row, column, rowIndex, columnIndex }) {
      if (columnIndex == 5) {
        return "underB";
      }
    },
    subsidiaryCompanyList() {
      let data = {};
      this.queryParams.deptName
        ? (data.deptName = this.queryParams.deptName)
        : "";
      this.queryParams.leader ? (data.leader = this.queryParams.leader) : "";
      this.queryParams.province
        ? (data.province = this.queryParams.province)
        : "";
      this.queryParams.city ? (data.city = this.queryParams.city) : "";
      this.queryParams.district
        ? (data.district = this.queryParams.district)
        : "";
      this.queryParams.pageNum ? (data.pageNum = this.queryParams.pageNum) : "";
      this.queryParams.pageSize
        ? (data.pageSize = this.queryParams.pageSize)
        : "";
      subsidiaryCompanyList(data).then((response) => {
        this.allSubsidiaryCompany = response.rows;
        this.total = response.total;
        this.loading = false;
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
      this.subsidiaryCompanyList();
    },

    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Reset the form
    reset() {
      this.form = {
        deptName: undefined,
        leader: undefined,
        address: undefined,
        city: undefined,
        deptId: undefined,
        leaderPhone: undefined,
        memberNum: undefined,
        associateMemberNum: undefined,
        province: undefined,
        totalNum: undefined,
        district: undefined,
        deptCode: undefined,
        email: undefined,
        shopList: [],
        type: 1,
      };
      this.selectedName = "";
      this.selectedOptions1 = [];
      this.resetForm("form");
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.page = 1;
      this.subsidiaryCompanyList();
    },
    /** Reset button operation */
    resetQuery() {
      this.selectedOptions = [];
      this.queryParams = {
        province: undefined,
        totalNum: undefined,
        district: undefined,
      };
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
      this.subsidiaryCompanyList();

      listDept().then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
        this.open = true;
        this.title = "Add a subsidiary";
      });


    },
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
    /** View button action */
    handleShow(row) {
      this.reset();
      const deptId = row.deptId;
      subsidiaryCompanyInfo(deptId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Subsidiary information";
      });
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const deptId = row.deptId;
      listDept().then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");



        getDept(deptId).then((response) => {
          console.log(response);
          this.form = response.data;
          this.selectedName = [
            response.data.province,
            response.data.city,
            response.data.district,
          ].join("/");
          this.open = true;
          this.title = "Editing subsidiary";
        });
      });
    },

    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {

          if (this.title == "Editing subsidiary") {
            updateDept(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.subsidiaryCompanyList();
              }
            });
          } else {
            this.form.type = 1;
            addDept(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("New success");
                this.open = false;
                this.subsidiaryCompanyList();
              }
            });
          }
        }
      });
    },
    /** Delete button operation */
    handleDelete(row) {
      const deptId = row.deptId || this.ids;
      this.$confirm('Confirm deleting the selected data?', "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return delDept(deptId);
        })
        .then(() => {
          this.subsidiaryCompanyList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("Verify that all data items are exported?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return subsidiaryCompanyListExport(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    /** Import button operation */
    handleImport() {
      this.upload.title = "Subsidiary import";
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
      this.upload.isUploading = true;
    },
    // The file is successfully uploaded. Procedure
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "Import the results", { dangerouslyUseHTMLString: true });
      this.subsidiaryCompanyList();
    },
    // Submit and upload files
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>
<style lang="scss" scoped>
$color: #0d1444;

/deep/.cascader {
  /deep/input::-webkit-input-placeholder {
    color: $color !important;
  }
  /deep/input::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    color: $color !important;
  }
  /deep/input:-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    color: $color !important;
  }
  /deep/input:-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    color: $color !important;
  }
}

@import "@/assets/styles/new.scss";
.app-container1 {
  box-shadow: 0px 0px 8px rgba($color: #000000, $alpha: 0.2);
  border-radius: 6px;
  padding-top: 12px;
  overflow: hidden;
  //   width: 96%;
  //   margin-left: 2%;
  .from {
    width: 96%;
    margin-left: 2%;
  }

  .left_shu {
    font-size: 0.8em;
    @include leftShu(2em, 2em);
  }
}
</style>

