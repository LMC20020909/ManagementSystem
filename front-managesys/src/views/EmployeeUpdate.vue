<template>
  <div style="margin-top: 60px; width: 500px;height: 500px;border: 0 solid red;" >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="ID">
        <el-input v-model="ruleForm.id" readonly></el-input>
      </el-form-item>

      <el-form-item label="编号" prop="number">
        <el-input v-model="ruleForm.number"></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="gerder">
        <div style="width: 170px;height: 30px;">
          <template>
            <el-radio v-model="ruleForm.gerder" label="男">男</el-radio>
            <el-radio v-model="ruleForm.gerder" label="女">女</el-radio>
          </template>
        </div>
      </el-form-item>

      <el-form-item label="部门">
        <el-select v-model="apartmentId" placeholder="请选择部门" @change="getJoblist('apartmentId')">
          <el-option v-for="item in apartmentlist" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="岗位" prop="jobId">
        <el-select v-model="ruleForm.jobId" placeholder="请选择岗位">
          <el-option v-for="item in joblist" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即修改</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  data() {
    return {
      category:null,
      ruleForm: {
        id:'',
        number: '',
        name: '',
        gerder: '男',
        jobId: null,
      },
      apartmentId:'',
      apartmentlist: '',
      joblist:'',
      rules: {
        number: [
          { required: true, message: '请输入编号', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        jobId: [
          { required: true, message: '请选择岗位', trigger: 'change' }
        ],
      }
    };
  },
  methods: {
    getJoblist(formName) {
      const _this = this
      _this.ruleForm.jobId = ''
      if(_this.apartmentId != '') {
        axios.get('http://localhost:8181/job/getavailableJpbByApartmentId/' + _this.apartmentId).then(function (resp) {
          _this.joblist = resp.data.data
        })
      }
    },
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.put('http://localhost:8181/employee/update',_this.ruleForm).then(function (resp) {
            // alert("fuck")
            if(resp.data.code == 0){
                _this.$alert('员工\"' + _this.ruleForm.name+ '\"修改成功', '', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/employeeManage')
                }
              });
            }
          })
        }
      });
    },
    resetForm(formName) {
      const _this = this
      // alert(_this.$route.query.id)
      axios.get('http://localhost:8181/apartment/list').then(function (resp) {
        _this.apartmentlist = resp.data.data
      })
      axios.get('http://localhost:8181/employee/findById/' + _this.ruleForm.id).then(resp => {
        console.log(resp.data.data)
        axios.get('http://localhost:8181/job/getJpbByApartmentId/' + resp.data.data.apartmentId).then(resp1 => {
          _this.joblist = resp1.data.data
        })
        _this.ruleForm = resp.data.data
        _this.apartmentId = resp.data.data.apartmentId
      })
      location.reload()
    }
  },
  created() {
    const _this = this
    // alert(_this.$route.query.id)
    axios.get('http://localhost:8181/apartment/list').then(function (resp) {
      _this.apartmentlist = resp.data.data
    })
    axios.get('http://localhost:8181/employee/findById/' + _this.$route.query.id).then(resp => {
      console.log(resp.data.data)
      axios.get('http://localhost:8181/job/getJpbByApartmentId/' + resp.data.data.apartmentId).then(resp1 => {
        _this.joblist = resp1.data.data
      })
      _this.ruleForm = resp.data.data
      _this.apartmentId = resp.data.data.apartmentId
    })
  }
}
</script>
