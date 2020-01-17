<template>
  <div>
    <!-- 导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>职位列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card class="box-card">
      <!-- gutter设置每个el-col之间的间隙 -->
      <el-row :gutter="10">
        <el-col :span="12">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.paramName"
            class="input-with-select"
            clearable
            @clear="getParameterList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getParameterList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="showAddDialog()">新增</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table :data="parameterist" style="width: 100%" boder stripe>
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="paramName" label="职位名" width="180"></el-table-column>
        <el-table-column prop="paramType" label="职位类型" width="180"></el-table-column>
        <el-table-column prop="paramValue" label="职位优先级" width="180"></el-table-column>
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

    <!-- 新增、修改用户对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClose"
      @before-close="addDialogBeforeClose"
    >
      <!-- 内容主体区 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="90px">
        <el-form-item label="id" prop="id" class="el-form-item-id">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="职位名" prop="paramName">
          <el-input v-model="addForm.paramName"></el-input>
        </el-form-item>
        <el-form-item label="职位类型" prop="paramType">
          <el-input v-model="addForm.paramType"></el-input>
        </el-form-item>
        <el-form-item label="职位优先级" prop="paramValue">
          <el-input v-model="addForm.paramValue"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部按钮区 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addParameter">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 提示对话框 -->
    <el-dialog title="删除职位" :visible.sync="dialogVisible" width="30%">
      <span>确定删除该职位吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="delParameter">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { request } from '../../../network/request'

export default {
  name: '',
  data () {
    // 自定义校验规则
    var checkVal = (rule, value, callback) => {

      const regAge = /\d+/
      if (!value) {
        return callback(new Error('参数值不能为空'))
      } else if (!regAge.test(value)) {
        return callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      msg: 'this is user page',
      isAddDialog: true,
      dialogVisible: false,
      delId: '',
      parameterist: [],
      total: 0,
      pageSizes: [5, 10, 100, 500],
      // 添加用户对话框的显示隐藏
      addDialogVisible: false,
      dialogTitle: '',
      // 用户列表参数
      queryInfo: {
        paramName: '',
        page: 1,
        length: 5
      },
      // 添加用户的表单数据对象
      addForm: {
        paramName: '',
        paramValue: '',
        type: [],
        paramType: ''
      },
      // 表单验证规则
      addFormRules: {
        paramName: [
          { required: true, message: '请输入参数名', trigger: 'blur' },
        ],
        paramValue: [
          { required: true, message: '请输入参数值', trigger: 'blur' },
          { validator: checkVal, trigger: 'blur' }
        ],
        paramType: [
          { required: true, message: '请输入参数类型', trigger: 'blur' },
          // { type: 'number', message: '年龄必须为数字值' }
        ]
      }
    }
  },
  methods: {
    handleSizeChange (length) {
      this.queryInfo.length = length
      this.getParameterList()
    },
    handleCurrentChange (current) {
      this.queryInfo.page = current
      this.getParameterList()
    },
    addParameter () {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return
        let reqPath = ''
        if (this.isAddDialog) {
          reqPath = '/web/parameter/add'
        } else {
          reqPath = '/web/parameter/update'
        }
        request({
          url: reqPath,
          data: this.addForm,
          method: 'post'
        }).then(res => {
          // this.$message.success(res.msg)
          this.$message({
            message: res.msg,
            type: 'success',
            showClose: true
          })
          this.addDialogVisible = false
          this.getParameterList()
        }).catch(err => {
          console.log(err);
        })
      })
    },
    delParameter () {
      request({
        url: '/web/parameter/del',
        method: 'get',
        params: {
          id: this.delId
        }
      }).then(res => {
        this.$message({
          message: '删除参数成功',
          type: 'success',
          showClose: true
        })
        this.dialogVisible = false
        this.getParameterList()
      }).catch(err => {
        console.log(err);
      })
    },
    addDialogClose () {
      this.$refs.addFormRef.resetFields()
    },
    addDialogBeforeClose () {
      this.$refs.addFormRef.resetFields()
    },
    userUpdate (userInfo) {
      this.getParameterList()
    },
    showAddDialog () {
      this.isAddDialog = true
      this.addDialogVisible = true
      this.dialogTitle = '新增职位'
    },
    showDelDialog (id) {
      this.dialogVisible = true
      this.delId = id;
    },
    showEditDialog (id) {
      this.addDialogVisible = true
      this.isAddDialog = false
      this.dialogTitle = '修改职位'
      request({
        url: '/web/parameter/queryById',
        method: 'get',
        params: {
          id: id
        }
      }).then(res => {
        this.addForm = res.data
      }).catch(err => {
        console.log(err);
      })
    },
    selectParamterChange (val) {
      this.addForm.type = val
    },
    selectParamter () {
      request({
        url: '/web/parameter/queryParamType',
        method: 'get',
      }).then(data => {
        this.addForm.type = data.data
      }).catch(err => {
        console.log(err);
      })
    },
    getParameterList () {
      request({
        url: '/web/parameter',
        method: 'get',
        params: this.queryInfo
      }).then(data => {
        if (data.status !== 200) {
          this.$message({
            message: data.msg,
            type: 'error',
            showClose: true
          })
          return
        }
        this.parameterist = data.data
        this.total = data.total
      }).catch(err => {
        console.log(err);
      })
    }
  },
  created () {
    this.getParameterList()
  }
}
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
