<template>
  <div class="app-container">
    <!-- packUpUpdate The value of -->
    <!-- Image of login page -->
    <div
      class="member_set aui-margin-b-10"
      v-hasPermi="['glasses:paramsData:list']"
    >
      <div class="flex flex_start aui-padded-t-10 aui-padded-b-10">
        <p class="fw_bold text-black font-size-20">Image of login page</p>
      </div>
      <el-table
        class="shadow"
        v-loading="paramsLoading"
        stripe
        :data="loginPic"
        @selection-change="handleSelectionChange"
      >
        <el-table-column label="The name of the" prop="name" width="240"/>
        <el-table-column label="The picture" prop="value" width="240">
          <template slot-scope="scope">
            <div class="aui-padded-t-5">
              <img
                :src="scope.row.value"
                alt
                style="width: 50px; height: 50px"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="Operating time" prop="lastDate" width="240"/>

        <el-table-column label="operation" class-name="small-padding ">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              style="color: #5ecc59 !important; font-size: 16px"
              @click="handleBackGroundPicUpdate(scope.row)"
              v-hasPermi="['glasses:paramsData:edit','system:config:edit']"
              title="The editor"
            ></el-button>
          </template>
        </el-table-column>
        <el-table-column prop width="240" align="right">
          <template slot="header">
            <div class="text-theme aui-padded-r-15" @click="packUpUpdate(11)">
              <span class="showdata">Pack up</span>
              <img
                class="showimg"
                style="width: 10px; height: 10px"
                src="../../../assets/image/display_data.png"
                alt
              />
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- Technical Customer Service Telephone -->
    <div
      class="member_set aui-margin-b-10"
      v-hasPermi="['glasses:paramsData:list']"
    >
      <div class="flex flex_start aui-padded-t-10 aui-padded-b-10">
        <p class="fw_bold text-black font-size-20">Technical Customer Service Telephone</p>
      </div>
      <el-table
        class="shadow"
        v-loading="paramsLoading"
        stripe
        :data="tecPhone"
        @selection-change="handleSelectionChange"
      >
        <el-table-column label="The name of the" prop="name" width="240"/>
        <el-table-column label="The phone" prop="value" width="240"/>
        <el-table-column label="Operating time" prop="lastDate" width="240"/>
        <el-table-column label="operation" class-name="small-padding ">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              style="color: #5ecc59 !important; font-size: 16px"
              @click="paramshandleUpdate(scope.row)"
              v-hasPermi="['glasses:paramsData:edit']"
              title="The editor"
            ></el-button>
          </template>
        </el-table-column>
        <el-table-column prop width="240" align="right">
          <template slot="header">
            <div class="text-theme aui-padded-r-15" @click="packUpUpdate(12)">
              <span class="showdata">Pack up</span>
              <img
                class="showimg"
                style="width: 10px; height: 10px"
                src="../../../assets/image/display_data.png"
                alt
              />
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- Parameter Settings -->
    <el-dialog
      :title="paramstitle"
      :visible.sync="paramsopen"
      width="500px"
      append-to-body
    >
      <el-form
        ref="paramsform"
        :model="paramsform"
        :rules="paramsRules"
        label-width="80px"
      >
        <el-form-item label="The name" prop="name">
          <el-input v-model="paramsform.name" placeholder="Please enter your name"/>
        </el-form-item>
        <div class v-if="paramsform.groupType === 5">
          <el-form-item label="value" prop="value">
            <el-upload
              action="#"
              :http-request="requestUpload"
              :show-file-list="false"
              :before-upload="beforeUpload"
            >
              <img :src="paramsform.value" class="avatar"/>
            </el-upload>
          </el-form-item>
        </div>
        <el-form-item label="value" prop="value" v-else>
          <el-input
            v-model="paramsform.value"
            placeholder="Please enter a value"
            disabled
            v-if="dataType"
          />
          <el-input
            v-model="paramsform.value"
            placeholder="Please enter a value"
            v-if="!dataType"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="paramsubmitForm">indeed set</el-button>
        <el-button @click="paramsCancel">take eliminate</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {
  listParamsData,
  getParamsData,
  delParamsData,
  addParamsData,
  updateParamsData,
  exportParamsData, getBackGroundPic, updateBackGroundPic
} from '@/api/system/dataset/paramsdata'

export default {
  name: 'MemberLevel',
  data() {
    return {
      dataType: false,
      backGroundPic: false,
      dynamicTags: [],
      // Pack up/an
      packTitle: 'Pack up ',
      packImg: '../../../assets/image/display_data.png',
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
      // Membership level setting table data
      memberLevelList: [],

      // Pop-up layer title
      title: '',
      // Whether to display the pop-up layer
      open: false,
      // Delete the state0not1Have a dictionary
      delStatusOptions: [],
      // Query parameters
      queryParams: {
        // pageNum: 1,
        // pageSize: 20,
        name: undefined
      },

      // The form parameter
      form: {},

      // Form validation
      rules: {
        name: [
          { required: true, message: 'Member level names cannot be empty', trigger: 'blur' }
        ],
        condition: [
          { required: true, message: 'The condition cannot be null', trigger: 'blur' }
        ],
        discount: [
          { required: true, message: 'The discount rate cannot be empty', trigger: 'blur' }
        ],
        delStatus: [
          {
            required: true,
            message: 'Delete the state0not1It cannot be empty',
            trigger: 'blur'
          }
        ]
      },
      //Parameter Settings
      paramsLoading: true,
      paymentList: [], //Proportion of down payment
      stockList: [], //Inventory alarm setting
      performanceList: [], //Performance appraisal criteria
      returnList: [], //Set the return visit time
      resorceList: [], //Appointment Management Sources
      loginPic: [], //Image of login page
      tecPhone: [], //Technical Customer Service Telephone
      paramsDataList: [],
      paramsopen: false,
      paramstitle: '',
      paramsform: {},
      // View qr code
      codePic: '',
      codeShow: false,
      checkDetailParams: {
        id: ''
      },
      paramsAdd: '', // Add parameters
      paramsRules: {
        name: [{ required: true, message: 'The name cannot be empty', trigger: 'blur' }],
        value: [{ required: true, message: 'The value cannot be null', trigger: 'blur' }]
      },
      //Performance assessment calculation setup
      calculateList: [],
      branchList: [], //Assessment and calculation of branch company
      storeList: [], //Store assessment and calculation
      telephoneList: [], //Operator assessment calculation
      optometristList: [], //Optometrist assessment calculation
      calculateLoading: false,
      calculateopen: false,
      calculatetitle: '',
      calculateform: {},
      calculaterules: {
        name: [{ required: true, message: 'The name cannot be empty', trigger: 'blur' }]
      },

      // Frame parameters
      mirrorBracketParamsList: [],
      mirrorLoading: false,
      mirrorOpen: false,
      mirrorForm: {},
      mirrorTitle: '',
      mirrorList: [], //According to thetypeGet frame parameters

      mirrorRules: {
        name: [
          { required: true, message: 'Parameter names cannot be empty', trigger: 'blur' }
        ],
        type: [
          {
            required: true,
            message:
              'type(1style2color3Vertical line height4The material5Mirror ring width6The centre sill width7Before the Angle8Its leg length9Surface bending)Can't be empty',
            trigger: 'blur'
          }
        ],
        delStatus: [
          {
            required: true,
            message: 'Delete the state0not1It cannot be empty',
            trigger: 'blur'
          }
        ],
        lastDate: [
          { required: true, message: 'The last operation time cannot be empty', trigger: 'blur' }
        ]
      },
      //Frame type
      glassList: [],
      glassloading: false,
      glassopen: false,
      glasstitle: '',
      glassform: {}, // The form parameter
      glassrules: {
        name: [
          { required: true, message: 'The model name cannot be empty', trigger: 'blur' }
        ],
        styles: [{ required: true, message: 'The style cannot be empty', trigger: 'blur' }],
        colors: [{ required: true, message: 'The color cannot be empty', trigger: 'blur' }],
        verticalLineHeight: [
          { required: true, message: 'Vertical line height cannot be empty', trigger: 'blur' }
        ],
        textures: [
          { required: true, message: 'Materials cannot be empty', trigger: 'blur' }
        ],
        mirrorRingWidth: [
          { required: true, message: 'The circle width must not be empty', trigger: 'blur' }
        ],
        centreSillWidth: [
          { required: true, message: 'The width of the middle beam must not be empty', trigger: 'blur' }
        ],
        topRake: [
          { required: true, message: 'Rake Angle cannot be empty', trigger: 'blur' }
        ],
        legLength: [
          { required: true, message: 'The length of the mirror leg must not be empty', trigger: 'blur' }
        ],
        surfaceBending: [
          { required: true, message: 'Face bend cannot be empty', trigger: 'blur' }
        ],
        lastDate: [
          { required: true, message: 'The last operation time cannot be empty', trigger: 'blur' }
        ]
      },
      //Add frame parameter list
      kuanshi_List: [],
      yanse_List: [],
      lixian_List: [],
      caizhi_List: [],
      jingquan_List: [],
      zhongliang_List: [],
      qianqing_List: [],
      jingtui_List: [],
      mianwan_List: [],

      applyFaceTypeList: [], //The face list
      applyFaceTypes: []
    }
  },
  computed: {
    ...mapGetters(['permissions']),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    }
  },
  created() {
    this.getDicts('apply_face_type').then((response) => {
      console.log(response.data)
      this.applyFaceTypeList = response.data
    })
    console.log(this.permissions)
    if (this.permissions.includes('*:*:*')) {
      this.paramsdataList()
    } else {
      if (this.permissions.includes('glasses:paramsData:list')) {
        this.paramsdataList()
      }
    }

    this.getDicts('del_status').then((response) => {
      this.delStatusOptions = response.data
    })
  },
  methods: {
    beforeUpload(file) {
      if (file.type.indexOf('image/') == -1) {
        this.msgError('File format error,Please upload the image type,Such as:JPG,PNGFile with suffix。')
      } else {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.paramsform.value = reader.result
        }
      }
    },
    beforeUpload1(file) {
      if (file.type.indexOf('image/') == -1) {
        this.msgError('File format error,Please upload the image type,Such as:JPG,PNGFile with suffix。')
      } else {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.glassform.coverImage = reader.result
        }
      }
    },
    requestUpload() {
    },

    // Parameter Setting List
    paramsdataList() {
      this.paramsLoading = true
      listParamsData(this.queryParams).then((response) => {
        var paramsDataList = response.data
        for (let i = 0; i < paramsDataList.length; i++) {
          if (paramsDataList[i].groupType == 1) {
            this.paymentList.push(paramsDataList[i]) //Proportion of down payment
          } else if (paramsDataList[i].groupType == 2) {
            this.stockList.push(paramsDataList[i]) //Inventory alarm setting
          } else if (paramsDataList[i].groupType == 3) {
            this.returnList.push(paramsDataList[i]) //Set the return visit time
          } else if (paramsDataList[i].groupType == 4) {
            paramsDataList[i].checkDetail = 'View qr code'
            this.resorceList.push(paramsDataList[i]) //Appointment Management Sources
          } else if (paramsDataList[i].groupType == 5) {
            this.loginPic.push(paramsDataList[i]) //Image of login page
          } else if (paramsDataList[i].groupType == 6) {
            this.tecPhone.push(paramsDataList[i]) //Technical Customer Service Telephone
          } else if (paramsDataList[i].groupType == 7) {
            this.performanceList.push(paramsDataList[i]) //Performance appraisal criteria
          }
        }
        this.paramsLoading = false
      })
    },
    /** Modify button operation */
    paramshandleUpdate(row) {
      console.log(row)
      this.paramsReset()
      const id = row.id
      getParamsData(id).then((response) => {
        console.log(response)
        console.log(2222222)

        if (response.data.type == 5) {
          this.dataType = true
          console.log(3333333)
        }
        this.paramsform = response.data
        this.paramsopen = true
        this.paramstitle = 'Modifying Parameter Settings'
      })
    },
    /** Modifying the Login page */
    handleBackGroundPicUpdate(row) {
      this.paramsReset()
      const id = row.id
      getBackGroundPic(id).then((response) => {
        if (response.data.type === 6) { // The images
          this.backGroundPic = true
        }
        this.paramsform = response.data
        this.paramsopen = true
        this.paramstitle = 'Modifying Parameter Settings'
      })
    },
    paramshandleDelete(row) {
      const name = row.name || this.name
      const ids = row.id || this.ids
      this.$confirm('Is it confirmed to delete the frame model as"' + name + '"Data item?', 'warning', {
        confirmButtonText: 'determine',
        cancelButtonText: 'cancel',
        type: 'warning'
      })
        .then(function() {
          return delParamsData(ids)
        })
        .then(() => {
          this.paymentList = []
          this.stockList = []
          this.performanceList = []
          this.returnList = []
          this.resorceList = []
          this.loginPic = []
          this.tecPhone = []
          this.paramsdataList()
          this.msgSuccess('Delete the success')
        })
        .catch(function() {
        })
    },
    /** Add button action */
    paramshandleAdd() {
      this.paramsReset()
      this.paramsopen = true
      this.paramstitle = 'Add parameter Settings'
      this.paramsform.groupType = 4
      this.paramsform.type = 5
      this.paramsAdd = 1
    },
    /** The submit button */
    paramsubmitForm: function() {
      this.$refs['paramsform'].validate((valid) => {
        if (valid) {
          if (this.paramsform.id != undefined) {
            if (this.backGroundPic) {
              updateBackGroundPic(this.paramsform).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess('Modify the success')
                  this.paramsopen = false
                  this.paymentList = []
                  this.stockList = []
                  this.performanceList = []
                  this.returnList = []
                  this.resorceList = []
                  this.loginPic = []
                  this.tecPhone = []
                  this.paramsdataList()
                  this.paramsAdd = 0
                  this.backGroundPic = false
                }
              })
            } else {
              updateParamsData(this.paramsform).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess('Modify the success')
                  this.paramsopen = false
                  this.paymentList = []
                  this.stockList = []
                  this.performanceList = []
                  this.returnList = []
                  this.resorceList = []
                  this.loginPic = []
                  this.tecPhone = []
                  this.paramsdataList()
                  this.paramsAdd = 0
                }
              })
            }
          } else {
            addParamsData(this.paramsform).then((response) => {
              if (response.code === 200) {
                this.msgSuccess('New success')
                this.paramsopen = false
                this.paramsopen = false
                this.paymentList = []
                this.stockList = []
                this.performanceList = []
                this.returnList = []
                this.resorceList = []
                this.loginPic = []
                this.tecPhone = []
                this.paramsdataList()
              }
            })
          }
        }
      })
      this.dataType = false
    },
    // Cancel button
    paramsCancel() {
      this.dataType = false
      this.paramsopen = false
      this.paramsAdd = 0
      this.paramsReset()
    },
    // Reset the form
    paramsReset() {
      this.paramsform = {
        id: undefined,
        name: undefined,
        value: undefined,
        type: undefined,
        lastDate: undefined,
        groupType: undefined
      }
      this.paramsAdd = 0
      this.resetForm('paramsform')
    },
    /** Query the membership level setting list */
    getList() {
      this.loading = true
      listMemberLevel(this.queryParams).then((response) => {
        this.memberLevelList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // Delete the state0not1Dictionary translation
    delStatusFormat(row, column) {
      return this.selectDictLabel(this.delStatusOptions, row.delStatus)
    },
    // Cancel button
    cancel() {
      this.open = false
      this.reset()
    },
    // Reset the form
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        condition: undefined,
        discount: undefined,
        delStatus: undefined,
        lastDate: undefined
      }
      this.resetForm('form')
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** Reset button operation */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      // this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** New Button Operation */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = 'Add membership level Settings'
    },

    /** hiddenitemoperation */
    packUpUpdate(index) {
      if (document.querySelectorAll('.el-table__body-wrapper')[index].style.display === 'none') {
        document.querySelectorAll('.el-table__body-wrapper')[index].style.display = 'block'
        document.querySelectorAll('.showdata')[index].innerHTML = 'Pack up'
        document.querySelectorAll('.showimg')[index].src = '../../assets/image/login_top.png'
      } else {
        document.querySelectorAll('.el-table__body-wrapper')[index].style.display = 'none'
        document.querySelectorAll('.showdata')[index].innerHTML = 'an'
        document.querySelectorAll('.showimg')[index].src = '../../assets/image/login_top.png'
      }
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getMemberLevel(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = 'Modify membership level Settings'
      })
    },
    /** The submit button */
    submitForm: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMemberLevel(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess('Modify the success')
                this.open = false
                this.getList()
              }
            })
          } else {
            addMemberLevel(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess('New success')
                this.open = false
                this.getList()
              }
            })
          }
        }
      })
    },
    /** Delete button operation */
    handleDelete(row) {
      const ids = row.id
      this.$confirm(
        'Confirm to delete the membership level setting number as"' + ids + '"Data item?',
        'warning',
        {
          confirmButtonText: 'determine',
          cancelButtonText: 'cancel',
          type: 'warning'
        }
      )
        .then(function() {
          return delMemberLevel(ids)
        })
        .then(() => {
          this.getList()
          this.msgSuccess('Delete the success')
        })
        .catch(function() {
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('Confirm to export all membership level setting data items?', 'warning', {
        confirmButtonText: 'determine',
        cancelButtonText: 'cancel',
        type: 'warning'
      })
        .then(function() {
          return exportMemberLevel(queryParams)
        })
        .then((response) => {
          this.download(response.msg)
        })
        .catch(function() {
        })
    }
  }
}
</script>
<style>
/*.checkdetail .el-table__row .checkdetail_txt .cell {*/
/*  color: #1765ff !important;*/
/*  font-size: 12px !important;*/
/*}*/

body {
  background-color: #fff !important;
}

.el-table__header-wrapper {
  background-color: #f6f8ff !important;
  height: 50px !important;
  /* line-height: 60px !important; */
}

.el-table__header-wrapper th {
  height: 50px !important;
}

.el-table__body-wrapper .el-table th,
.el-table td {
  height: 50px !important;
  padding: 0 !important;
}

/*.el-table__header-wrapper .cell {*/
/*  font-size: 14px !important;*/
/*  !*color: #a6abcc !important;*!*/
/*  font-weight: 400 !important;*/
/*  margin-left: 10px !important;*/
/*}*/

/*.member_set .el-table__header-wrapper .el-table_1_column_6 .cell {*/
/*  color: #4860fb !important;*/
/*  padding-right: 10px !important;*/
/*}*/

/*.el-table__row .cell {*/
/*  color: #0d1444 !important;*/
/*  font-size: 14px !important;*/
/*  margin-left: 10px !important;*/
/*}*/

.el-dialog__wrapper .el-form-item {
  border: none !important;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.change_mirror .el-dialog__header {
  border-bottom: 1px solid #eeeeee;
}

.change_mirror .el-input__inner {
  border-top-right-radius: 0px !important;
  border-bottom-right-radius: 0px !important;
}

.change_mirror .qd {
  border-top-left-radius: 0px !important;
  border-bottom-left-radius: 0px !important;
}

.change_mirror .canshu_line {
  width: 86%;
  height: 1px;
  border-top: 1px dashed #eeeeee;
}

.el-button--primary {
  background-color: #4860fb !important;
  border-color: #4860fb !important;
}

.change_mirror .el-tag {
  margin-right: 10px !important;
}

.el-dialog__header {
  border-bottom: 1px solid #eeeeee;
}

.line_dash {
  border-top: 1px dashed #eeeeee;
  width: 84%;
}

.code_pics .el-dialog .el-dialog__body {
  text-align: center !important;
}
</style>
