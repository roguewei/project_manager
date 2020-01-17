<template>
  <el-container class="home-container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img :src="userInfo.headpath" alt="aaa" />
        <span>项目管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出登录</el-button>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isClose ? '64px' : '200px'">
        <div class="toggle-button" @click="togglemenu">|||</div>
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#ffd04b"
          unique-opened
          :default-active="$route.path"
          :collapse="isClose"
          :collapse-transition="false"
          :router="true"
        >
          <!-- 一级菜单 -->
          <el-menu-item index="/welcome">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-submenu
            :index="item.id + ''"
            v-for="item in menuList"
            :key="item.id + ''"
          >
            <!-- 一级菜单内容 -->
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{ item.pername }}</span>
            </template>

            <!-- 二级菜单 -->
            <el-menu-item
              :index="menuitem.perurl"
              v-for="menuitem in item.children"
              :key="menuitem.id + ''"
            >
              <!-- 二级菜单内容 -->
              <template slot="title">
                <i :class="menuitem.icon"></i>
                <span>{{ menuitem.pername }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 右侧内容主体 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import request from "@/network/request";
import { getPermission } from "@/api/permission";
import { logout } from "@/api/user";
import { getUserInfo, removeAll } from "@/utils/auth";

export default {
  name: "",
  data() {
    return {
      msg: "this is home page",
      menuList: [],
      isClose: false,
      userInfo: ""
    };
  },
  methods: {
    logout() {
      request({
        url: "/logout",
        method: "get"
      })
        .then(res => {
          this.$message({
            message: res.data,
            type: "success",
            showClose: true
          });
        })
        .catch(err => {
          console.log(err);
        });
      removeAll();
      this.$router.push("/login");
    },
    getMenuList() {
      this.userInfo = getUserInfo();
      getPermission({ username: this.userInfo.username })
        .then(data => {
          if (data.status !== 200) {
            return this.$message({
              message: data.msg,
              type: "success",
              showClose: true
            });
          } else {
            this.menuList = data.data;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    togglemenu() {
      this.isClose = !this.isClose;
    }
  },
  computed: {
    defaultPath() {
      return this.$route.path;
    }
  },
  created() {
    this.getMenuList();
  }
};
</script>
<style>
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
}

.el-header div {
  display: flex;
  align-items: center;
}

.el-header div span {
  margin-left: 15px;
}
.el-header img {
  height: 50px;
  width: 50px;
}
.el-aside {
  background-color: #333744;
}
.el-menu {
  border-right: none;
}
.el-main {
  background-color: #eaedf1;
}
.home-container {
  color: #fff;
  height: 100%;
}
.el-menu i {
  margin-right: 5px;
}
.toggle-button {
  background-color: #4a5064;
  text-align: center;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  /* 字符的间距 */
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
