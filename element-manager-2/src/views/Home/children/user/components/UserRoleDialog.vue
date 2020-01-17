<template>
  <!-- 修改角色对话框 -->
  <el-dialog
    title="注销用户"
    :visible.sync="isaaShow"
    width="30%"
    @close="changeShow"
  >
    <span>确定删除用户吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="changeShow">取a消</el-button>
      <el-button type="primary">确 定</el-button>
    </span>
  </el-dialog>
  <!-- <transition name="fade">
    <div v-if="dialogProp">
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeShow">取 消</el-button>
        <el-button type="primary">确 定</el-button>
      </span>
    </div>
  </transition> -->
</template>

<script>
export default {
  props: {
    dialogProp: true
  },
  data() {
    return {
      showDialog: this.dialogProp
    };
  },
  computed: {
    isaaShow() {
      return true;
    }
  },
  methods: {
    changeShow() {
      this.$emit("changeShow", false);
    }
  },
  created() {}
};
</script>
<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
