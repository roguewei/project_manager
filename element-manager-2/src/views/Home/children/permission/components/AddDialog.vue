<template>
  <!-- 编辑权限 -->
  <el-dialog title="编辑权限" :visible.sync="isShow" width="50%" @close="dialogClose">
    <!-- 内容主体区 -->
    <el-form :model="formData" :rules="formDataRules" ref="formDataRef" label-width="100px">
      <el-form-item label="id" prop="id" class="el-form-item-id">
        <el-input v-model="formData.id"></el-input>
      </el-form-item>
      <el-form-item label="权限名称" prop="pername">
        <el-input clearable v-model="formData.pername"></el-input>
      </el-form-item>
      <el-form-item label="权限路径" prop="perurl">
        <el-input clearable v-model="formData.perurl"></el-input>
      </el-form-item>
      <el-form-item label="权限类型" prop="type">
        <!-- <el-input v-model="formData.type"></el-input> -->
        <el-select clearable v-model="formData.type" placeholder="请选择" @change="typeChange">
          <el-option
            v-for="item in perType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="父级菜单" prop="parentid">
        <!-- <el-input v-model="formData.type"></el-input> -->
        <el-select clearable v-model="formData.parentid" placeholder="请选择" @change="parentChange">
          <el-option :key="0" label="无上级" :value="0" />
          <el-option
            v-for="item in parentPer"
            :key="item.id"
            :label="item.pername"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="系统图标" prop="icon">
        <!-- <el-input v-model="formData.type"></el-input> -->
        <el-select
          filterable
          clearable
          v-model="formData.icon"
          placeholder="请选择"
          :remote-method="remoteMethod"
          @change="parentChange"
        >
          <el-option :key="0" label="选择图标" :value="0" disabled />
          <el-option
            v-for="item in iconList"
            :key="item.id"
            :label="item.iconClass"
            :value="item.iconClass"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <i :class="formData.icon"></i>
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
import { getIconAll } from "@/api/elicon.js";

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
      pername: "",
      perurl: "",
      type: "",
      parentid: 0,
      icon: ""
    },
    parentPer: {
      type: Array
    }
  },
  data() {
    return {
      formDataRules: {
        pername: [{ required: true, message: "请输权限名", trigger: "blur" }],
        perurl: [{ required: true, message: "请输权限路径", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        parentid: [{ required: true, message: "请选择父级", trigger: "blur" }]
      },
      perType: [
        {
          value: "0",
          label: "父级菜单"
        },
        {
          value: "1",
          label: "子菜单"
        }
      ],
      iconList: []
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
  watch: {
    formData: {
      handler(val) {
        this.setUrlRule(val.type);
      }
    }
  },
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
    },
    parentChange(type) {
      console.log(type);
    },
    typeChange(type) {
      this.setUrlRule(type);
    },
    setUrlRule(type) {
      if (type === "0") {
        this.formDataRules.perurl = [];
      } else {
        this.formDataRules.perurl = [
          { required: true, message: "请输权限路径", trigger: "blur" }
        ];
      }
    },
    getIconList() {
      getIconAll()
        .then(res => {
          this.iconList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    remoteMethod(query) {
      console.log(query);
    }
  },
  created() {
    this.getIconList();
  }
};
</script>
<style>
.el-form-item-id {
  display: none;
}
</style>
