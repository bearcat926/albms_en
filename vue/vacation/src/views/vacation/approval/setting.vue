<template>
  <div>
    <div class="holiday">
      <!-- At the bottom of the form -->
      <div class="aui-padded-15 aui-padded-t-0 table_data">

<!--        The new button-->
        <div class="flex flex_start aui-padded-b-10 aui-margin-t-10">
          <p class="font-size-20">Approval settings</p>
          <div class="aui-padded-l-10 flex">
            <div
              class="addnew bg-theme text-white border-radius font-size-14"
              @click="handleAdd"
              v-hasPermi="['vacation:holidayApproval:add']"
            >new</div>
          </div>
        </div>

<!--        The search box + The list of-->
        <div class="bg-white bg_shadow bg-radius">
          <div class="aui-padded-15 aui-padded-t-10 aui-padded-b-0">
            <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
              <el-row>
                <el-col :span="20">
                  <el-form-item label="Holiday types:" prop="holidayTypeId">
                    <el-select
                      v-model="queryParams.holidayTypeId"
                      placeholder="Please select a"
                      clearable
                      filterable
                      size="small"
                      style="width: 200px"
                    >
                      <el-option
                        v-for="dict in holidayTypeList"
                        :key="dict.dictCode"
                        :label="dict.dictLabel"
                        :value="dict.dictCode"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <div class>
                    <div class="flex export_box" style="float:right">
                      <div class="text-black font-size-14 aui-padded-r-10">A total of{{total}}The data</div>
                    </div>
                    <el-form-item style="margin-top:5px">
                      <el-button type="primary" size="mini" @click="handleQuery" class="find_btn">The query</el-button>
                      <el-button size="mini" @click="resetQuery" class="reset_btn">reset</el-button>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <el-table v-loading="loading" :data="holidayApprovalList"   @selection-change="handleSelectionChange" stripe >
            <el-table-column type="selection" width="80" align="center" />
            <el-table-column label="Current Approval Number" prop="currentApprovedIndex" align="center" />
            <el-table-column label="Role of the person awaiting approval" prop="roleName" align="center" />
            <el-table-column label="Approver role" prop="approvedRoleName" align="center"/>
            <el-table-column label="Whether it is the last level" prop="nextApprovalId" align="center" >
              <template slot-scope="scope">
                <span v-if="scope.row.nextApprovalId == 0">is</span>
                <span v-if="scope.row.nextApprovalId != 0">no</span>
              </template>
            </el-table-column>
            <el-table-column label="operation" align="center" width="200">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  style="color: #E8522A !important;font-size:16px"
                  v-hasPermi="['vacation:holidayApproval:remove']"
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
          @pagination="getHolidayApprovalList"
        />

        <!-- Add or modify Windows -->
        <el-dialog class :title="title" :visible.sync="open" width="40%" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="Holiday types:">
                  <el-select
                    v-model="form.holidayTypeId"
                    placeholder="Please select a"
                    clearable
                    style="width:100%"
                  >
                    <el-option
                      v-for="dict in holidayTypeList"
                      :key="dict.dictCode"
                      :label="dict.dictLabel"
                      :value="dict.dictCode"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="Role of the person awaiting approval:">
                  <el-select
                    v-model="form.roleId"
                    clearable
                    filterable
                    placeholder="Please enter your name"
                    :filter-method="brandKeyChange"
                    style="width:100%"
                    @change="fnEdit"
                  >
                    <el-option
                      v-for="role in roleList"
                      :key="role.roleId"
                      :label="role.roleName"
                      :value="role.roleId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="The approver:">
                  <el-select
                    v-model="form.approvedRoleId"
                    clearable
                    filterable
                    placeholder="Please enter your name"
                    :filter-method="brandKeyChange"
                    style="width:100%"
                    @change="fnEdit"
                  >
                    <el-option
                      v-for="role in roleList"
                      :key="role.roleId"
                      :label="role.roleName"
                      :value="role.roleId"
                    ></el-option>
                  </el-select>
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
    </div>
  </div>
</template>

<script>
import {
  getHolidayApprovalInfo,
  holidayApprovalAdd,
  holidayApprovalList,
  holidayApprovalRemove,
  holidayApprovalUpdate,
  roleList
} from '../../../api/vacation/approval'
import store from '../../../store'

export default {
  name: "approvalSetting",
  components: {
  },
  data() {
    return {
      dialogVisible: false,
      // The holiday list
      title: "",
      open: false,
      loading: false,
      total: 0,
      dateRange: "",
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: 1,
        holidayTypeId: undefined,
      },
      // The form parameter
      form: {
      },
      // Holiday types
      holidayTypeList: [],
      ageList: [],
      holidayApprovalList: [],
      roleList: [],
      rules: {
        holidayInstruction: [
          { required: true, message: "Leave note cannot be empty", trigger: "blur" },
        ],
      },
      // The customer details
      textarea: "",
      holidayInfo: {
      },
      infoHoliday: "",
      recordForm: {
        holidayId: undefined,
        type: 1,
        content: "",
      },
    };
  },
  created() {
    this.getHolidayApprovalList();
    this.getRoleList();
    this.getDicts("holiday_type").then((response) => {
      this.holidayTypeList = response.data;
    });
  },
  methods: {
    // Select data in multiple boxes
    handleSelectionChange(e) {
      this.ids = selection.map((item) => item.roleId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // Approver selection
    fnEdit(vId) {
      this.form.parentHolidayId = vId;
    },
    // View vacation details
    handleDataScope(row) {
      this.infoHoliday = row;
      this.infoCheck();
    },
    infoCheck() {
      const id = this.infoHoliday.holidayId;
      getHolidayApprovalInfo(id).then((response) => {
        if (response.code == 200) {
          this.holidayInfo = response.data;
          this.dialogVisible = true;
        }
      });
    },
    // Add ok button
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.holidayId != undefined) {
            holidayApprovalUpdate(this.form).then((response) => {
              if (response.code == 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getHolidayApprovalList();
              }
            });
          } else {
            if (this.form.roleId !== this.form.approvedRoleId) {
              holidayApprovalAdd(this.form).then((response) => {
                if (response.code == 200) {
                  this.msgSuccess("New success");
                  this.open = false;
                  this.getHolidayApprovalList();
                }
              });
            } else {
              this.msgError("The approved role and the approved role must be different!");
            }

          }
        }
      });
    },
    // Added cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    handleDelete(row) {
      const ids = row.holidayApprovalId;
      this.$confirm('Confirm whether to delete this item?', "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return holidayApprovalRemove(ids);
        })
        .then((res) => {
          console.log(res);
          this.getHolidayApprovalList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
    /** New Button Operation */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = 'Add vacation approval Settings'
    },
    /** Modify button operation */
    // handleUpdate(row) {
    //   this.reset();
    //   const id = row.holidayId;
    //   getHolidayApprovalInfo(id).then((response) => {
    //     let data = {
    //         holidayTypeId: response.data.holidayTypeId,
    //         currentApprovedIndex: response.data.currentApprovedIndex,
    //     }
    //     userListForVacation(data).then((response2) => {
    //       if (response2.code == 200) {
    //         let expect = {
    //           holidayId: response.data.holidayId,
    //           holidayTypeId: response.data.holidayTypeId,
    //           // Possible missing parameters
    //         }
    //         this.form = expect
    //         this.userList = response2.data;
    //         this.title = "Modifying vacation Information";
    //         this.open = true;
    //       }
    //     })
    //   });
    // },
    brandKeyChange(inputKey){

    },
    // Reset the form
    reset() {
      this.addressForm = [];
      this.form = {
        status: 1,
        holidayName: undefined,
        holidaySex: undefined,
        holidayPhone: undefined,
        holidayAge: undefined,
        passportNumber: undefined,
        province: undefined,
        city: undefined,
        district: undefined,
        address: undefined,
        shopId: undefined,
        remark: undefined,
        parentHolidayId: undefined,

        // new
        sourceType:undefined,
        sourceTypeName:undefined
      };
      this.resetForm("form");
    },
    // Querying the Holiday List
    getHolidayApprovalList() {
      this.loading = true;
      holidayApprovalList(this.queryParams).then((response) => {
        if (response.code == 200) {
          this.holidayApprovalList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      });
    },
    // Querying the Role List
    getRoleList() {
      roleList().then((response) => {
        if (response.code == 200) {
          this.roleList = response.data;
        }
      });
    },
    // Reset the form
    resetQuery() {
      this.dateRange = "";
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        status: 1,
        holidayName: undefined,
        holidayPhone: undefined,
        sourceType: undefined,
        userId: undefined,
        holidayLevel: undefined,
        minConsumptionNum: undefined,
        maxConsumptionNum: undefined,
        shopId: undefined,
        province: undefined,
        city: undefined,
        district: undefined,
        address: undefined,
        beginTime: undefined,
        endTime: undefined,
      };
      this.getHolidayApprovalList();
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.beginTime = this.dateRange[0]; //The start time
      this.queryParams.endTime = this.dateRange[1]; //The end of time
      this.getHolidayApprovalList();
    },
    // Close the page
    handleClose(done) {
      done();
    },
  },
};
</script>

<style  >
.holiday .top_four {
  height: 250px;
}
.holiday .top_four_item {
  width: 24%;
  height: 100%;
  border-radius: 10px;
  position: relative;
}
.holiday .top_four_item_text {
  position: absolute;
  left: 0;
  top: 0;
}

.el-dialog__header {
  border-bottom: 1px solid #eeeeee;
}
.el-dialog__body {
  padding: 20px;
  color: #222;
}
.el-dialog:not(.is-fullscreen) {
  margin-top: 0 !important;
}

.holiday .line_dash {
  border-top: 1px dashed #eeeeee;
  width: 88%;
}
</style>

