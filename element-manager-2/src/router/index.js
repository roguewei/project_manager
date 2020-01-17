import Vue from "vue";
import VueRouter from "vue-router";
import routes from "./routes";
import { getToken } from "@/utils/auth";

/**
 * 重写路由的push方法
 */
const routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error => error);
};

Vue.use(VueRouter);

const router = new VueRouter({
  routes,
  // 没有配置的话浏览器路径上显示的是带#的hash路径，配置了该属性就没有#了
  // HTML5的history模式
  mode: "history"
});

// 配置路由导航守卫
router.beforeEach((to, from, next) => {
  // 如果用户访问的登录页，就放行
  if (to.path === "/login") {
    return next();
  } else {
    // 如果不是登录页就去查是否有token
    const token = getToken();
    // 如果没有就跳转到登录
    if (!token) {
      return next("/login");
    }
    next();
  }
});

// 3、导出路由
export default router;
