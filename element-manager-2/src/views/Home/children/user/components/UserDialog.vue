<template>
  <!-- 新增、修改用户对话框 -->
  <el-dialog
    title="新增、修改用户"
    :visible.sync="dialogVisiable"
    width="50%"
    @close="addDialogClose"
    @before-close="addDialogBeforeClose"
  >
    <!-- 内容主体区 -->
    <el-form
      :model="addForm"
      :rules="addFormRules"
      ref="addFormRef"
      label-width="70px"
    >
      <el-form-item label="id" prop="id" class="el-form-item-id">
        <el-input v-model="addForm.id"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="addForm.username"
          :disabled="!isAddDialog"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="密码"
        prop="password"
        :class="{ showpwdinput: !isAddDialog }"
      >
        <el-input v-model="addForm.password"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="addForm.mobile"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio v-model="addForm.sex" label="1">男</el-radio>
        <el-radio v-model="addForm.sex" label="2">女</el-radio>
      </el-form-item>
    </el-form>
    <!-- 底部按钮区 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="cencelShowDialog">取 消</el-button>
      <el-button type="primary" @click="dialogSubmit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: {
    addDialogVisible: {
      type: Boolean,
      default: false
    },
    isAddDialog: {
      type: Boolean,
      default: true
    }
  },
  data() {
    // 自定义校验规则
    var checkAge = (rule, value, callback) => {
      const regAge = /\d{11}/;
      if (!value) {
        return callback(new Error("手机号不能为空"));
      } else if (!regAge.test(value)) {
        console.log("--------------");

        return callback(new Error("请输入11位数字值"));
      } else {
        callback();
      }
    };

    return {
      // 添加用户的表单数据对象
      addForm: {
        username: "",
        password: "",
        mobile: "",
        sex: "1"
      },
      // 表单验证规则
      addFormRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        mobile: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          // { type: 'number', message: '年龄必须为数字值' }
          { validator: checkAge, trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    dialogVisiable() {
      return this.addDialogVisible;
    }
  },
  methods: {
    dialogSubmit() {},
    addDialogBeforeClose() {
      this.$refs.addFormRef.resetFields();
    },
    addDialogClose() {
      this.$refs.addFormRef.resetFields();
    },
    cencelShowDialog() {
      this.addDialogVisible = !this.addDialogVisible;
      this.$emit("cencelShowDialog", this.addDialogVisible);
    }
  },
  created() {}
};
</script>
