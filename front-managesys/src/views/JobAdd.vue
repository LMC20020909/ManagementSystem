<template>
  <div style="margin-top: 60px; width: 500px;height: 500px;border: 0 solid red;" >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="部门" prop="apartmentId">
        <el-select v-model="ruleForm.apartmentId" placeholder="请选择部门">
          <el-option v-for="item in apartmentlist" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="岗位" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>

      <el-form-item label="最多可招聘人数" prop="maxnum">
        <el-input v-model="ruleForm.maxnum"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
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
        apartmentId: '',
        name: '',
        maxnum: '',
      },
      apartmentlist: '',
      rules: {
        apartmentId: [
          { required: true, message: '请选择部门', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请输入岗位名称', trigger: 'blur' }
        ],
        maxnum: [
          { required: true, message: '请输入最多可招聘人数', trigger: 'blur' }
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
          axios.post('http://localhost:8181/job/save',_this.ruleForm).then(function (resp) {
            if(resp.data.code == 0){
              _this.$alert('该部门' + _this.ruleForm.name + '岗位添加成功', '', {
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
      this.$refs[formName].resetFields();
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/apartment/list').then(function (resp) {
      _this.apartmentlist = resp.data.data
    })
  }
}
</script>
