<template>
  <div style="margin-top: 60px; width: 500px;height: 500px;border: 0 solid red;" >
    <el-form label-width="150px" class="demo-ruleForm">
      <el-form-item label="获取新聘员工报表">
        <el-button @click="download1" class="el-button-color add-button-box" size="medium" type="primary">
          <i class="el-icon-download el-icon--right"></i>下载新聘员工报表
        </el-button>
      </el-form-item>

      <el-form-item label="获取离职员工报表">
        <el-button @click="download2" class="el-button-color add-button-box" size="medium" type="primary">
          <i class="el-icon-download el-icon--right"></i>下载离职员工报表
        </el-button>
      </el-form-item>

      <el-form-item label="获取员工调动报表">
        <el-button @click="download3" class="el-button-color add-button-box" size="medium" type="primary">
          <i class="el-icon-download el-icon--right"></i>下载员工调动报表
        </el-button>
      </el-form-item>

      <el-form-item label="获取员工人事月报">
        <el-button @click="download4" class="el-button-color add-button-box" size="medium" type="primary">
          <i class="el-icon-download el-icon--right"></i>下载员工人事月报
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  methods: {
    download1(){
      var url = 'http://localhost:8181/movein/report'
      // alert("1")
      window.open(url)
      // alert("2")
    },
    download2(){
      var url = 'http://localhost:8181/moveout/report'
      window.open(url)
    },
    download3(){
      var url = 'http://localhost:8181/movejob/report'
      window.open(url)
    },
    download4(){
      window.open('http://localhost:8181/movejob/report')
      window.open('http://localhost:8181/movein/report')
      window.open('http://localhost:8181/moveout/report')
    }
  },
}
</script>