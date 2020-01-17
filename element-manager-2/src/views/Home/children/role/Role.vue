<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入角色描述"
            v-model="queryInfo.roleDesc"
            class="input-with-select"
            clearable
            @clear="getRoleList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getRoleList"
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
        :data="roleList"
        style="width: 100%"
        boder
        stripe
      >
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column
          prop="roleName"
          label="角色名"
          width="250"
        ></el-table-column>
        <el-table-column
          prop="roleDesc"
          label="角色描述"
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
      :roleId="roleId"
      :formData="addFormData"
    />

    <EditRolePer
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
  getRoleListParams,
  getRoleById,
  addRole,
  updateRole,
  delRole
} from "@/api/role.js";
import { queryByRoleId, queryAll } from "@/api/permission.js";
import { saveGur } from "@/api/grp.js";
import AddDialog from "./components/AddDialog";
import EditRolePer from "./components/EditRolePer";

export default {
  name: "",
  components: {
    AddDialog,
    EditRolePer
  },
  data() {
    return {
      loading: false,
      editDialogLoading: false,
      perDialogLoading: false,

      total: 0,
      pageSizes: [5, 10, 100, 500],
      roleList: [],
      queryInfo: {
        roleDesc: "",
        page: 1,
        length: 10
      },
      // 编辑角色弹窗
      roleId: 0,
      isShow: false,
      isAddDialog: true,
      addFormData: {},
      // 编辑权限弹窗
      isShowPer: false,
      destroyTree: true,
      perTreeList: [],
      perFormData: {
        roleId: "",
        roleName: "",
        perId: []
      }
    };
  },
  methods: {
    showAddDialog() {
      this.isAddDialog = true;
      this.isShow = true;
    },
    async showEditDialog(id) {
      this.isAddDialog = false;
      this.isShow = true;

      await getRoleById(id)
        .then(res => {
          this.addFormData = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    async showPerDialog(roleInfo) {
      this.isShowPer = true;
      this.destroyTree = false;
      this.perFormData.roleId = roleInfo.id;
      this.perFormData.roleDesc = roleInfo.roleDesc;

      await queryByRoleId(roleInfo.id)
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
    showDelDialog(id) {
      this.$confirm("此操作将删除该角色, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delRole(id).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });

            this.getRoleList();
          });
        })
        .catch(() => {});
    },
    closePerDialog() {
      this.isShowPer = false;
    },
    closeEditDialog() {
      this.isShow = false;
    },
    handleSizeChange(length) {
      this.queryInfo.length = length;
      this.getRoleList();
    },
    handleCurrentChange(current) {
      this.queryInfo.page = current;
      this.getRoleList();
    },
    submitData(type, formData) {
      this.editDialogLoading = true;
      if (type) {
        addRole(formData)
          .then(res => {
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.isShow = false;
            this.getRoleList();

            this.editDialogLoading = false;
          })
          .catch(err => {
            console.log(err);
            this.editDialogLoading = false;
          });
      } else {
        updateRole(formData)
          .then(res => {
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.isShow = false;
            this.getRoleList();

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
    async getRoleList() {
      this.loading = true;
      await getRoleListParams(this.queryInfo)
        .then(res => {
          this.roleList = res.data;
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
    this.getRoleList();
    this.getPerListAll();
  }
};
</script>
<style></style>
