<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -40px" :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="部门：">
        <el-select v-model="ruleForm.apartmentId" style="width: 160px;float: left" placeholder="请选择岗位" @change="getJoblist('apartmentId')">
          <el-option v-for="item in apartmentlist" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="岗位：">
          <el-select v-model="ruleForm.jobId" placeholder="请选择岗位" style="width: 160px;">
            <el-option v-for="item in joblist" :label="item.name" :value="item.id"></el-option>
          </el-select>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm('ruleForm')">搜索</el-button>
        </el-form-item>
      </div>
    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="id"
          label="ID"
          width="130">
      </el-table-column>
      <el-table-column
          prop="name"
          label="员工"
          width="130">
      </el-table-column>
      <el-table-column
          prop="number"
          label="编号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="gerder"
          label="性别"
          width="130">
      </el-table-column>
      <el-table-column
          prop="apartmentName"
          label="部门"
          width="130">
      </el-table-column>
      <el-table-column
          prop="jobName"
          label="岗位"
          width="130">
      </el-table-column>
      <el-table-column
          prop="createDate"
          label="注册时间"
          width="130">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="edit(scope.row)">编辑</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="del(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="position:fixed;top:600px;right: 30px"
                   background
                   layout="prev, pager, next"
                   :page-size="pageSize"
                   :total="total"
                   :current-page.sync="currentPage"
                   @current-change="page">
    </el-pagination>
  </div>

</template>

<script>
export default {
  data() {
    return {
      tableData: null,
      currentPage: 1,
      pageSize: 4,
      total: 0,
      key: '',
      value: '',
      apartmentlist:null,
      joblist:null,
      ruleForm: {
        apartmentId: '',
        jobId: '',
        page: '',
        size: 4,
      },
    }
  },
  methods:{
    getJoblist(formName) {
      const _this = this
      _this.ruleForm.jobId = ''
      if(_this.ruleForm.apartmentId != '') {
        axios.get('http://localhost:8181/job/getJpbByApartmentId/' + _this.ruleForm.apartmentId).then(function (resp) {
          _this.joblist = resp.data.data
        })
      }
    },
    submitForm(formName) {
      const _this = this
      //让翻页复原
      _this.currentPage = 1
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          _this.ruleForm.page = _this.currentPage
          axios.get('http://localhost:8181/employee/search',{params:_this.ruleForm}).then(function (resp) {
            _this.tableData = resp.data.data.data
            _this.total = resp.data.data.total
          })
        }
      });

    },
    page(currentPage){
      const _this = this
      if(_this.ruleForm.apartmentId == ''){
        axios.get('http://localhost:8181/employee/list/'+currentPage+'/'+_this.pageSize).then(function (resp) {
          _this.tableData = resp.data.data.data
          _this.total = resp.data.data.total
        })
      } else {
        _this.ruleForm.page = _this.currentPage
        axios.get('http://localhost:8181/employee/search',{params:_this.ruleForm}).then(function (resp) {
          _this.tableData = resp.data.data.data
          _this.total = resp.data.data.total
        })
      }

    },
    edit(row){
      this.$router.push('/employeeUpdate?id='+row.id)
    },
    del(row) {
      const _this = this
      this.$confirm('确认删除【'+row.name+'】吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8181/employee/deleteById/'+row.id).then(function (resp) {
          if(resp.data.code==0){
            _this.$alert('【'+row.name+'】已删除', '', {
              confirmButtonText: '确定',
              callback: action => {
                location.reload()
              }
            });
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除',
        })
      });
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/apartment/list').then(function (resp) {
      _this.apartmentlist = resp.data.data
    })
    axios.get('http://localhost:8181/employee/list/1/'+_this.pageSize).then(function (resp) {
      _this.tableData = resp.data.data.data
      _this.total = resp.data.data.total
    })
  }
}
</script>