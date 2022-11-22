import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import SystemAdmin from "../views/SystemAdmin";
import ApartmentAdd from "../views/ApartmentAdd";
import ApartmentManage from "../views/ApartmentManage";
import ApartmentUpdate from "../views/ApartmentUpdate";
import JobAdd from "../views/JobAdd";
import JobManage from "../views/JobManage";
import JobUpdate from "../views/JobUpdate";
import EmployeeAdd from "../views/EmployeeAdd";
import EmployeeManage from "../views/EmployeeManage";
import EmployeeUpdate from "../views/EmployeeUpdate";
import Report from "../views/Report";

Vue.use(VueRouter)

const routes = [
  {
    path: '/systemAdmin',
    name: '初始界面',
    component: SystemAdmin,
    children: [
      {
        path: '/apartmentAdd',
        name:'添加部门',
        component: ApartmentAdd
      },
      {
        path: '/apartmentManage',
        name:'管理部门',
        component: ApartmentManage
      },
      {
        path: '/apartmentUpdate',
        name:'修改部门',
        component: ApartmentUpdate
      },
      {
        path: '/jobAdd',
        name:'添加岗位',
        component: JobAdd
      },
      {
        path: '/jobManage',
        name:'管理岗位',
        component: JobManage
      },
      {
        path: '/jobUpdate',
        name:'修改岗位',
        component: JobUpdate
      },
      {
        path: '/employeeAdd',
        name:'添加员工',
        component: EmployeeAdd
      },
      {
        path: '/employeeManage',
        name:'管理员工',
        component: EmployeeManage
      },
      {
        path: '/employeeUpdate',
        name:'修改员工',
        component: EmployeeUpdate
      },
      {
        path: '/report',
        name:'获取报表',
        component: Report
      },
    ]
  },
  {
    path: '/',
    name:'登录',
    component: Login
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
