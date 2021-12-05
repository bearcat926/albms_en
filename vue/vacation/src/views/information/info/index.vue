<template>
  <div>
    <div class="aui-padded-15">
      <div class="flex flex_start">
        <p class="font-size-20 fw_bold">The message center</p>
        <div class="aui-padded-l-15 flex">
          <el-radio-group v-model="radio" @change="agreeChange" class="flex">
            <el-radio :label="1" class="flex">
              <div class="info_nav">
                System message
                <p class="info_nums" v-if="sysReadNum>0">{{sysReadNum}}</p>
              </div>
            </el-radio>
            <el-radio :label="2" class="flex">
              <div class="info_nav">
                Active message
                <p class="info_nums" v-if="actReadNum>0">{{actReadNum}}</p>
              </div>
            </el-radio>
          </el-radio-group>
          <div class="flex aui-padded-l-15" @click="allClear">
            <img src="../../../assets/image/qingchu.png" alt style="width:16px;height:16px" />
            <p class="font-size-14 text-theme">One key is marked as read</p>
          </div>
        </div>
      </div>
    </div>

    <div class="font-size-14">
      <div class="bg-radius aui-padded-15 aui-padded-r-0 aui-padded-t-0 part_box info_box">
        <div class="info">
          <!-- System message -->
          <div class="aui-padded-b-5 font-size-14" v-if="radio==1">
            <el-row>
              <el-col :span="12" v-for="(item,index) in messagesData" :key="index">
                <div
                  class="aui-padded-5 bg_shadow aui-margin-b-15 bg-white border-radius aui-margin-r-15"
                  v-if="item.status==1"
                >
                  <div class="flex aui-padded-t-5 aui-padded-b-5 aui-border-b">
                    <p>
                      <span>System message</span>
                      <span class="font-size-12 text-grayer aui-padded-l-5">{{item.createDate}}</span>
                    </p>
                    <div class="flex" v-if="item.readStatus==0">
                      <img
                        src="../../../assets/image/qingchu.png"
                        alt
                        style="width:16px;height:16px"
                        @click="clear(item.id)"
                      />
                      <p class="info_point aui-margin-l-10"></p>
                    </div>
                  </div>
                  <div class="aui-padded-t-10 aui-padded-b-5">
                    <span>{{item.activityGoods}}</span>
                    <!-- <span class="text-theme">Click to view</span> -->
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
          <!-- Active message -->
          <div class="aui-padded-t-5 font-size-14 aui-padded-r-0" v-else>
            <el-row>
              <el-col :span="12" v-for="(item,index) in messagesData" :key="index">
                <div
                  class="aui-padded-5 bg_shadow bg-white border-radius aui-margin-b-15 aui-margin-r-15"
                  v-if="item.status==2"
                >
                  <div class="flex aui-padded-t-5 aui-padded-b-5 aui-border-b">
                    <p>
                      <span>Active message</span>
                      <span class="font-size-12 text-grayer aui-padded-l-5">{{item.createDate}}</span>
                    </p>
                    <div class="flex" v-if="item.readStatus==0">
                      <img
                        src="../../../assets/image/qingchu.png"
                        alt
                        style="width:16px;height:16px"
                        @click="clear(item.id)"
                      />
                      <p class="info_point aui-margin-l-10"></p>
                    </div>
                  </div>
                  <div class="aui-padded-t-5 aui-padded-b-5">
                    <p class="aui-padded-b-5">
                      <span class="text-graybc">The activity type:</span>
                      <span>{{item.type}}</span>
                    </p>
                    <p class="aui-padded-b-5">
                      <span class="text-graybc">Amount of activity:</span>
                      <span>{{item.price}}</span>
                    </p>
                    <p class="aui-padded-b-5">
                      <span class="text-graybc">The activity time:</span>
                      <span>{{item.activityDate}}</span>
                    </p>
                    <p class="aui-padded-b-5">
                      <span class="text-graybc">Activities of goods:</span>
                      <span class="text-black">{{item.activityGoods}}</span>
                    </p>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getNoReadNumStatus,
  messagesList,
  clearMessages,
} from "@/api/information";
export default {
  name: "infomation",
  data() {
    return {
      sysReadNum: 0,
      actReadNum: 0,
      messagesData: [],
      delId: [],

      radio: 1,
      info_list: ["", "", ""],
    };
  },
  created() {
    this.getNum(1);
    this.getNum(2);
    this.getList();
  },
  methods: {
    agreeChange() {},
    allClear() {
      const list = this.messagesData;
      const ids = [];
      for (let i = 0; i < list.length; i++) {
        if (list[i].readStatus == 0) {
          ids.push(list[i].id);
        }
      }
      if(ids.length<=0){
        this.msgError("There are no tagged messages at the moment");
        return

      }
      this.$confirm("Make sure to mark all messages as read?", "warning", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      })
        .then(function () {
          return clearMessages(ids);
        })
        .then(() => {
          this.msgSuccess("Operation is successful");
          this.getList();
          this.getNum(1);
          this.getNum(2);
         this.getInfo()
        })
        .catch(function () {});
    },
    clear(id) {
      clearMessages(id).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("The message is marked as read");
          this.getList();
          this.getNum(1);
          this.getNum(2);
          this.getInfo()
         
        }
      });
    },
    getInfo(){
       this.$store
            .dispatch("GetInfoNum", this.loginForm)
            .then(() => {
            })
            .catch(() => {
            });

    },
    getList() {
      messagesList().then((res) => {
        if (res.code == 200) {
          this.messagesData = res.rows;
        }
      });
    },
    getNum(idx) {
      getNoReadNumStatus(idx).then((res) => {
        if (res.code == 200) {
          if (idx == 1) {
            this.sysReadNum = res.data;
          } else {
            this.actReadNum = res.data;
          }
        }
      });
    },
  },
};
</script>

<style scoped>
.info_nav {
  position: relative;
}
</style> >

