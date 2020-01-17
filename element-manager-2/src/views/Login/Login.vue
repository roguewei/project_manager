<template>
  <div class="login_container" v-loading="loading">
    <div class="login_box">
      <!-- 头像 -->
      <div class="avatar_box">
        <img src="../../assets/logo.png" />
      </div>
      <el-form
        ref="loginFormRef"
        :rules="loginFormRules"
        :model="form"
        label-width="80px"
        class="login_form"
      >
        <el-form-item
          label="用户名"
          for="username"
          prop="username"
          class="login_label"
        >
          <el-input
            v-model="form.username"
            id="username"
            placeholder="请输入用户名"
            prefix-icon="iconfont wgsaccount"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          for="password"
          prop="password"
          class="login_label"
        >
          <el-input
            v-model="form.password"
            id="password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="iconfont wgspassword"
          ></el-input>
        </el-form-item>
        <el-form-item class="login_form_btn" label-width="0px">
          <el-button @click="loginClick">登录</el-button>
          <el-button type="info" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import request from "@/network/request.js";
import { login, logout, getInfo } from "@/api/user";
import {
  setToken,
  setUserInfo,
  setUserRole,
  setUserGroup
} from "@/utils/auth.js";

export default {
  name: "Login",
  data() {
    return {
      loading: false,
      form: {
        username: "",
        password: ""
      },
      loginFormRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    loginClick() {
      this.loading = true;
      this.$refs.loginFormRef.validate(valid => {
        if (!valid) {
          return;
        } else {
          login(this.form)
            .then(res => {
              if (res.status === 200) {
                this.$message({
                  message: res.msg,
                  type: "success",
                  showClose: true
                });
                // 讲用户数据保存到store
                setToken(res.data.token);
                setUserInfo(res.data.userInfo);
                setUserRole(res.data.role);
                setUserGroup(res.data.group);

                this.loading = false;
                // 通过编程式导航跳转页面
                this.$router.push("/home");
              } else {
                this.$message({
                  message: res.msg,
                  type: "error",
                  showClose: true
                });
                this.loading = false;
              }
            })
            .catch(err => {
              console.log(err);
              this.loading = false;
            });
        }
      });
    },
    reset() {
      // 调用element里form表单的函数来重置表单，如果上面的data里设置了初始值，则重置之后就会是初始值
      this.$refs.loginFormRef.resetFields();
    }
  }
};
</script>
<style>
.login_label .el-form-item__label {
  color: #fff;
}
</style>
<style lang="less" scoped>
.login_container {
  // background-color: #2b4b6b;
  background: url("../../assets/img/backgorund.jpg");
  height: 100%;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: rgba(255, 255, 255, 0.15);
  border-radius: 5px;

  // 绝对定位
  position: absolute;
  left: 50%;
  top: 50%;
  // 偏移量，偏移相对自身50%
  transform: translate(-50%, -50%);
}

.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  // 偏移量，偏移相对自身50%
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.15);
  img {
    height: 100%;
    width: 100%;
    border-radius: 50%;
    background-color: rgba(248, 245, 245, 0.2);
  }
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.login_form_btn {
  text-align: center;
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
}
.el-input {
  width: 95%;
}
.el-form-item__label {
  background: #fff;
}
.el-button {
  background: #183c5e;
  color: #fff;
}
</style>
