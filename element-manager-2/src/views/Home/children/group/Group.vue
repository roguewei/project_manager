<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统</el-breadcrumb-item>
      <el-breadcrumb-item>部门管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入部门名称"
            v-model="queryInfo.groupName"
            class="input-with-select"
            clearable
            @clear="getGroupsList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getGroupsList"
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
        :data="groupsList"
        style="width: 100%"
        boder
        stripe
      >
        <el-table-column type="index" width="150"></el-table-column>
        <el-table-column
          prop="groupName"
          label="部门名称"
          width="250"
        ></el-table-column>
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
              content="分配权限"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-key"
                size="mini"
                @click="showPerDialog(scope.row)"
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
    />

    <EditGroupPer
      v-loading="perDialogLoading"
      @perClose="closePerDialog"
      @submitPerData="submitPerData"
      :dialogVisiable="isShowPer"
      :perFormData="perFormData"
      :perTree="perTreeList"
      :destroyTree="destroyTree"
    />
  </div>
</template>
<script>
import {
  getGroupListParams,
  getGroupById,
  addGroup,
  updateGroup,
  delGroup
} from "@/api/groups.js";
import { saveGur } from "@/api/grp.js";
import { queryByGroupId, queryAll } from "@/api/permission.js";
import AddDialog from "./components/AddDialog";
import EditGroupPer from "./components/EditGroupPer";

export default {
  name: "",
  components: {
    AddDialog,
    EditGroupPer
  },
  data() {
    return {
      loading: false,
      editDialogLoading: false,
      perDialogLoading: false,

      total: 0,
      pageSizes: [5, 10, 100, 500],
      groupsList: [],
      queryInfo: {
        groupName: "",
        page: 1,
        length: 10
      },
      // 编辑弹窗
      isShow: false,
      isAddDialog: true,
      editFormData: {},
      // 编辑权限弹窗
      isShowPer: false,
      destroyTree: true,
      perTreeList: [],
      perFormData: {
        groupId: "",
        groupName: "",
        perId: []
      }
    };
  },
  methods: {
    handleSizeChange(length) {
      this.queryInfo.length = length;
      this.getGroupsList();
    },
    handleCurrentChange(current) {
      this.queryInfo.page = current;
      this.getGroupsList();
    },
    showAddDialog() {
      this.isAddDialog = true;
      this.isShow = true;
    },
    async showEditDialog(id) {
      this.isAddDialog = false;
      this.isShow = true;

      await getGroupById(id)
        .then(res => {
          this.editFormData = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    showDelDialog(id) {
      this.$confirm("此操作将删除该部门, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delGroup(id).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });

            this.getGroupsList();
          });
        })
        .catch(() => {});
    },
    async showPerDialog(groupInfo) {
      this.isShowPer = true;
      this.destroyTree = false;
      this.perFormData.groupId = groupInfo.id;
      this.perFormData.groupName = groupInfo.groupName;

      await queryByGroupId(groupInfo.id)
        .then(res => {
          let result = res.data;
          this.perFormData.perId = result
            .filter(n => {
              return n.type !== "0";
            })
            .map(n => {
              return n.id;
            });
          this.destroyTree = true;
        })
        .catch(err => {
          console.log(err);
        });
    },
    closeEditDialog() {
      this.isShow = false;
    },
    closePerDialog() {
      this.isShowPer = false;
    },
    submitData(type, formData) {
      this.editDialogLoading = true;
      if (type) {
        addGroup(formData)
          .then(res => {
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.isShow = false;
            this.getGroupsList();

            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      } else {
        updateGroup(formData)
          .then(res => {
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.isShow = false;
            this.getGroupsList();

            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      }
    },
    submitPerData(perFormData) {
      this.perDialogLoading = true;

      saveGur(perFormData)
        .then(res => {
          this.$message({
            message: res.msg,
            type: "success",
            showClose: true
          });
          this.isShowPer = false;
          this.perDialogLoading = false;
        })
        .catch(err => {
          console.log(err);
          this.perDialogLoading = false;
        });
    },
    async getGroupsList() {
      this.loading = true;

      await getGroupListParams(this.queryInfo)
        .then(res => {
          this.groupsList = res.data;
          this.total = res.total;
          this.loading = false;
        })
        .catch(err => {
          console.log(err);
          this.loading = false;
        });
    },
    async getPerListAll() {
      await queryAll()
        .then(res => {
          this.perTreeList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {},
  mounted() {
    this.getGroupsList();
    this.getPerListAll();
  }
};
</script>
<style></style>
