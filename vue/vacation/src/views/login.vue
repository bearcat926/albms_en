<template>
  <div class="login">
    <div class="login_box bg_shadow">
      <div class="login_left">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <div class="title-container flex flex_start aui-margin-b-15">
            <img src="../assets/image/login_top.png" alt />
            <h3
              class="title aui-margin-l-5 aui-padded-l-10 aui-margin-t-5 text-black aui-border-l"
            >{{login_title}}</h3>
          </div>
          <div class>
            <P class="text-black font-size-12 aui-margin-b-10">account:</P>
            <el-form-item prop="username">
              <el-input
                autocomplete="off"
                v-model="loginForm.username"
                type="text"
                @keyup.native="checkUser"
                placeholder="Please enter your account number"
                style="box-shadow: inset 0 0 0 1000px #f7f7f7 !important;"
              ></el-input>
            </el-form-item>
          </div>
          <div class>
            <P class="text-black font-size-12 aui-margin-b-10">password:</P>
            <el-form-item prop="password">
              <div class="flex">
                <el-input
                  ref="password"
                  v-model="loginForm.password"
                  :type="passwordType"
                  autocomplete="off"
                  placeholder="Please enter your password"
                  @blur="capsTooltip = false"
                  @keyup.enter.native="handleLogin"
                ></el-input>
                <span class="show-pwd" @click="showPwd">
                  <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                </span>
              </div>
            </el-form-item>
          </div>
          <div class>
            <P class="text-black font-size-12 aui-margin-b-10">Verification code:</P>
            <el-form-item prop="code">
              <el-input
                v-model="loginForm.code"
                auto-complete="off"
                placeholder="Please enter the verification code"
                style="width: 58%"
                @keyup.enter.native="handleLogin"
              ></el-input>
              <div class="login-code">
                <img :src="codeUrl" @click="getCode" />
              </div>
            </el-form-item>
          </div>
          <div class="flex">
            <el-checkbox v-model="loginForm.rememberMe">Remember the password</el-checkbox>
            <div class="text-deepblue udline font-size-12" @click="forget">Forgot password/Change the password</div>
          </div>
          <el-form-item class="aui-margin-t-15" style="width:100%;border:none !important">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:60%;height:40px;border-radius:20px;margin-left:20%;  "
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">deng record</span>
              <span v-else>deng record In the...</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg, getLoginInfo, getUserName } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      login_title: "Vacation reservation management system",
      technicalPhone: "", //Customer service telephone
      backGroundPic: "", //Login Background image
      codeUrl: "", //Captcha picture
      cookiePassword: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "The user name cannot be empty" },
        ],
        password: [
          { required: true, trigger: "blur", message: "The password cannot be empty" },
        ],
        code: [
          { required: true, trigger: "change", message: "The verification code cannot be empty" },
        ],
      },
      loading: false,
      redirect: undefined,
      passwordType: "password",
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    this.getCode();
    this.getCookie();Cookies.get("username");
    this.getLoginInfomation();
    this.checkUser()
  },
  methods: {
    checkUser() {
      getUserName(this.loginForm.username).then((res) => {
        console.log(res)
        if (res.code == 200) {
          this.login_title = res.data;
        }
        if(res==""){
this.login_title = "Vacation reservation management system";
        }
      });
    },
    forget() {
      this.$router.push({ path: "/password" });
    },
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    getCode() {
      getCodeImg().then((res) => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getLoginInfomation() {
      getLoginInfo().then((res) => {
        console.log(res, "Obtain the login page picture and technical customer service number");
        this.backGroundPic = res.data.backGroundPic;
        this.technicalPhone = res.data.technicalPhone;
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30,
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30,
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" });
            })
            .catch(() => {
              this.loading = false;
              this.getCode();
            });
        }
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #fff;
$dark_gray: #889aa4;
$light_gray: #eee;
$font_dark: #0d1444;
.login_button {
  width: 60%;
  height: 40px;
  border-radius: 20px;
  margin-left: 20%;
}
.el-checkbox__inner {
  border-radius: 50%;
  border-color: #4860fb !important;
}
.el-checkbox__input.is-focus .el-checkbox__inner {
  border-radius: 50%;
  border-color: #4860fb !important;
}
.el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #4860fb !important;
  border-color: #4860fb !important;
  border-radius: 50%;
}
.el-checkbox__input.is-checked + .el-checkbox__label {
  color: #a6abcc;
}
.el-button--primary {
  background-color: #4860fb !important;
  border-color: #4860fb !important;
}
.code_pic {
  width: 90px;
  height: 30px;
}
.code_pic img {
  width: 100%;
  height: 100%;
}
.login {
  width: 540px;
  height: 580px;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-left: -275px;
  margin-top: -275px;
}
.login_box {
  width: 540px;
  height: 518px;
//   background-image: url(../assets/image/login_bg.png);
  background-size: 100% 100%;
  background-color: #fff;
  border-radius: 30px;
  overflow: hidden;
}
.login_left {
  width: 540px;
  padding-top: 35px;
  float: left;
}
.login_right {
  width: 366px;
  height: 100%;
float: right;
overflow: hidden;

}
.login_right img{width: 100%;height: 100%;object-fit: cover;}
.title {
  font-size: 16px;
  color: $font_dark;
}
.login_left .el-form-item {
  border-bottom: 1px solid #eee;
}
.login-form {
  width: 300px;
  margin: 0 auto;
  .el-input {
    height: 38px;
    padding: 0 !important;
    input {
      height: 38px;
      padding: 0 !important;
      border: none !important;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 36%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
    width: 100%;
    height: 100%;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login input:-webkit-autofill {
  box-shadow: 0 0 0px 38px white inset !important;
}
.login input:-webkit-autofill:focus {
  box-shadow: 0 0 0px 38px white inset !important;
}
</style>
