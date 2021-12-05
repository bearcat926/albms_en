<template>
  <div>
    <div class="member">
        <p class="font-size-20">User analysis</p>
      <div class="top_four flex aui-margin-t-15">
        <div class="top_four_item bg-white top_four_item1 flex bg-radius bg_shadow">
          <Chart1 id="chart1" height="100%" width="94%" style="margin-left:3%" :data="chartData" />
          <div class="top_four_item_text aui-padded-10">
            <p>Number of head office employees</p>
            <div class="flex flex_start" style="align-items:flex-end">
              <p class="font-size-24 fw_bold aui-padded-t-5 aui-padded-r-5">{{chartData.totalUserNum}}</p>
              <!-- <img src="../../../assets/image/member_up.png" alt /> -->
            </div>
            <p class="aui-padded-t-5 font-size-14">
              <span class="text-green">{{chartData.totalUserNumRatio}}</span>
              <span class="aui-padded-l-5">Comparison with last month</span>
            </p>
          </div>
        </div>
        <div class="top_four_item bg-white top_four_item2 flex bg-radius bg_shadow">
          <Chart2 id="chart2" height="100%" width="94%" style="margin-left:3%" :data="chartData"/>
          <div class="top_four_item_text aui-padded-10">
            <p>Number of subsidiaries</p>
            <div class="flex flex_start" style="align-items:flex-end;">
              <p class="font-size-24 fw_bold aui-padded-t-5 aui-padded-r-5">{{chartData.totalCompanyNum}}</p>
              <!-- <img src="../../../assets/image/member_down.png" alt /> -->
            </div>
            <p class="aui-padded-t-5 font-size-14">
              <span class="text-or">{{chartData.totalCompanyNumRatio}}</span>
              <span class="aui-padded-l-5">Comparison with last month</span>
            </p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { userTopData } from "@/api/user/charts";
import Chart1 from "@/components/Charts/userLineChart1";
import Chart2 from "@/components/Charts/userLineChart2";
import Chart3 from "@/components/Charts/userLineChart3";
import Chart4 from "@/components/Charts/userLineChart4";
export default {
  name: "member",
  components: {
    Chart1,
    Chart2,
    Chart3,
    Chart4
  },
  data() {
    return {
      dialogVisible: false,
      chartData:"",
       data1: ["01month", "02month", "03month", "04month", "05month", "06month"],
      data2: [80, 90, 200, 400, 125, 200],
    };
  },
  created() {
    userTopData().then((response) => {
      if (response.code == 200) {
        if (response.data) {
          this.chartData = response.data;
        }
      }
    });

  },
  methods: {
    handleClose(done) {
      this.$confirm("Confirm the shut down?")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
};
</script>

<style  >
.member .top_four {
  height: 250px;
}
.member .top_four_item {
  width: 49%;
  height: 100%;
  border-radius: 10px;
  position: relative;
}
.member .top_four_item_text {
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

.member .line_dash {
  border-top: 1px dashed #eeeeee;
  width: 90%;
}
</style>
