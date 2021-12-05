<template>
  <div>
    <div class="member">
      <div class="top_four flex aui-padded-10">
        <div class="top_four_item bg-white top_four_item1 flex bg-radius bg_shadow">
          <Chart1 id="chart1" height="100%" width="100%" :data1="data1" :data2="data2"/>
          <div class="top_four_item_text aui-padded-10">
            <p>Number of head office employees</p>
            <div class="flex flex_start" style="align-items:flex-end">
              <p class="font-size-24 fw_bold aui-padded-t-5 aui-padded-r-5">45,632</p>
              <img src="../../../assets/image/member_up.png" alt />
            </div>
            <p class="aui-padded-t-5 font-size-14">
              <span class="text-green">+4,632%</span>
              <span class="aui-padded-l-5">Comparison with last month</span>
            </p>
          </div>
        </div>
        <div class="top_four_item bg-white top_four_item2 flex bg-radius bg_shadow">
          <Chart2 id="chart2" height="100%" width="100%" :data1="data1" :data2="data2"/>
          <div class="top_four_item_text aui-padded-10">
            <p>Number of subsidiaries</p>
            <div class="flex flex_start" style="align-items:flex-end;">
              <p class="font-size-24 fw_bold aui-padded-t-5 aui-padded-r-5">45,632</p>
              <img src="../../../assets/image/member_down.png" alt />
            </div>
            <p class="aui-padded-t-5 font-size-14">
              <span class="text-or">-4,632%</span>
              <span class="aui-padded-l-5">Comparison with last month</span>
            </p>
          </div>
        </div>
        <div class="top_four_item bg-white top_four_item3 flex bg-radius bg_shadow">
          <Chart3 id="chart3" height="100%" width="100%" :data1="data1" :data2="data2"/>
          <div class="top_four_item_text aui-padded-10">
            <p>Number of stores</p>
            <div class="flex flex_start" style="align-items:flex-end">
              <p class="font-size-24 fw_bold aui-padded-t-5 aui-padded-r-5">45,632</p>
              <img src="../../../assets/image/member_up.png" alt />
            </div>
            <p class="aui-padded-t-5 font-size-14">
              <span class="text-green">+4,632%</span>
              <span class="aui-padded-l-5">Comparison with last month</span>
            </p>
          </div>
        </div>
         <div class="top_four_item bg-white top_four_item2 flex bg-radius bg_shadow">
          <Chart4 id="chart4" height="100%" width="100%" :data1="data1" :data2="data2"/>
          <div class="top_four_item_text aui-padded-10">
            <p>Number of optometrists</p>
            <div class="flex flex_start" style="align-items:flex-end;">
              <p class="font-size-24 fw_bold aui-padded-t-5 aui-padded-r-5">45,632</p>
              <img src="../../../assets/image/member_down.png" alt />
            </div>
            <p class="aui-padded-t-5 font-size-14">
              <span class="text-or">-4,632%</span>
              <span class="aui-padded-l-5">Comparison with last month</span>
            </p>
          </div>
        </div>
      </div>
      <!-- The member information
      <el-dialog title="The member information" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
        <div>
          <div class="text-grayer aui-padded-t-15 flex">
            <div>Basic information</div>
            <div class="line_dash"></div>
          </div>
          <div class="aui-padded-15 font-size-14">
            <el-row>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Place the order with the user:</span>
                <span class="aui-padded-l-5 text-graybc">With the wind</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>gender:</span>
                <span class="aui-padded-l-5 text-graybc">male</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>The phone:</span>
                <span class="aui-padded-l-5 text-graybc">17868879654</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>age:</span>
                <span class="aui-padded-l-5 text-graybc">22</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>area:</span>
                <span class="aui-padded-l-5 text-graybc">Shaanxi province Xi 'an The yanta district</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Detailed address:</span>
                <span class="aui-padded-l-5 text-graybc">Yanta District and Shengjingguang Center</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Passport no.:</span>
                <span class="aui-padded-l-5 text-graybc">3423</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>In the store:</span>
                <span class="aui-padded-l-5 text-graybc">Respect of stores</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>references:</span>
                <span class="aui-padded-l-5 text-graybc">Liu Minjun</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Make an appointment to source:</span>
                <span class="aui-padded-l-5 text-graybc">Liu Minjun</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Make an appointment to people:</span>
                <span class="aui-padded-l-5 text-graybc">Liu Minjun</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Customer remarks:</span>
                <span class="aui-padded-l-5 text-graybc">This client is very kind,There is also a desire to buy,At follow up!</span>
              </el-col>
            </el-row>
          </div>
          <div class="text-grayer flex">
            <div>Data statistics</div>
            <div class="line_dash"></div>
          </div>
          <div class="aui-padded-15 font-size-14 aui-padded-b-0">
            <el-row>
              <el-col :span="12" class="aui-padded-b-10">
                <span>By inputting time:</span>
                <span class="aui-padded-l-5 text-graybc">2020.02.02</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>source:</span>
                <span class="aui-padded-l-5 text-graybc">Telephone counseling</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Membership grade:</span>
                <span class="aui-padded-l-5 text-graybc">v1</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>The cumulative:</span>
                <span class="aui-padded-l-5 text-graybc">34.874yuan</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Number of consumption:</span>
                <span class="aui-padded-l-5 text-theme">3</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Last consumption time:</span>
                <span class="aui-padded-l-5 text-graybc">2020.03.14</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>Referral times:</span>
                <span class="aui-padded-l-5 text-theme">2</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>The accumulative integral:</span>
                <span class="aui-padded-l-5 text-graybc">234</span>
              </el-col>
              <el-col :span="12" class="aui-padded-b-10">
                <span>The remaining points:</span>
                <span class="aui-padded-l-5 text-graybc">345</span>
              </el-col>
            </el-row>
          </div>
          <div class="text-grayer flex">
            <div>Return visit to record</div>
            <div class="line_dash"></div>
          </div>
          <div class="aui-padded-t-10">
            <el-input
              type="textarea"
              placeholder="Click here to add a return visit record"
              v-model="textarea"
              
              show-word-limit
            ></el-input>
            <el-button class="aui-margin-t-10" type="primary">submit</el-button>
          </div>
          <div class="aui-margin-t-10 font-size-12">
            <div class="aui-padded-b-10">
              <p class="font-size-14">The customer is very willing to buy,Always follow up</p>
              <p class="text-graybc aui-padded-t-5"> 
                <span>The salesman:Bang card</span>
                <span class="aui-padded-l-10 ">2020.03.20 12:45</span>
              </p>
            </div>
            <div class="aui-padded-b-10">
              <p class="font-size-14">The customer is very willing to buy,Always follow up。The customer is very willing to buy,Always follow up。The customer is very willing to buy,Always follow up。The customer is very willing to buy,Always follow up。</p>
              <p class="text-graybc aui-padded-t-5"> 
                <span>The salesman:Bang card</span>
                <span class="aui-padded-l-10 ">2020.03.20 12:45</span>
              </p>
            </div>
          </div>
        </div>
      </el-dialog> -->
    </div>
  </div>
</template>

<script>
import Chart1 from "@/components/Charts/memberLineChart1";
import Chart2 from "@/components/Charts/memberLineChart2";
import Chart3 from "@/components/Charts/memberLineChart3";
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
       data1: ["01month", "02month", "03month", "04month", "05month", "06month"],
      data2: [80, 90, 200, 400, 125, 200],
    };
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
  width: 24%;
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