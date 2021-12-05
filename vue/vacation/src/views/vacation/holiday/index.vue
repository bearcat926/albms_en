<template>
  <div>
    <div class="holiday">
      <!-- At the bottom of the form -->
      <div class="aui-padded-15 aui-padded-t-0 table_data">

<!--        The new button-->
        <div class="flex flex_start aui-padded-b-10 aui-margin-t-10">
          <p class="font-size-20">Vacation Management</p>
          <div class="aui-padded-l-10 flex">
            <div
              class="addnew bg-theme text-white border-radius font-size-14"
              @click="handleAdd"
              v-hasPermi="['vacation:holiday:add']"
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
                  <el-form-item label="State of the holiday:" prop="status">
                    <el-select
                      v-model="queryParams.status"
                      placeholder="Please select a"
                      clearable
                      filterable
                      size="small"
                      style="width: 200px"
                    >
                      <el-option
                        v-for="dict in statusList"
                        :key="dict.value"
                        :label="dict.name"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="time:">
                    <el-date-picker
                      v-model="dateRange"
                      style="width: 240px"
                      value-format="yyyy-MM-dd"
                      type="daterange"
                      range-separator="-"
                      start-placeholder="Start date"
                      end-placeholder="End date"
                    ></el-date-picker>
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
          <el-table v-loading="loading" :data="holidayList"   @selection-change="handleSelectionChange" stripe >
            <el-table-column type="selection" width="80" align="center" />
            <el-table-column label="The applicant" prop="proposerName" align="center" />
            <el-table-column label="Current approver" prop="currentApproverName" align="center"/>
            <el-table-column label="Holiday types" prop="typeName" align="center" />
            <el-table-column label="Start time of vacation" prop="holidayStartDate" align="center" />
            <el-table-column label="End of vacation" prop="holidayEndDate" align="center" />
<!--            <el-table-column label="The vacation time" prop="holidayDuration" align="center" />-->
            <el-table-column label="Current Approval Status" align="center">
              <template slot-scope="scope">
                <span v-if="scope.row.status == 0">In the examination and approval</span>
                <span v-if="scope.row.status == 1">Have been through</span>
                <span v-if="scope.row.status == 2">Has been rejected</span>
              </template>
            </el-table-column>
            <el-table-column label="operation" align="center" width="200">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="handleDataScope(scope.row)"
                  style="font-size:16px"
                  v-hasPermi="['vacation:holiday:detail']"
                  title="Check the details"
                >
                  <img src="../../../assets/image/chaxun.png" alt />
                </el-button>
<!--                <el-button-->
<!--                  size="mini"-->
<!--                  type="text"-->
<!--                  icon="el-icon-edit"-->
<!--                  @click="handleUpdate(scope.row)"-->
<!--                  v-hasPermi="['vacation:holiday:edit']"-->
<!--                  style="color:#5ECC59 !important;font-size:16px"-->
<!--                  title="The editor"-->
<!--                ></el-button>-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-if="scope.row.status == 1"
                  style="color: #E8522A !important;font-size:16px"
                  v-hasPermi="['vacation:holiday:remove']"
                  title="Terminate his/her leave"
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
          @pagination="getHolidayList"
        />

        <!-- Add a window -->
        <el-dialog class :title="title" :visible.sync="open" width="40%" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="Holiday types:" prop="holidayTypeId">
                  <el-select
                    v-model="form.holidayTypeId"
                    @change="selectChanged"
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
                <el-form-item label="The start time:" prop="holidayStartDate">
                  <el-date-picker
                    v-model="form.holidayStartDate"
                    style="width: 240px"
                    format="yyyy-MM-dd HH:mm:ss"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetime"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="The end of time:" prop="holidayEndDate">
                  <el-date-picker
                    v-model="form.holidayEndDate"
                    style="width: 240px"
                    format="yyyy-MM-dd HH:mm:ss"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetime"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="The approver:" prop="currentApproverId">
                  <el-select
                    v-model="form.currentApproverId"
                    clearable
                    filterable
                    placeholder="Please select approver"
                    :filter-method="brandKeyChange"
                    style="width:100%"
                    @change="fnEdit"
                  >
                    <el-option
                      v-for="dict in userList"
                      :key="dict.userId"
                      :label="dict.displayName"
                      :value="dict.userId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="Leave that:" prop="holidayInstruction">
              <el-input type="textarea" v-model="form.holidayInstruction"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">indeed set</el-button>
            <el-button @click="cancel">take eliminate</el-button>
          </div>
        </el-dialog>

        <!-- dashboard -->
        <el-dialog
          title="Holiday information"
          :visible.sync="dialogVisible"
          width="40%"
          :before-close="handleClose"
        >
          <div>
            <div class="text-grayer flex">
              <div>Holiday details</div>
              <div class="line_dash"></div>
            </div>
            <div class="aui-padded-15 font-size-14">
              <el-row>
                <el-col :span="12" class="aui-padded-b-10">
                  <span>The applicant:</span>
                  <span class="aui-padded-l-5 text-graybc">{{holidayInfo.proposerName}}</span>
                </el-col>
                <el-col :span="12" class="aui-padded-b-10">
                  <span>Holiday types:</span>
                  <span class="aui-padded-l-5 text-graybc">{{holidayInfo.typeName}}</span>
                </el-col>
<!--                <el-col :span="12" class="aui-padded-b-10">-->
<!--                  <span>The vacation time:</span>-->
<!--                  <span class="aui-padded-l-5 text-graybc">{{holidayInfo.holidayDuration}}</span>-->
<!--                </el-col>-->
                <el-col :span="24" class="aui-padded-b-10">
                  <span>Start time of vacation:</span>
                  <span class="aui-padded-l-5 text-graybc">{{holidayInfo.holidayStartDate}}</span>
                </el-col>
                <el-col :span="24" class="aui-padded-b-10">
                  <span>End of vacation:</span>
                  <span class="aui-padded-l-5 text-graybc">{{holidayInfo.holidayEndDate}}</span>
                </el-col>
                <el-col :span="12" class="aui-padded-b-10">
                  <span>Current Approval Status:</span>
                  <span class="aui-padded-l-5 text-graybc">{{holidayInfo.status == 0 ? 'In the examination and approval' : holidayInfo.status == 1 ? 'Have been through' : 'Has been rejected'}}</span>
                </el-col>
              </el-row>
            </div>
            <div class="text-grayer flex">
              <div>The examination and approval for details</div>
              <div class="line_dash"></div>
            </div>
            <div class="aui-padded-15 font-size-14 aui-padded-b-0">
              <el-table :data="holidayInfo.items" stripe >
                <el-table-column label="The approver" prop="approvedUserName" align="center" />
                <el-table-column label="The examination and approval status"align="center">
                  <template slot-scope="scope">
                    <span v-if="scope.row.status == 0">In the examination and approval</span>
                    <span v-if="scope.row.status == 1">Have been through</span>
                    <span v-if="scope.row.status == 2">Has been rejected</span>
                  </template>
                </el-table-column>
                <el-table-column label="The examination and approval time" prop="approveTime" align="center" />
                <el-table-column label="Instructions for examination and approval of" prop="approveInstruction" align="center" />
              </el-table>
            </div>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getHolidayInfo,
  holidayAdd,
  holidayList,
  holidayRemove,
  holidayUpdate,
  userListForVacation
} from '../../../api/vacation'

export default {
  name: "holiday",
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
        pageSize: 20,
        status: undefined,
        selectStartDate: undefined,
        selectEndDate: undefined,
        holidayTypeId: undefined
      },
      // The form parameter
      form: {
      },
      // List of users
      userList: [],
      // Holiday types
      holidayTypeList: [],
      statusList:[
        {
          name: 'In the examination and approval',
          value: 0
        },
        {
          name: 'Have been through',
          value: 1
        },
        {
          name: 'Be rejected',
          value: 2
        },
      ],
      ageList: [],
      holidayList: [],
      rules: {
        holidayTypeId: [
          { required: true, message: "The holiday type cannot be empty", trigger: "blur" },
        ],
        currentApproverId: [
          { required: true, message: "The approver cannot be empty", trigger: "blur" },
        ],
        holidayStartDate: [
          { required: true, message: "The start time cannot be empty", trigger: "blur" },
        ],
        holidayEndDate: [
          { required: true, message: "The end time cannot be empty", trigger: "blur" },
        ],
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
    this.getHolidayList();
    this.getDicts("holiday_type").then((response) => {
      this.holidayTypeList = response.data;
      console.log(this.holidayTypeList)
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
      getHolidayInfo(id).then((response) => {
        if (response.code == 200) {
          console.log(response.data)
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
            holidayUpdate(this.form).then((response) => {
              if (response.code == 200) {
                this.msgSuccess("Modify the success");
                this.open = false;
                this.getHolidayList();
              }
            });
          } else {
            holidayAdd(this.form).then((response) => {
              if (response.code == 200) {
                this.msgSuccess("New success");
                this.open = false;
                this.getHolidayList();
              }
            });
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
      const ids = row.holidayId;
      this.$confirm('Are you sure to cancel this holiday?', "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return holidayRemove(ids);
        })
        .then((res) => {
          this.getHolidayList();
          this.msgSuccess("Delete the success");
        })
        .catch(function () {});
    },
    /** New Button Operation */
    selectChanged(value){
      let data = {
        holidayTypeId: value
      }
      userListForVacation(data).then((response) => {
        if (response.code == 200) {
          console.log(response.data)
          this.userList = response.data;
        }
      })
    },
    handleAdd() {
      this.reset();
      this.title = "Adding holiday information";
      this.open = true;
    },
    /** Modify button operation */
    // handleUpdate(row) {
    //   this.reset();
    //   const id = row.holidayId;
    //   getHolidayInfo(id).then((response) => {
    //     let data = {
    //         holidayId: id,
    //         holidayTypeId: response.data.holidayTypeId,
    //         currentApprovedIndex: response.data.currentApprovedIndex,
    //     }
    //     userListForVacation(data).then((response2) => {
    //       if (response2.code == 200) {
    //         this.form = {
    //           holidayId: response.data.holidayId,
    //           holidayTypeId: response.data.holidayTypeId,
    //           // Possible missing parameters
    //         }
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
        holidayTypeId: undefined,
        holidayStartDate: undefined,
        holidayEndDate: undefined,
        currentApproverId: undefined,
        holidayInstruction: undefined,
      };
      this.resetForm("form");
    },
    // Querying the Holiday List
    getHolidayList() {
      this.loading = true;
      holidayList(this.queryParams).then((response) => {
        console.log(response);
        if (response.code == 200) {
          this.holidayList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      });
    },
    // Reset the form
    resetQuery() {
      this.dateRange = "";
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        status: undefined,
        selectStartDate: undefined,
        selectEndDate: undefined,
        holidayTypeId: undefined
      };
      this.getHolidayList();
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.selectStartDate = this.dateRange[0]; //The start time
      this.queryParams.selectEndDate = this.dateRange[1]; //The end of time
      this.getHolidayList();
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

