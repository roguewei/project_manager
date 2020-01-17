<template>
  <!-- 编辑角色权限 -->
  <el-dialog
    title="编辑角色权限"
    :visible.sync="isShow"
    width="50%"
    @close="dialogClose"
  >
    <!-- 内容主体区 -->
    <el-form
      :model="perFormData"
      :rules="perFormDataRules"
      ref="perFormDataRef"
      label-width="100px"
    >
      <el-form-item label="id" prop="id" class="el-form-item-id">
        <el-input v-model="perFormData.roleId"></el-input>
      </el-form-item>
      <el-form-item label="角色名" prop="roleName">
        <el-input disabled v-model="perFormData.roleDesc"></el-input>
      </el-form-item>
      <el-form-item label="权限" prop="perId" v-if="destroyTree">
        <el-tree
          :data="perTree"
          ref="tree"
          show-checkbox
          node-key="id"
          :default-checked-keys="perFormData.perId"
          :props="defaultProps"
        >
        </el-tree>
      </el-form-item>
    </el-form>

    <!-- 底部按钮区 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="perClose">取 消</el-button>
      <el-button type="primary" @click="dialogSubmit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: {
    dialogVisiable: {
      type: Boolean,
      default: false
    },
    isAdd: {
      type: Boolean,
      default: true
    },
    perFormData: {
      roleId: "",
      roleDesc: "",
      perId: []
    },
    perTree: {
      type: Array
    },
    destroyTree: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      perFormDataRules: {
        roleName: [{ required: true, message: "请输角色名", trigger: "blur" }],
        roleDesc: [{ required: true, message: "请输角色描述", trigger: "blur" }]
      },
      defaultProps: {
        children: "children",
        label: "pername"
      }
    };
  },
  computed: {
    isShow: {
      get() {
        return this.dialogVisiable;
      },
      set(val) {
        this.$emit("perClose");
      }
    }
  },
  watch: {},
  methods: {
    dialogClose() {
      // this.$refs.perFormDataRef.resetFields();
    },
    perClose() {
      this.$emit("perClose");
    },
    dialogSubmit() {
      let selectPer = this.$refs.tree.getCheckedNodes(false, true);
      let selectPerId = selectPer.map(n => {
        return n.id;
      });
      this.perFormData.perId = selectPerId;
      this.$emit("submitPerData", this.perFormData);
    }
  },
  created() {}
};
</script>
<style>
.el-form-item-id {
  display: none;
}
</style>
