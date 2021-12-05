<template>
  <div class="page1">
    <!-- The announcement to inform -->
    <div class="flex aui-padded-15 aui-padded-t-0  aui-margin-t-10" style="align-items: flex-start">
      <div class="font-size-14">
        <div class="text-black aui-padded-b-10 fw5">The announcement to inform</div>
        <div
          class="bg-radius aui-padded-t-15 aui-padded-b-15 bg-white part_box1 bg_shadow"
        >
          <div class="part1 aui-padded-b-15">
            <ul class="aui-border-b aui-padded-b-10">
              <li
                class="flex part1_item_box"
                v-for="(item, index) in noticeList"
                :key="index"
                @click="detail(item)"
              >
                <p class="flex part1_item1 aui-margin-r-5">
                  <span class="font-size-30 text-graydf aui-padded-r-5 bull"></span>
                  <span>{{ item.noticeTitle }}</span>
                </p>
                <p class="font-size-12 text-theme part1_item2">Check the details</p>
                <!-- <p class="part1_item2" v-html="item.noticeContent"></p> -->
                <p class="part1_item3 aui-text-gray aui-text-right">
                  {{ item.createTime }}
                </p>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <el-dialog
      :title="noticeTitle"
      :visible.sync="noticeTrue"
      width="80%"
      :before-close="handleClose"
    >
      <div class="font-size-14">
        <el-row>
          <el-col :span="24" class="aui-padded-b-10">
            <span class="text-graybc">The title:</span>
            <span class="aui-padded-l-5">{{ noticeDetail.noticeTitle }}</span>
          </el-col>
          <el-col :span="24" class="aui-padded-b-10">
            <span class="text-graybc">type:</span>
            <span class="aui-padded-l-5">{{ noticeDetail.noticeTypeTxt }}</span>
          </el-col>
          <el-col :span="24" class="aui-padded-b-10">
            <span class="text-graybc">Creation time:</span>
            <span class="aui-padded-l-5">{{ noticeDetail.createTime }}</span>
          </el-col>
          <el-col :span="24" class="aui-padded-b-10">
            <el-col :span="5" class="aui-padded-b-10">
              <span class="text-graybc">content:</span>
            </el-col>
            <el-col :span="19" class="aui-padded-b-10">
              <div v-html="noticeDetail.noticeContent"></div>
            </el-col>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import Chart from '@/components/Charts/LineMarker'
import Histogram from '@/components/Charts/Histogram'
import pieChart from '@/components/Charts/pieChart'
import {
  // getTopData,
  // salePriceAnalyse,
  // saleNumAnalyse,
  // clientMemberLevelAnalyse,
  afterSalesOrderToBeReviewed,
  afterIntegralOrderToBeReviewed,
  afterAllotOrderToBeReviewed,
  pendingMatters
  // indexPopUpWindows
} from '@/api/index'
import { addNoticeRecord } from '@/api/system/noticeRecord'
import { listNotice1 } from '@/api/system/notice'

export default {
  name: 'Index',
  components: {
    Chart,
    Histogram,
    pieChart
  },
  data() {
    return {
      noticeDetail: '',
      noticeTitle: '',
      noticeTrue: false,
      typeOptions: '',
      topData: '',
      chartData: '',
      histogramData: '',
      pieData: '',
      dialogVisible: false,
      dialogVisible1: false,
      clientDetail: '',
      salesOrderData: '',
      afterIntegralOrderData: '',
      afterAllotOrderData: '',
      pendingMattersData: '',
      windowsData: [],

      noticeList: []
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
    this.getDicts('sys_notice_type').then((response) => {
      this.typeOptions = response.data
      // console.log(response.data);
    })
    this.getNoticeList()
    // this.getData()
    // this.getList()
  },

  methods: {
    detail(row) {
      this.noticeTrue = true
      this.noticeTitle = row.noticeTitle
      this.noticeDetail = row
      var form = {
        sysNoticeId: row.noticeId
      }
      addNoticeRecord(form).then((response) => {
        // console.log(response);
      })
    },
    getNoticeList() {
      listNotice1().then((response) => {
        var list = response.data
        console.log(list)
        var typeOptions = this.typeOptions
        for (let i = 0; i < list.length; i++) {
          for (let j = 0; j < typeOptions.length; j++) {
            if (list[i].noticeType == typeOptions[j].dictValue) {
              list[i].noticeTypeTxt = typeOptions[j].dictLabel
            }
          }
        }

        this.noticeList = list
      })
    },
    getData() {
      getTopData().then((res) => {
        if (res.code == 200) {
          this.topData = res.data
          this.topData.thisMonthTotalMoney = res.data.thisMonthTotalMoney.toFixed(
            2
          )
          this.topData.thisCustomerPrice = res.data.thisCustomerPrice.toFixed(
            2
          )
        }
      })
      // Graph data
      salePriceAnalyse().then((res) => {
        if (res.code == 200) {
          this.chartData = res.data
        }
      })
      // Bar chart data
      saleNumAnalyse().then((res) => {
        if (res.code == 200) {
          this.histogramData = res.data
        }
      })
      // Ring graph data

      clientMemberLevelAnalyse().then((res) => {
        if (res.code == 200) {
          this.pieData = res.data
          var data = this.pieData
          for (var j = data.length - 1; j >= 0; j--) {
            if (data[j].value == 0) {
              data.splice(j, 1)
            }
          }
          this.pieData = data
        }
      })

      indexPopUpWindows().then((res) => {
        if (res.code == 200) {
          if (res.data.length > 0) {
            this.dialogVisible = true
            this.windowsData = res.data
          }
        }
      })
      // listClient().then((res) => {
      //   console.log(res)
      //   if (res.code == 200) {
      //     if (res.rows.length > 0) {
      //       this.dialogVisible = true;
      //       this.windowsData = res.rows;
      //     }
      //   }
      // });
    },
    getList() {
      if (
        this.permissions.includes('glasses:index:afterSalesOrderToBeReviewed')
      ) {
        afterSalesOrderToBeReviewed().then((res) => {
          console.log(res)
          if (res.code == 200) {
            this.salesOrderData = res.data
          }
        })
      }
      if (
        this.permissions.includes(
          'glasses:index:afterIntegralOrderToBeReviewed'
        )
      ) {
        afterIntegralOrderToBeReviewed().then((res) => {
          if (res.code == 200) {
            this.afterIntegralOrderData = res.data
          }
        })
      }
      if (
        this.permissions.includes('glasses:index:afterAllotOrderToBeReviewed')
      ) {
        afterAllotOrderToBeReviewed().then((res) => {
          if (res.code == 200) {
            this.afterAllotOrderData = res.data
          }
        })
      }
      if (this.permissions.includes('glasses:index:pendingMatters')) {
        pendingMatters().then((res) => {
          if (res.code == 200) {
            this.pendingMattersData = res.data
          }
        })
      }
    },
    // View customer details
    handleDataScope(row) {
      const id = row.clientId
      clientDetail(id).then((response) => {
        if (response.code == 200) {
          this.clientDetail = response.data
          this.dialogVisible1 = true
        }
      })
    },
    handleClose(done) {
      done()
    }
  }
}
</script>

<style lang="scss">
.page1 .dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

.page1 .top_four_item {
  width: 15.5%;
  height: 134px;
  border-radius: 10px;
  box-shadow: 0 5px 10px #ddd;
  transition: 0.5s;
}

.page1 .top_four_item:last-child {
  margin-right: 0 !important;
}

.page1 .top_four_item:hover {
  transform: scale(1.1);
}

.page1 .top_four_item1 {
  background-image: linear-gradient(to right, #ff4449, #ff8e55);
}

.page1 .top_four_item2 {
  background-image: linear-gradient(to right, #6237dc, #9654ca);
}

.page1 .top_four_item3 {
  background-image: linear-gradient(to right, #f77115, #fabb4d);
}

.page1 .top_four_item4 {
  background-image: linear-gradient(to right, #4c6af1, #5296f8);
}

.page1 .top_four_item5 {
  background-image: linear-gradient(to right, #ff4449, #ff8e55);
}

.top_four_item6 {
  background-image: linear-gradient(to right, #3f3ed1, #8881ff);
}

.page1 .top_four_item .top_four_img {
  transform: scale(0.5);
}

.page1 .chart-container {
  position: relative;
  width: 100%;
  height: 346px;
}

// todo
.page1 .part_box1 {
  height: 348px;
  overflow: auto;
}

.page1 .part_box2 {
  height: 408px;
  overflow: auto;
}

.page1 .part_box::-webkit-scrollbar {
  display: none; /*Hide scroll bar*/
}

.page1 .part1:last-child .aui-border-b {
  border-bottom: none !important;
}

.page1 .part1_item_box {
  align-items: flex-start;
  padding-right: 20px;
  padding-left: 20px;
}

.page1 .part1_title {
  padding-left: 20px;
}

.page1 .part1_item_box:hover {
  background-color: #f6f8ff !important;
  color: #4860fb !important;
}

.page1 .part1_item1 {
  width: 40%;
  justify-content: flex-start;
  align-items: flex-start;
  line-height: 28px;
}

.page1 .part1_item2 {
  width: 28%;
  line-height: 28px;
}

.page1 .part1_item3 {
  width: 32%;
  line-height: 28px;
}

.page1 .part1_item1 .bull {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: #eaedff !important;
  margin-top: 9px;
  margin-right: 10px;
}

.page1 .el-dialog .el-dialog__header .el-dialog__title {
  color: #4860fb !important;
}
</style>
