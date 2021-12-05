<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="The user nickname" prop="nickName">
      <el-input v-model="user.nickName" />
    </el-form-item>
    <el-form-item label="Mobile phone number" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="email" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="gender">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">male</el-radio>
        <el-radio label="1">female</el-radio>
        <el-radio label="2">The unknown</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">save</el-button>
      <el-button type="danger" size="mini" @click="close">Shut down</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // Form validation
      rules: {
        nickName: [
          { required: true, message: "User nicknames cannot be empty", trigger: "blur" }
        ],
        email: [
          { required: true, message: "The email address cannot be empty", trigger: "blur" },
          {
            type: "email",
            message: "'Please enter the correct email address",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "The mobile phone number cannot be empty", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter the correct mobile phone number",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
            if (response.code === 200) {
              this.msgSuccess("Modify the success");
            }
          });
        }
      });
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    }
  }
};
</script>
