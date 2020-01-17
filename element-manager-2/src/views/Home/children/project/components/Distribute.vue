<template>
  <!-- 编辑项目 -->
  <el-dialog title="项目分配" :visible.sync="isShowDis" width="50%" @close="dialogClose">
    <!-- 内容主体区 -->
    <el-form :model="formData" :rules="formDataRules" ref="formDataRef" label-width="100px">
      <el-form-item label="proId" prop="proId" class="el-form-item-id">
        <el-input v-model="formData.proId"></el-input>
      </el-form-item>
      <el-form-item label="项目名" prop="proName">
        <el-input disabled clearable v-model="formData.proName"></el-input>
      </el-form-item>
      <el-form-item label="项目组" prop="groupId">
        <!-- <el-input v-model="formData.type"></el-input> -->
        <el-select
          @change="groupChange"
          :disabled="!isGroup"
          v-model="formData.groupId"
          placeholder="请选择"
          clearable
        >
          <el-option
            v-for="item in groupsList"
            :key="item.id"
            :label="item.groupName"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目人" prop="staffId">
        <!-- <el-input v-model="formData.type"></el-input> -->
        <el-select
          @change="perChange"
          :disabled="!isPer"
          v-model="formData.staffId"
          placeholder="请选择"
          clearable
          filterable
        >
          <el-option
            v-for="item in staffList"
            :key="item.id"
            :label="item.username"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <!-- 底部按钮区 -->
    <span slot="footer" class="dialog-footer">
      <el-button @click="addClose">取 消</el-button>
      <el-button type="primary" @click="dialogSubmit">确 定</el-button>
    </span>
  </el-dialog>
</template>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';

export default {
  props: {
    dialogVisiable: {
      type: Boolean,
      default: false
    },
    formData: {
      proId: 0,
      proName: "",
      groupId: 0,
      staffId: 0
    },
    groupsList: {
      type: Array
    },
    staffList: {
      type: Array
    }
  },
  data() {
    // 这里存放数据
    return {
      isGroup: true,
      isPer: true,
      formDataRules: {
        groupId: [{ required: true, message: "请选择项目组", trigger: "blur" }],
        staffId: [{ required: true, message: "请选择项目人", trigger: "blur" }]
      }
    };
  },
  // 监听属性 类似于data概念
  computed: {
    isShowDis: {
      get() {
        return this.dialogVisiable;
      },
      set(val) {
        this.$emit("disClose");
      }
    }
  },
  // 监控data中的数据变化
  watch: {},
  // 方法集合
  methods: {
    dialogClose() {
      this.$refs.formDataRef.resetFields()
    },
    addClose() {
      this.$emit("disClose");
    },
    dialogSubmit() {
      console.log(this.formData);
      // this.$refs.formDataRef.validate(valid => {
      //   if (!valid) return;

      //   this.$emit("submitDis", this.formData);
      // });
    },
    groupChange() {
      console.log(this.formData);

      if (this.formData.groupId !== '') {
        this.isPer = false
        this.formDataRules.staffId = []
      } else {
        this.isPer = true
        this.formDataRules.staffId = [{ required: true, message: "请选择项目人", trigger: "blur" }]
      }
      // this.$emit('groupChange', this.formData)
    },
    perChange() {
      console.log(this.formData);
      if (this.formData.staffId !== '') {
        this.isGroup = false
        this.formDataRules.groupId = []
      } else {
        this.isGroup = true
        this.formDataRules.groupId = [{ required: true, message: "请选择项目组", trigger: "blur" }]
      }
      // this.$emit('perChange', this.formData)
    }
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {

  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {
  },
  beforeCreate() { }, // 生命周期 - 创建之前
  beforeMount() { }, // 生命周期 - 挂载之前
  beforeUpdate() { }, // 生命周期 - 更新之前
  updated() { }, // 生命周期 - 更新之后
  beforeDestroy() { }, // 生命周期 - 销毁之前
  destroyed() { }, // 生命周期 - 销毁完成
  activated() { }, // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style>
.el-form-item-id {
  display: none;
}
</style>