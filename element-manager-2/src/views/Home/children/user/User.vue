<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.username"
            class="input-with-select"
            clearable
            @clear="getUserList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="showAddDialog()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table :data="userList" style="width: 100%" boder stripe>
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="username" label="姓名" width="180"></el-table-column>
        <el-table-column prop="mobile" label="手机号" width="180"></el-table-column>
        <el-table-column prop="sex" label="性别">
          <template slot-scope="scope">
            {{
            scope.row.sex === "1" ? "男" : "女"
            }}
          </template>
        </el-table-column>
        <el-table-column label="冻结">
          <!-- 使用作用域插槽获取当前行的数据 -->
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.state"
              :active-value="'1'"
              :inactive-value="'2'"
              @change="switchChange(scope.row)"
            ></el-switch>
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

            <el-tooltip
              class="item"
              effect="dark"
              content="分配角色"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-key"
                size="mini"
                @click="showRoleDialog(scope.row.id)"
              ></el-button>
            </el-tooltip>
            <!-- <el-tooltip
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
            ></el-button>-->
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

    <!-- 新增、修改用户对话框 -->
    <el-dialog
      title="新增、修改用户"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClose"
      @before-close="addDialogBeforeClose"
    >
      <!-- 内容主体区 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="id" prop="id" class="el-form-item-id">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username" :disabled="!isAddDialog"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :class="{ showpwdinput: !isAddDialog }">
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
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示对话框 -->
    <el-dialog title="注销用户" :visible.sync="dialogVisible" width="30%">
      <span>确定删除用户吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="userDel">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加角色对话框 -->
    <el-dialog :title="roleTitle" :visible.sync="roleDialogVisible" width="50%">
      <el-form :model="roleGroupForm" label-width="90px">
        <!-- {{roleGroupForm.role.id}} -->
        <el-form-item label="选择角色" prop="rId">
          <el-select filterable v-model="roleGroupForm.roleId" placeholder="请选择角色" clearable>
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.roleDesc"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择部门" prop="rId">
          <el-select
            filterable
            multiple
            v-model="roleGroupForm.groupId"
            placeholder="请选择部门"
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
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="userRoleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getUsers, getInfo, update, add } from "@/api/user.js";
import { getRoleList, getRoleById, getRoleByUserId } from "@/api/role.js";
import { getGroupList, getGroupById, getGroupByUserId } from "@/api/groups.js";
import { saveGur } from "@/api/gur.js";

export default {
  name: "",
  components: {},
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
      msg: "this is user page",
      isAddDialog: true,
      userList: [],
      total: 0,
      pageSizes: [5, 10, 100, 500],
      delId: "",
      dialogVisible: false,
      // 添加用户对话框的显示隐藏
      addDialogVisible: false,
      // 添加修改用户角色对话框显隐
      roleDialogVisible: false,
      // 角色对话框标题
      roleTitle: "",
      // 用户列表参数
      queryInfo: {
        username: "",
        page: 1,
        length: 10
      },
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
      },
      roleList: [],
      groupsList: [],
      roleGroupForm: {
        userId: "",
        roleId: "",
        groupId: []
      }
    };
  },
  methods: {
    addDialogClose() {
      this.$refs.addFormRef.resetFields();
    },
    handleSizeChange(length) {
      this.queryInfo.length = length;
      this.getUserList();
    },
    handleCurrentChange(current) {
      this.queryInfo.page = current;
      this.getUserList();
    },
    switchChange(userInfo) {
      update(userInfo)
        .then(res => {
          if (res.status === 200) {
            return this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
          } else {
            userInfo.state = userInfo.state === 1 ? 0 : 1;
            return this.$message({
              message: "更新用户状态失败",
              type: "error",
              showClose: true
            });
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    dialogSubmit() {
      if (this.isAddDialog) {
        this.addUser();
      } else {
        this.updateUser();
      }
    },
    addUser() {
      let reqPath = "";
      if (this.isAddDialog) {
        reqPath = "/web/user/add";
      } else {
        (reqPath = "/web/user/update"),
          // 修改的时候因为不显示密码，所以去掉密码校验规则
          (this.addFormRules.password = []);
      }
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return;
        add(this.addForm)
          .then(res => {
            // this.$message.success(res.msg)
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.addDialogVisible = false;
            this.getUserList();
          })
          .catch(err => {
            console.log(err);
          });
      });
    },
    updateUser() {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return;
        update(this.addForm)
          .then(res => {
            // this.$message.success(res.msg)
            this.$message({
              message: res.msg,
              type: "success",
              showClose: true
            });
            this.addDialogVisible = false;
            this.getUserList();
          })
          .catch(err => {
            console.log(err);
          });
      });
    },
    userDel() {
      request({
        url: "/web/user/del",
        method: "get",
        params: {
          id: this.delId
        }
      })
        .then(res => {
          this.$message({
            message: res.msg,
            type: "success",
            showClose: true
          });
          this.dialogVisible = false;
          this.getUserList();
        })
        .catch(err => {
          console.log(err);
        });
    },
    userUpdate(userInfo) {
      console.log(userInfo);
      this.getUserList();
    },
    showAddDialog() {
      // console.log('----------')

      this.isAddDialog = true;
      this.addDialogVisible = true;

      this.addFormRules.password = [
        { required: true, message: "请输入密码", trigger: "blur" },
        { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
      ];
    },
    showEditDialog(id) {
      this.addDialogVisible = true;
      this.isAddDialog = false;

      this.addFormRules.password = [];

      getInfo(id)
        .then(res => {
          console.log(res);

          this.addForm = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    showDelDialog(id) {
      this.delId = id;
      this.dialogVisible = true;
    },
    showRoleDialog(userId) {
      this.roleTitle = "编辑用户角色";
      this.roleDialogVisible = true;
      this.roleGroupForm.userId = userId;

      this.getUserRole(userId);
      this.getUserGroup(userId);
    },
    userRoleSubmit() {
      saveGur(this.roleGroupForm).then(res => {
        if (res.status === 200) {
          this.roleDialogVisible = false;
          this.$message({
            message: res.msg,
            type: "success",
            showClose: true
          });
        }
      });
    },
    addDialogBeforeClose() {
      this.$refs.addFormRef.resetFields();
    },
    getUserList() {
      getUsers(this.queryInfo)
        .then(data => {
          if (data.status !== 200) {
            this.$message({
              message: data.msg,
              type: "error",
              showClose: true
            });
            return;
          }
          this.userList = data.data;
          this.total = data.total;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getRoleList() {
      getRoleList().then(res => {
        this.roleList = res.data;
      });
    },
    getGroupList() {
      getGroupList().then(res => {
        this.groupsList = res.data;
      });
    },
    async getUserRole(id) {
      await getRoleByUserId(id)
        .then(res => {
          this.roleGroupForm.roleId = res.data.id;
        })
        .catch(err => {
          console.log(err);
        });
    },
    async getUserGroup(id) {
      await getGroupByUserId(id)
        .then(res => {
          let result = res.data;

          this.roleGroupForm.groupId = result.map(n => {
            return n.id;
          });
          console.log(this.roleGroupForm.groupId);
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() { },
  mounted() {
    this.getUserList();
    this.getRoleList();
    this.getGroupList();
  }
};
</script>
<style>
.el-table {
  margin-top: 15px;
}
.el-pagination {
  margin-top: 15px;
}
.el-form-item-id {
  display: none;
}
.showpwdinput {
  display: none;
}
</style>
