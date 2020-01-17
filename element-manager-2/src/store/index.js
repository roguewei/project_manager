import Vue from "vue";
import Vuex from "vuex";

import mutations from "./mutations";

// 安装插件
Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    userInfo: {},
    meetingId: ""
  },
  mutations
});

export default store;
