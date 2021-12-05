<template>
  <div class="aui-padded-15">
    <div class="flex flex_start aui-padded-b-10">
      <p class="font-size-20 fw_bold">Bulletin Viewing Records</p>
    </div>
    <div class="table_data">
      <div class="bg-white bg_shadow bg-radius">
        <div class="aui-padded-15 aui-padded-t-10 aui-padded-b-0">
          <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            label-width="68px"
          >
            <el-row>
              <el-col :span="20">
                <el-form-item label="Announcement of the name" prop="noticeName">
                  <el-input
                    v-model="queryParams.noticeName"
                    placeholder="Please enter a bulletin name"
                    clearable
                    size="small"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="Mobile phone no." prop="userPhone">
                  <el-input
                    v-model="queryParams.userPhone"
                    placeholder="Please enter your mobile phone number"
                    clearable
                    size="small"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="Creation time" prop="createDate">
                  <el-date-picker
                    clearable
                    size="small"
                    style="width: 200px"
                    v-model="queryParams.createDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="Select creation time"
                  >
                  </el-date-picker>
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
          :data="noticeRecordList"
          @selection-change="handleSelectionChange"
          stripe 
        >
          <!-- <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="The userid" align="center" prop="userId" /> -->
          <el-table-column label="The announcement title" align="center" prop="noticeName" />
          <el-table-column label="The creator" align="center" prop="userName" />
          <el-table-column label="Mobile phone no." align="center" prop="userPhone" />
          <el-table-column label="Creation time" align="center" prop="createDate">
            <template slot-scope="scope">
              <span>{{ scope.row.createDate }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import {
  listNoticeRecord,
} from "@/api/system/noticeRecord";

export default {
  name: "NoticeRecord",
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
      // Bulletin view record table data
      noticeRecordList: [],
      // Pop-up layer title
      title: "",
      // Whether to display the pop-up layer
      open: false,
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        sysNoticeId: undefined,
        createDate: undefined,
        userPhone: undefined,
      },
      // The form parameter
      form: {},
      // Form validation
      rules: {
        userId: [
          { required: true, message: "The useridCan't be empty", trigger: "blur" },
        ],
        sysNoticeId: [
          { required: true, message: "The announcementidCan't be empty", trigger: "blur" },
        ],
        createDate: [
          { required: true, message: "The creation time cannot be empty", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Query bulletin View the record list */
    getList() {
      this.loading = true;
      listNoticeRecord(this.queryParams).then((response) => {
        console.log(response.rows);
        this.noticeRecordList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        id: undefined,
        userId: undefined,
        sysNoticeId: undefined,
        createDate: undefined,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** New Button Operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add bulletin viewing records";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getNoticeRecord(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify bulletin viewing records";
      });
    },
    /** The submit button */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateNoticeRecord(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addNoticeRecord(this.form).then((response) => {
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
      const ids = row.id || this.ids;
      this.$confirm(
        'Confirm deleting the bulletin Check record number is"' + ids + '"Data item?',
        "warning",
        {
          confirmButtonText: "determine",
          cancelButtonText: "cancel",
          type: "warning",
        }
      )
        .then(function () {
          return delNoticeRecord(ids);
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
      this.$confirm("Whether you are sure to export all bulletin view record data items?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return exportNoticeRecord(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>