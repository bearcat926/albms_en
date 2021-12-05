<template>
  <div class="login">
    <div class="login_box">
      <div class="login_left">
        <el-form ref="form" :model="form" :rules="loginRules" class="login-form">
          <div class="title-container flex flex_start aui-margin-b-15">
            <img src="../assets/image/login_top.png" alt />
            <h3
              class="title aui-margin-l-5 aui-padded-l-10 aui-margin-t-5 text-black aui-border-l"
            >Forgot password</h3>
          </div>
          <div class>
            <P class="text-black font-size-12 aui-margin-b-5">Mobile phone no.:</P>
            <el-form-item prop="username">
              <el-input v-model="form.phone" type="text" auto-complete="off" placeholder="Please enter your mobile phone number"></el-input>
            </el-form-item>
          </div>

          <div class>
            <P class="text-black font-size-12 aui-margin-b-5">Verification code:</P>
            <el-form-item prop="code">
              <div class="flex" style="border-bottom:1px solid #eee">
                <el-input
                  v-model="form.code"
                  auto-complete="off"
                  placeholder="Please enter the verification code"
                  style="width: 63%;"
                  @keyup.enter.native="handleLogin"
                  class="get_code_box"
                ></el-input>
                <div class="text-deepblue udline font-size-12" @click="getCode" v-if="this.code_type==0">{{code_txt}}</div>
                <div class="text-deepblue udline font-size-12" v-if="this.code_type==1">{{code_txt}}</div>
              </div>
            </el-form-item>
          </div>
          <div class>
            <P class="text-black font-size-12 aui-margin-b-5">Reset password:</P>
            <el-form-item prop="password">
              <el-input
                v-model="form.password"
                type="password"
                auto-complete="off"
                placeholder="Please enter your password"
                @keyup.enter.native="handleLogin"
              ></el-input>
            </el-form-item>
          </div>
          <div class>
            <P class="text-black font-size-12 aui-margin-b-5">Enter your password again:</P>
            <el-form-item prop="password">
              <el-input
                v-model="form.againPassword"
                type="password"
                auto-complete="off"
                placeholder="Please enter your password again"
                @keyup.enter.native="handleLogin"
              ></el-input>
            </el-form-item>
          </div>

          <el-form-item class="aui-margin-t-15" style="width:100%;border:none !important">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              style="width:60%;height:40px;border-radius:20px;margin-left:20%;  "
              @click.native.prevent="submit"
            >
              <span v-if="!loading">determine</span>
              <span v-else>In the submission...</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg, getLoginInfo, getUserName } from "@/api/login";
import { getCodes, forgetPassword } from "@/api/password";


export default {
  name: "password",
  data() {
    return {
      login_title: "Vacation reservation management system",
      code_txt: "Obtaining verification code",
      technicalPhone: "", //Customer service telephone
      backGroundPic: "", //Login Background image
      cookiePassword: "",
      form: {
        phone: "",
        password: "",
        againPassword: "",
        code: "",
        uuid: "",
      },
      loginRules: {
        phone: [{ required: true, trigger: "blur", message: "Mobile phone number cannot be empty" }],
        password: [
          { required: true, trigger: "blur", message: "The password cannot be empty" },
        ],
        againPassword: [
          { required: true, trigger: "blur", message: "The password cannot be empty" },
        ],
        code: [
          { required: true, trigger: "change", message: "The verification code cannot be empty" },
        ],
      },
      loading: false,
      redirect: undefined,
      timer:'',
      count:'',
      code_type:0
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
    this.getLoginInfomation();
  },
  methods: {
    getLoginInfomation() {
      getLoginInfo().then((res) => {
        this.backGroundPic = "data:image/gif;base64," + res.data.backGroundPic;
        this.technicalPhone = res.data.technicalPhone;
      });
    },
    // getCode() {

    // },
    getCode() {
      if (this.form.phone == "") {
        this.msgError("Please enter your account number");
        return;
      }
      var myreg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
      if (!myreg.test(this.form.phone)) {
        this.msgError("The phone number format is incorrect");
        return;
      }
      getCodes(this.form.phone, 1, 2).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("The verification code is sent successfully. Procedure");
          this.count=60
          if (!this.timer) {
            this.timer = setInterval(() => {
              if (this.count > 0 && this.count <= 60) {
                this.count--;
                this.code_type=1
                this.code_txt =this.count+"sRetransmit after";
              } else {
                this.code_type=0
                this.code_txt = "To resend";
                clearInterval(this.timer);
                this.timer = null;
              }
            }, 1000);
          }

        }
      });
    },

    submit() {
      if (this.form.code == "") {
        this.msgError("Please enter the verification code");
        return;
      }
      if (this.form.password != this.form.againPassword) {
        this.msgError("The two passwords are inconsistent");
        return;
      }
      forgetPassword(this.form).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("Password changed successfully");
          // Call the globally mounted method,Close the current TAB
          this.$store.dispatch("tagsView/delView", this.$route);
          // Returns the route of the previous step,Returns the previous TAB
          this.$router.go(-1);
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
  width: 906px;
  height: 580px;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-left: -453px;
  margin-top: -275px;
}
.login_box {
  width: 906px;
  height: 518px;
  background-image: url(../assets/image/login_bg.png);
  background-size: 100% 100%;
}
.login_box .login_left {
  width: 560px;
  padding-top: 35px;
}
.login_box .title {
  font-size: 16px;
  color: $font_dark;
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
      border-bottom: 1px solid #eeeeee !important;
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
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.login_box .el-login-footer {
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
.login_box .get_code_box input {
  border: none !important;
}
.login_box input:-webkit-autofill {
  box-shadow: 0 0 0px 38px white inset !important;
}
.login_box input:-webkit-autofill:focus {
  box-shadow: 0 0 0px 38px white inset !important;
}
</style>
