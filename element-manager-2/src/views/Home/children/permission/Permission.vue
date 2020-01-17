<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="5">
          <el-select
            filterable
            clearable
            v-model="queryInfo.type"
            placeholder="请选择权限类型"
            @change="change"
          >
            <el-option
              v-for="item in parameterList"
              :key="item.id"
              :label="item.paramDesc"
              :value="item.paramValue"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-input
            placeholder="请输入权限名称"
            v-model="queryInfo.pername"
            class="input-with-select"
            clearable
            @clear="getPerList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getPerList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="showAddDialog()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table
        v-loading="loading"
        :data="perList"
        style="width: 100%"
        boder
        stripe
      >
        <el-table-column type="index" width="150"></el-table-column>
        <el-table-column
          prop="pername"
          label="权限名称"
          width="200"
        ></el-table-column>
        <el-table-column
          prop="perurl"
          label="权限路径"
          width="200"
        ></el-table-column>
        <el-table-column label="权限图标" width="200">
          <template slot-scope="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <!-- 使用作用域插槽获取当前行的数据 -->
          <template slot-scope="scope">
            <el-tooltip
              class="item"
              effect="dark"
              content="修改"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>

            <el-tooltip
              class="item"
              effect="dark"
              content="删除"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="showDelDialog(scope.row.id)"
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

    <AddDialog
      v-loading="editDialogLoading"
      :dialogVisiable="isShow"
      @addClose="closeEditDialog"
      @submitData="submitData"
      :isAdd="isAddDialog"
      :formData="editFormData"
      :parentPer="parentPerList"
    />
  </div>
</template>
<script>
import {
  query,
  queryById,
  queryParant,
  addPer,
  updatePer,
  delPer
} from "@/api/permission.js";
import { getParameter } from "@/api/parameter.js";
import AddDialog from "./components/AddDialog";
export default {
  name: "",
  components: {
    AddDialog
  },
  // 直接注入依赖
  inject: ["reload"],
  data() {
    return {
      loading: false,
      editDialogLoading: false,

      total: 0,
      pageSizes: [5, 10, 100, 500],
      perList: [],
      parentPerList: [],
      parameterList: [],
      queryInfo: {
        pername: "",
        type: "",
        page: 1,
        length: 10
      },
      queryParameter: {
        paramType: "menu_type"
      },
      // 编辑弹窗
      isShow: false,
      isAddDialog: true,
      editFormData: {}
    };
  },
  methods: {
    handleSizeChange(length) {
      this.queryInfo.length = length;
      this.getPerList();
    },
    handleCurrentChange(current) {
      this.queryInfo.page = current;
      this.getPerList();
    },
    showAddDialog() {
      this.isAddDialog = true;
      this.isShow = true;

      this.editFormData.parentid = 0;
    },
    async showEditDialog(id) {
      this.isAddDialog = false;
      this.isShow = true;

      await queryById(id)
        .then(res => {
          this.editFormData = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    showDelDialog(id) {
      this.$confirm("此操作将删除该权限, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delPer(id).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });

            this.getPerList();
          });
        })
        .catch(() => {});
    },
    closeEditDialog() {
      this.isShow = false;
    },
    submitData(type, formData) {
      this.editDialogLoading = true;
      if (type) {
        addPer(formData)
          .then(res => {
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.isShow = false;
            this.getPerList();
            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      } else {
        updatePer(formData)
          .then(res => {
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.isShow = false;
            this.getPerList();
            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      }
      // 刷新导航菜单
      this.reload();
    },
    // 刷新导航菜单
    btnClick() {
      this.reload();
    },
    change() {
      this.getPerList();
    },
    getPerList() {
      query(this.queryInfo).then(res => {
        this.perList = res.data;
        this.total = res.total;
      });
    },
    getParameterList() {
      getParameter(this.queryParameter).then(res => {
        this.parameterList = res.data;
      });
    },
    getparentPerList() {
      queryParant().then(res => {
        this.parentPerList = res.data;
      });
    }
  },
  created() {},
  mounted() {
    this.getPerList();
    this.getParameterList(this.queryParameter);
    this.getparentPerList();
  }
};
</script>
<style></style>
