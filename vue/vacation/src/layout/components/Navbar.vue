<template>
  <div class="navbar">
    <hamburger
      id="hamburger-container"
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />
    <!-- <breadcrumb id="breadcrumb-container" class="breadcrumb-container" /> -->
    <div class="right-menu flex">
      <el-dropdown
        class="avatar-container right-menu-item hover-effect"
        trigger="click"
        style="padding:0"
      >
        <div class="flex">
          <div class="avatar-wrapper flex aui-padded-r-15">
            <img :src="avatar" class="user-avatar" style="background-color:#fff;"/>
            <div class="aui-padded-l-5" style="height:60px;">
              <div class="font-size-16 aui-margin-t-5 text-white" style="line-height:24px">{{name}}</div>
              <div class="user_table aui-text-white" >{{rolesname[0].roleName}}</div>
            </div>
          </div>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>Personal center</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>Layout Settings</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>Log out</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <template>
        <div class="flex">
          <div class="flex aui-padded-l-15 aui-padded-r-15 nav_box" @click="info" >
            <div class="tongzhi_box">
              <img src="../../assets/image/tongzhi.png" class="user-avatar" />
              <div class="info_num" v-if="infonum>0">{{infonum}}</div>
            </div>
          </div>
          <div class="flex aui-padded-l-15 aui-padded-r-15 nav_box" @click="logout">
            <div class="tongzhi_box">
              <img src="../../assets/image/exit.png" class="user-avatar" />
            </div>
          </div>
        </div>
      </template>
      <!-- <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item" />
        
        <el-tooltip content="The source address" effect="dark" placement="bottom">
          <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
        </el-tooltip>

        <el-tooltip content="The document address" effect="dark" placement="bottom">
          <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
        </el-tooltip>

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip content="The layout size" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

      </template> -->
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import Screenfull from "@/components/Screenfull";
import SizeSelect from "@/components/SizeSelect";
import Search from "@/components/HeaderSearch";
import RuoYiGit from "@/components/RuoYi/Git";
import RuoYiDoc from "@/components/RuoYi/Doc";
import { getNoReadNum } from "@/api/index";

export default {
  components: {
    Breadcrumb,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc,
  },
  data() {
    return {
      readNum:0
    }
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "device","name","remark","infonum","rolesname"]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings;
      },
      set(val) {
        this.$store.dispatch("settings/changeSetting", {
          key: "showSettings",
          value: val,
        });
      },
    },
  },
  created() {
    this.$store
            .dispatch("GetInfoNum", this.loginForm)
            .then(() => {
            })
            .catch(() => {
            });
  },
  methods: {
    info() {
      this.$router.push({ name: "The message", path: "/information/info" });
    },
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      this.$confirm("Are you sure to log out and exit the system?", "prompt", {
        confirmButtonText: "determine",
        cancelButtonText: "cancel",
        type: "warning",
      }).then(() => {
        this.$store.dispatch("LogOut").then(() => {
          location.reload();
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 70px;
  overflow: hidden;
  position: relative;
  background: #4860fb;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 70px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    // line-height: 70px;
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        // margin-top: 5px;
        position: relative;
        height: 70px;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 20px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.user_table {
  padding: 2px 4px;
  width: fit-content;
  
  background-color: #ffffff;
  border-radius: 12px;
  font-size: 12px;
  color: #4860fb;
  text-align: center;
}
.tongzhi_box {
  position: relative;
  width: 24px;
  height: 24px;
  margin: 0 auto;
}

.tongzhi_box .user-avatar {
  cursor: pointer;
  width: 100%;
  height: 100%;
  margin-top: 0;
}
.aui-badge {
  display: inline-block;
  width: auto;
  text-align: center;
  min-width: 0.8rem;
  height: 0.8rem;
  line-height: 0.8rem;
  padding: 0 0.2rem;
  font-size: 0.6rem;
  color: #ffffff;
  background-color: #ff2600;
  border-radius: 0.4rem;
  position: absolute;
  top: 7px;
  left: 58%;
  z-index: 99;
}
.nav_box {
  position: relative;
  border-left: 1px solid #0d1444;
  height: 70px;
  width: 120px;
}
.info_num {
  position: absolute;
  top: -5px;
  right: -25px;
  height: 16px;
  border-radius: 8px;
  background-color: red;
  color: #fff;
  font-size: 12px;
  text-align: center;
  line-height: 16px;
  padding: 0 5px;
}
</style>
