<template>
    <el-container class="home_container">
      <el-header class="home_header">
        <img src="../assets/logo.png" class="home_img"/>
        <div class="home_title">HUSTZL 华中科技大学人事管理系统</div>
        <div class="home_userinfoContainer">
          <el-dropdown>
            <span class="el-dropdown-link home_userinfo">
              {{admin.name}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
    <el-container>
    <img src="../assets/sketch1.png" class="home_back">
    <el-aside class="home_aside" width="200px">

      <el-menu class="home_menu" router>
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-s-home"></i>部门管理</template>
          <el-menu-item index="/apartmentAdd" :class="$route.path=='/apartmentAdd'?'is-active':''">
            <i class="el-icon-folder-add"></i>添加部门
          </el-menu-item>
          <el-menu-item index="/apartmentManage" :class="$route.path=='/apartmentManage'?'is-active':''">
            <i class="el-icon-document-copy"></i>管理部门
          </el-menu-item>
        </el-submenu>

        <el-submenu index="2">
          <template slot="title"><i class="el-icon-s-platform"></i>岗位管理</template>
          <el-menu-item index="/jobAdd" :class="$route.path=='/jobAdd'?'is-active':''">
            <i class="el-icon-folder-add"></i>添加岗位
          </el-menu-item>
          <el-menu-item index="/jobManage" :class="$route.path=='/jobManage'?'is-active':''">
            <i class="el-icon-document-copy"></i>管理岗位
          </el-menu-item>
        </el-submenu>

        <el-submenu index="3">
          <template slot="title"><i class="el-icon-user-solid"></i>人事管理</template>
          <el-menu-item index="/employeeAdd":class="$route.path=='/employeeAdd'?'is-active':''">
            <i class="el-icon-folder-add"></i>员工入职
          </el-menu-item>
          <el-menu-item index="/employeeManage" :class="$route.path=='/employeeManage'?'is-active':''">
            <i class="el-icon-document-copy"></i>管理员工
          </el-menu-item>
        </el-submenu>

        <el-menu-item index="/report">
          <i class="el-icon-s-unfold"></i>
          <span slot="title">报告生成</span>
        </el-menu-item>
      </el-menu>

    </el-aside>

    <el-container>
      <el-main>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
        </el-breadcrumb>
        <router-view></router-view>
      </el-main>
      <el-footer class="home_footer">2022 © HUSTZL</el-footer>
    </el-container>

  </el-container>

  </el-container>
</template>
<script>
export default {
  methods: {
    logout(){
      let _this = this;
      this.$confirm('注销登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        localStorage.removeItem('systemAdmin')
        _this.$router.replace({path: '/'})
      })
    }
  },
  data(){
    return {
      admin:''
    }
  },
  created() {
    let admin = JSON.parse(window.localStorage.getItem('systemAdmin'))
    this.admin = admin
  }
}
</script>
<style>
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;

}

.home_header {
  background-color: #FFFFFF;
  border-bottom: 1px solid rgb(220, 223, 230);
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.home_title {
  color: #409EFF;
  font-size: 22px;
  display: inline;
}

.home_userinfo {
  color: #000000;
  cursor: pointer;
  z-index: 1;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}

.home_aside {
  background-color: #FFFFFF;
}

.home_footer {
  background-color: #FFFFFF;
  color: #000000;
  font-size: 10px;
  line-height: 60px;
  text-align: center;
}

.home_menu{
  height: 100%;
}

.home_img {
  width: 50px;
  height:50px;
}

.home_back {
  position: fixed;
  width: 1330px;
  left: 200px;
  bottom: -120px;
  z-index: -1;
}

</style>
