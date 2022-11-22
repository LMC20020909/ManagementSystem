<template>
  <div style="margin-top: 60px; width: 500px;height: 500px;border: 0 solid red;" >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="部门ID">
        <el-input v-model="ruleForm.id" readonly></el-input>
      </el-form-item>
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="部门联系电话" prop="telephone">
        <el-input v-model="ruleForm.telephone"></el-input>
      </el-form-item>
      <el-form-item label="部门负责人姓名" prop="master">
        <el-input v-model="ruleForm.master"></el-input>
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
        name: '',
        master:'',
        telephone: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入部门名称', trigger: 'blur' }
        ],
        master: [
          { required: true, message: '请输入负责人姓名', trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入部门电话', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log(_this.ruleForm)
          axios.put('http://localhost:8181/apartment/update',_this.ruleForm).then(function (resp) {
            if(resp.data.code == 0){
              _this.$alert("部门\"" +_this.ruleForm.name+'\"修改成功', '', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/apartmentManage')
                }
              });
            }
          })
        }
      });
    },
    resetForm(formName) {
      axios.get('http://localhost:8181/apartment/findById/' + this.ruleForm.id).then(function (resp) {
        console.log(resp.data.data)
        this.ruleForm = resp.data.data
      })
      location.reload()
    },
  },
  created() {
    const _this = this
    //alert(_this.$route.query.id)
    axios.get('http://localhost:8181/apartment/findById/' + _this.$route.query.id).then(function (resp) {
      // console.log(resp.data.data)
      _this.ruleForm = resp.data.data
    })
  }
}
</script>
