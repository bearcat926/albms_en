<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="The old password" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="Please enter your old password" type="password" />
    </el-form-item>
    <el-form-item label="The new password" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="Please enter a new password" type="password" />
    </el-form-item>
    <el-form-item label="Confirm password" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="Please confirm password." type="password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">save</el-button>
      <el-button type="danger" size="mini" @click="close">Shut down</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("The entered passwords are inconsistent"));
      } else {
        callback();
      }
    };
    return {
      test: "1test",
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // Form validation
      rules: {
        oldPassword: [
          { required: true, message: "The old password cannot be empty", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "The new password cannot be empty", trigger: "blur" },
          { min: 6, max: 20, message: "The length of 6 to 20 A character", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "Confirm password cannot be empty", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(
            response => {
              if (response.code === 200) {
                this.msgSuccess("Modify the success");
              }
            }
          );
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
