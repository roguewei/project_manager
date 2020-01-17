<template>
  <div id="app">
      <router-view v-if="isRouterAlive" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'app',
  // 通过provider来提供变量,此处为重新加载路由的方法
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return  {
      // 刷新页面标识
      isRouterAlive: true
    }
  },
  components: {},
  methods: {
    // 通过对router-view的v-if属性赋值，重新加载路由
    reload() {
      
      this.isRouterAlive = false;
      this.$nextTick(() => {
        this.isRouterAlive = true;
      })
    }
  },
  created(){
  }
}
</script>

<style>
/* #app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
} */
@import 'assets/css/base.css';
@import 'assets/fonts/iconfont.css';
</style>
