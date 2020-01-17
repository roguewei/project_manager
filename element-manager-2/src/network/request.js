import axios from "axios";
import { Message } from "element-ui";
import router from "@/router/index";
import { getToken, removeAll } from "@/utils/auth";

axios.defaults.withCredentials = true;

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000 // request timeout
});

// request interceptor
service.interceptors.request.use(
  config => {
    // 为请求头对象添加Token验证的Authorization字段
    config.headers.authorization = getToken();
    return config;
  },
  error => {
    // do something with request error
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data;

    if (res.status === 500105 || res.status === 500205) {
      Message({
        message: res.msg || "Error",
        type: "error",
        duration: 5 * 1000
      });
      removeAll();
      // 重定向
      router.push("/login");
      return Promise.reject(new Error(res.msg || "Error"));
    } else if (res.status !== 200) {
      return Promise.reject(new Error(res.msg));
    } else {
      return res;
    }
  },
  error => {
    Message({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

function errMsg(msg) {
  Message({
    message: msg,
    type: "error"
  });
}

export default service;
