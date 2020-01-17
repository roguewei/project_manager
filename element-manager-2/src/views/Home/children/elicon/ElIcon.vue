<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统</el-breadcrumb-item>
      <el-breadcrumb-item>图标管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入图标类名"
            v-model="queryInfo.iconClass"
            class="input-with-select"
            clearable
            @clear="getIconList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getIconList"
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
        :data="iconList"
        style="width: 100%"
        boder
        stripe
      >
        <el-table-column type="index" width="150"></el-table-column>
        <el-table-column
          prop="iconClass"
          label="图标类名"
          width="250"
        ></el-table-column>
        <el-table-column label="图标" width="250">
          <template slot-scope="scope">
            <i :class="scope.row.iconClass"></i>
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
      :formData="iconFormData"
    />
  </div>
</template>
<script>
import {
  getIconAll,
  getIcon,
  getIconById,
  addIcon,
  updateIcon,
  delIcon
} from "@/api/elicon.js";
import AddDialog from "./components/AddDialog";

export default {
  name: "",
  components: {
    AddDialog
  },
  data() {
    return {
      loading: false,
      editDialogLoading: false,

      total: 0,
      pageSizes: [5, 10, 100, 500],
      iconList: [],
      queryInfo: {
        iconClass: "",
        page: 1,
        length: 10
      },

      // 编辑图标弹窗
      isShow: false,
      isAddDialog: true,
      iconFormData: {}
    };
  },
  methods: {
    handleSizeChange(length) {
      this.queryInfo.length = length;
      this.getIconList();
    },
    handleCurrentChange(current) {
      this.queryInfo.page = current;
      this.getIconList();
    },
    showAddDialog() {
      this.isAddDialog = true;
      this.isShow = true;
    },
    async showEditDialog(id) {
      this.isAddDialog = false;
      this.isShow = true;

      await getIconById(id)
        .then(res => {
          this.iconFormData = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    showDelDialog(id) {
      this.$confirm("此操作将删除该图标, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delIcon(id).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });

            this.getIconList();
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
        addIcon(formData)
          .then(res => {
            this.isShow = false;
            this.getIconList();

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
        updateIcon(formData)
          .then(res => {
            this.isShow = false;
            this.getIconList();

            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      }
    },
    getIconList() {
      this.loading = true;
      getIcon(this.queryInfo)
        .then(res => {
          this.iconList = res.data;
          this.total = res.total;

          this.loading = false;
        })
        .catch(err => {
          console.log(err);
          this.loading = false;
        });
    }
  },
  created() {},
  mounted() {
    this.getIconList();
  }
};
</script>
