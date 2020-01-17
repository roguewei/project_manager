<!--  -->
<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>应用</el-breadcrumb-item>
      <el-breadcrumb-item>我的项目</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="11">
        <el-col :span="8.5">
          <!-- <el-date-picker
            v-model="queryInfo.startTime"
            type="date"
            placeholder="选择日期"
            format="yyyy 年 MM 月 dd 日"
            value-format="timestamp"
          >
          </el-date-picker>-->
          <el-date-picker
            v-model="queryTime"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="timestamp"
            unlink-panels
            @change="selectTime"
          ></el-date-picker>
        </el-col>
        <el-col :span="6">
          <el-input
            placeholder="请输入项目名"
            v-model="queryInfo.proName"
            class="input-with-select"
            clearable
            @clear="getMyProjectList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getMyProjectList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="showAddGrDialog()">新增个人项目</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="showAddBmDialog()">新增组项目</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table v-loading="loading" :data="proList" style="width: 100%" boder stripe>
        <el-table-column type="index" width="100"></el-table-column>
        <el-table-column prop="proName" label="项目名" width="180"></el-table-column>
        <el-table-column prop="startTimeFormat" label="开始时间" width="180"></el-table-column>
        <el-table-column prop="endTimeFormat" label="结束时间" width="180"></el-table-column>
        <el-table-column label="完成进度" width="180">
          <template slot-scope="scope">
            <el-progress
              :text-inside="true"
              :stroke-width="20"
              :percentage="scope.row.proSchedule"
              :color="customColors"
            ></el-progress>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <!-- 使用作用域插槽获取当前行的数据 -->
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="修改" placement="top" :enterable="false">
              <el-button
                type="warning"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>

            <el-tooltip class="item" effect="dark" content="删除" placement="top" :enterable="false">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="getMyProjectList(scope.row.id)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="pageSizes"
        :page-size="queryInfo.length"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <MyProDialog
      v-loading="editDialogLoading"
      :dialogVisiable="isShow"
      @addClose="closeEditDialog"
      @submitData="submitData"
      :isAdd="isAddDialog"
      :formData="proFormData"
      :parentList="parentProList"
    />
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';
import { getMyProject, getMyParentProject } from "@/api/gup.js";
import {
  getProject,
  getProjectAll,
  getProjectById,
  addProject,
  updateProject,
  delProjectById,
  addMyProject
} from "@/api/project.js";
import { getUserInfo, getUserRole, getUserGroup } from "@/utils/auth.js";
import MyProDialog from "./components/MyProDialog";

export default {
  // import引入的组件需要注入到对象中才能使用
  components: {
    MyProDialog
  },
  data() {
    // 这里存放数据
    return {
      loading: false,
      editDialogLoading: false,

      total: 0,
      pageSizes: [10, 20, 100, 500],
      customColors: [
        { color: '#409eff', percentage: 50 },
        { color: '#f39d4c', percentage: 99 },
        { color: '#52c114', percentage: 100 }
      ],
      proList: [],
      queryTime: [],
      queryInfo: {
        proName: "",
        startTime: "",
        endTime: "",
        groupId: "",
        staffId: "",
        cliId: "",
        proId: "",
        page: 1,
        length: 10
      },
      // 编辑弹窗
      isShow: false,
      isAddDialog: true,
      proFormData: {
        parentId: "0"
      },
      selfId: 0,
      gupForm: {},
      parentProList: [],
      group: '',
      user: '',
      role: '',
      isGroupPro: false
    };
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 方法集合
  methods: {
    handleSizeChange(length) {
      this.queryInfo.length = length;
      this.getMyProjectList();
    },
    handleCurrentChange(current) {
      this.queryInfo.page = current;
      this.getMyProjectList();
    },
    showAddGrDialog() {
      this.isGroupPro = false
      this.gupForm.groupId = null;
      this.gupForm.userId = this.user.id

      this.selfId = 0;
      this.isAddDialog = true;
      this.isShow = true;

      this.proFormData.parentId = 0;
      this.getParentProList();
    },
    showAddBmDialog() {
      this.isGroupPro = true
      if (this.group === undefined) {
        this.$message({
          message: "您还未进入任何部门",
          type: "error",
          showClose: true
        });
        return
      }
      this.gupForm.groupId = this.group.id;
      this.gupForm.userId = null

      this.selfId = 0;
      this.isAddDialog = true;
      this.isShow = true;

      this.proFormData.parentId = 0;
      this.getParentProList();
    },
    async showEditDialog(id) {
      this.selfId = id;
      this.isAddDialog = false;
      this.isShow = true;

      await this.getParentProList();

      await getProjectById(id)
        .then(res => {
          this.proFormData = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    closeEditDialog() {
      this.isShow = false;
    },
    submitData(type, formData) {
      this.editDialogLoading = true;
      if (type) {
        addMyProject(formData, this.gupForm)
          .then(res => {
            this.isShow = false;
            this.getMyProjectList();
            this.editDialogLoading = false;
          })
          .catch(err => {
            this.$message({
              message: err,
              type: "error",
              showClose: true
            });
            this.editDialogLoading = false;
          });
      } else {
        updateProject(formData)
          .then(res => {
            this.isShow = false;
            this.getMyProjectList();
            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      }
    },
    getParentProList() {
      let parentForm = {};
      if (this.isGroupPro) {
        if (this.group === undefined) {
          parentForm.groupId = null;
        } else {
          parentForm.groupId = this.group.id;
        }
        parentForm.staffId = null
      } else {
        parentForm.groupId = null;
        parentForm.staffId = this.user.id;
      }
      parentForm.id = this.selfId;
      getMyParentProject(parentForm).then(res => {
        this.parentProList = res.data;
      });
    },
    selectTime(time) {
      if (time !== null) {
        this.queryInfo.startTime = time[0];
        this.queryInfo.endTime = time[1];
      } else {
        this.queryInfo.startTime = "";
        this.queryInfo.endTime = "";
      }
      this.getMyProjectList();
    },
    getMyProjectList() {
      this.loading = true;
      getMyProject(this.queryInfo)
        .then(res => {
          this.proList = res.data;
          this.total = res.total;
          this.loading = false;

          this.getParentProList();
        })
        .catch(err => {
          this.loading = false;
        });
    }
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() { },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {
    this.group = getUserGroup();
    this.role = getUserRole();
    this.user = getUserInfo();

    if (this.group !== undefined) {
      this.queryInfo.groupId = this.group.id;
    }
    if (this.role.roleName !== "client") {
      this.queryInfo.staffId = this.user.id;
    } else {
      this.queryInfo.cliId = this.user.id;
    }
    this.getMyProjectList();
  },
  beforeCreate() { }, // 生命周期 - 创建之前
  beforeMount() { }, // 生命周期 - 挂载之前
  beforeUpdate() { }, // 生命周期 - 更新之前
  updated() { }, // 生命周期 - 更新之后
  beforeDestroy() { }, // 生命周期 - 销毁之前
  destroyed() { }, // 生命周期 - 销毁完成
  activated() { } // 如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style lang="scss" scoped>
/* @import url(); 引入公共css类 */
</style>
