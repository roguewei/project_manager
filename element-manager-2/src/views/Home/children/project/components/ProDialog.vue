<template>
  <!-- 编辑项目 -->
  <el-dialog
    title="编辑项目"
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
      <el-form-item label="项目名" prop="proName">
        <el-input clearable v-model="formData.proName"></el-input>
      </el-form-item>
      <el-form-item label="项目描述" prop="proDesc">
        <el-input
          clearable
          type="textarea"
          maxlength="255"
          show-word-limit
          v-model="formData.proDesc"
        ></el-input>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="formData.startTime"
          type="date"
          placeholder="选择日期"
          format="yyyy 年 MM 月 dd 日"
          value-format="timestamp"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成时间" prop="endTime">
        <el-date-picker
          v-model="formData.endTime"
          type="date"
          placeholder="选择日期"
          format="yyyy 年 MM 月 dd 日"
          value-format="timestamp"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际完成时间" prop="relEndTime">
        <el-date-picker
          v-model="formData.relEndTime"
          type="date"
          placeholder="选择日期"
          format="yyyy 年 MM 月 dd 日"
          value-format="timestamp"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成进度" prop="proSchedule">
        <el-slider v-model="formData.proSchedule"></el-slider>
      </el-form-item>
      <el-form-item label="父级项目" prop="parentId">
        <!-- <el-input v-model="formData.type"></el-input> -->
        <el-select clearable v-model="formData.parentId" placeholder="请选择">
          <el-option :key="0" label="无上级" :value="0" />
          <el-option
            v-for="item in parentList"
            :key="item.id"
            :label="item.proName"
            :value="item.id"
          >
          </el-option>
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
      proName: "",
      proDesc: "",
      startTime: "",
      endTime: "",
      startTimeFormat: "",
      endTimeFormat: "",
      relEndTime: "",
      relEndTimeFormat: "",
      proSchedule: "",
      parentId: ""
    },
    parentList: {
      type: Array
    }
  },
  data() {
    return {
      formDataRules: {
        proName: [{ required: true, message: "请输图标类名", trigger: "blur" }]
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
