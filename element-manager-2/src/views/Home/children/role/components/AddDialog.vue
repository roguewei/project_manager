<template>
  <!-- 编辑角色 -->
  <el-dialog
    title="编辑角色"
    :visible.sync="isShow"
    width="50%"
    @close="dialogClose"
  >
    <!-- 内容主体区 -->
    <el-form
      :model="formData"
      :rules="formDataRules"
      ref="formDataRef"
      label-width="100px"
    >
      <el-form-item label="id" prop="id" class="el-form-item-id">
        <el-input v-model="formData.id"></el-input>
      </el-form-item>
      <el-form-item label="角色名" prop="roleName">
        <el-input v-model="formData.roleName"></el-input>
      </el-form-item>
      <el-form-item label="角色描述" prop="roleDesc">
        <el-input v-model="formData.roleDesc"></el-input>
      </el-form-item>
    </el-form>

    <!-- 底部按钮区 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="addClose">取 消</el-button>
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
    formData: {
      id: "",
      roleName: "",
      roleDesc: ""
    },
    roleId: 0
  },
  data() {
    return {
      formDataRules: {
        roleName: [{ required: true, message: "请输角色名", trigger: "blur" }],
        roleDesc: [{ required: true, message: "请输角色描述", trigger: "blur" }]
      }
    };
  },
  computed: {
    isShow: {
      get() {
        return this.dialogVisiable;
      },
      set(val) {
        this.$emit("addClose");
      }
    }
  },
  watch: {},
  methods: {
    dialogClose() {
      this.$refs.formDataRef.resetFields();
    },
    addClose() {
      this.$emit("addClose");
    },
    dialogSubmit() {
      this.$refs.formDataRef.validate(valid => {
        if (!valid) return;

        this.$emit("submitData", this.isAdd, this.formData);
      });
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
