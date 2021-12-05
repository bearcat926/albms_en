<template>
  <div class="aui-padded-15">
    <div class="flex flex_start aui-padded-b-10">
      <p class="font-size-20 fw_bold">The announcement to inform</p>
      <div class="aui-padded-l-15 flex" v-hasPermi="['system:notice:add']">
        <div class="flex">
          <div
            class="addnew bg-theme text-white border-radius font-size-14"
            @click="handleAdd"
          >
            new
          </div>
        </div>
      </div>
    </div>
    <div class="table_data">
      <div class="bg-white bg_shadow bg-radius">
        <div class="aui-padded-15 aui-padded-t-10 aui-padded-b-0">
          <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            label-width="100px"
          >
            <el-row>
              <el-col :span="20">
                <el-form-item label="The announcement title" prop="noticeTitle">
                  <el-input
                    v-model="queryParams.noticeTitle"
                    placeholder="Please enter the announcement title"
                    clearable
                    size="small"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="The operator" prop="createBy">
                  <el-input
                    v-model="queryParams.createBy"
                    placeholder="Please enter operator"
                    clearable
                    size="small"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="type" prop="noticeType">
                  <el-select
                    v-model="queryParams.noticeType"
                    placeholder="The announcement type"
                    clearable
                    size="small"
                  >
                    <el-option
                      v-for="dict in typeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item class="aui-padded-l-15">
                  <el-button
                    type="primary"
                    size="mini"
                    @click="handleQuery"
                    class="find_btn"
                    >The query</el-button
                  >
                  <el-button size="mini" @click="resetQuery" class="reset_btn"
                    >reset</el-button
                  >
                </el-form-item>
           
              </el-col>

              <el-col :span="4">
                <div class>
                  <div class="flex export_box" style="float: right">
                    <div class="text-black font-size-14 aui-padded-r-10">
                      A total of{{ total }}The data
                    </div>
                  </div>
                </div>
               
              </el-col>
            </el-row>
          </el-form>
        </div>

        <el-table
          v-loading="loading"
          :data="noticeList"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            label="The serial number"
            align="center"
            prop="noticeId"
            width="100"
          />
          <el-table-column
            label="The announcement title"
            align="center"
            prop="noticeTitle"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="The announcement type"
            align="center"
            prop="noticeType"
            :formatter="typeFormat"
           
          />
          <el-table-column
            label="state"
            align="center"
            prop="status"
            :formatter="statusFormat"
            
          />
          <el-table-column
            label="The creator"
            align="center"
            prop="createBy"
           
          />
          <el-table-column
            label="Creation time"
            align="center"
            prop="createTime"
            
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="operation"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:notice:edit']"
                style="color: #5ecc59 !important; font-size: 16px"
                title="The editor"
              ></el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                style="color: #e8522a !important; font-size: 16px"
                v-hasPermi="['system:notice:remove']"
                title="delete"
              ></el-button>
              <!-- <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:notice:edit']"
                >Modify the</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:notice:remove']"
                >delete</el-button
              > -->
            </template>
          </el-table-column>
        </el-table>
      </div>

      <pagination
        v-show="total > 20"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- Add or modify announcement dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="The announcement title" prop="noticeTitle">
              <el-input
                v-model="form.noticeTitle"
                placeholder="Please enter the announcement title"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The announcement type" prop="noticeType">
              <el-select v-model="form.noticeType" placeholder="Please select a">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="state">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="content">
              <Editor v-model="form.noticeContent" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="padding-top: 20px">
        <el-button type="primary" @click="submitForm">indeed set</el-button>
        <el-button @click="cancel">take eliminate</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listNotice,
  getNotice,
  delNotice,
  addNotice,
  updateNotice,
  exportNotice,
} from "@/api/system/notice";
import Editor from "@/components/Editor";

export default {
  name: "Notice",
  components: {
    Editor,
  },
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
      // Announcement form data
      noticeList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // Type data dictionary
      statusOptions: [],
      // State data dictionary
      typeOptions: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        noticeTitle: undefined,
        createBy: undefined,
        status: undefined,
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        noticeTitle: [
          { required: true, message: "The announcement title cannot be empty", trigger: "blur" },
        ],
        noticeType: [
          { required: true, message: "The bulletin type cannot be empty", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_notice_status").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_notice_type").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** Querying the Bulletin List */
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then((response) => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Bulletin status dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Bulletin status dictionary translation
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.noticeType);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Reset the form
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: "0",
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
      this.ids = selection.map((item) => item.noticeId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add the announcement";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids;
      getNotice(noticeId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify bulletin";
      });
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.noticeId != undefined) {
            updateNotice(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addNotice(this.form).then((response) => {
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
      const noticeIds = row.noticeId || this.ids;
      this.$confirm(
        'Confirm whether to delete the bulletin"' + noticeIds + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delNotice(noticeIds);
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