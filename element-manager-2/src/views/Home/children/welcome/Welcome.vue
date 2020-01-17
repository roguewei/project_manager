<template>
  <div class="dashboard-editor-container">
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
    </el-breadcrumb>

    <panel-group
      v-if="myRole.roleName === 'admin'"
      @handleSetLineChartData="handleSetLineChartData"
    />

    <div v-else>
      aaaaa
    </div>
  </div>
</template>
<script>
import PanelGroup from "./components/PanelGroup";
// import LineChart from "./components/LineChart";
import { getUserRole } from "@/utils/auth.js";

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 10, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
};

export default {
  name: "",
  components: {
    PanelGroup
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis
    };
  },
  computed: {
    myRole() {
      return getUserRole();
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type];
    }
  }
};
</script>
<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
