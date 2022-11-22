<template>
  <div style="margin-top: 60px; width: 500px;height: 500px;border: 0 solid red;" >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="ID">
        <el-input v-model="ruleForm.id" readonly></el-input>
      </el-form-item>

      <el-form-item label="部门" prop="apartmentId">
        <el-select v-model="ruleForm.apartmentId" placeholder="请选择部门">
          <el-option v-for="item in apartmentlist" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="岗位" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>

      <el-form-item label="最多可招聘人数">
        <el-input v-model="ruleForm.maxnum" readonly></el-input>
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
      apartmentlist:'',
      ruleForm: {
        id:'',
        apartmentId: '',
        name: '',
        maxnum: '',
      },
      rules: {
        apartmentId: [
          { required: true, message: '请选择部门', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.put('http://localhost:8181/job/update',_this.ruleForm).then(function (resp) {
            if(resp.data.code == 0){
                _this.$alert('岗位\"' + _this.ruleForm.name+'\"修改完成', '', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/jobManage')
                }
              });
            }
          })
        }
      });
    },
    resetForm(formName) {
      axios.get('http://localhost:8181/job/findById/' + this.ruleForm.id).then(function (resp) {
        console.log(resp.data.data)
        this.ruleForm = resp.data.data
      })
      location.reload()
    },
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/apartment/list/').then(function (resp) {
      _this.apartmentlist = resp.data.data
    })
    axios.get('http://localhost:8181/job/findById/'+_this.$route.query.id).then(function (resp) {
      _this.ruleForm = resp.data.data
    })
  }
}
</script>
