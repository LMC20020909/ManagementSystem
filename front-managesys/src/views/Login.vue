<template>
  <div class="login-container">
    <div class="login-img">
      <img src="../assets/01.png"/>
    </div>
    <el-form :model="ruleForm" :rules="rules"
             status-icon
             ref="ruleForm"
             label-position="left"
             label-width="0px"
             class="demo-ruleForm login-page">
      <h3 class="title">系统登录</h3>
      <el-form-item prop="username">
        <el-input type="text"
                  v-model="ruleForm.username"
                  placeholder="用户名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password"
                  v-model="ruleForm.password"
                  placeholder="密码"
        ></el-input>
      </el-form-item>

<!--      <el-form-item prop="checkcode">-->
<!--        <el-input placeholder="输入验证码" v-model="ruleForm.checkcode" style="width:65%;"></el-input>-->

<!--        <el-image-->
<!--            style="width: 25%; height: 50px; margin-left: 30px; margin-top: 20px"-->
<!--            :src="url"-->
<!--            :fit="fit"></el-image>-->

<!--&lt;!&ndash;        <el-button size="small" type="primary" style="margin-left: 28px" v-if="show" @click="sendCheckCode">发送验证码</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-button size="small" type="primary" style="margin-left: 28px" disabled v-if="!show" @click="sendCheckCode" >{{count}}秒后重发</el-button>&ndash;&gt;-->
<!--      </el-form-item>-->

      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;"
                   v-popover:popover
                   @click="handleSubmit" :loading="logining">登录</el-button>
      </el-form-item>



    </el-form>

<!--    验证码弹窗-->
    <el-popover
        popper-class="slidingPictures"
        ref="popover"
        trigger="manual"
        v-model="visible"
    >
      <div class="sliding-pictures">
        <div class="vimg">
          <canvas id="sliderBlock"></canvas>
          <canvas id="codeImg"></canvas>
        </div>
        <div class="slider">
          <div class="track" :class="{ pintuTrue: puzzle }">
            {{ tips }}
          </div>
          <div class="button el-icon-s-grid" @mousedown.prevent="drag"></div>
        </div>
        <div class="operation">
 <span
     title="关闭验证码"
     @click="visible = false, logining = false"
     class="el-icon-circle-close"
 ></span>
          <span
              title="刷新验证码"
              @click="shuaxin"
              class="el-icon-refresh-left"
          ></span>
        </div>
      </div>
    </el-popover>


  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      tips: "拖动左边滑块完成上方拼图",
      logining: false,
      show: true,
      timer: null,
      count: "",
      ruleForm: {
        username: '',
        password: '',
      },
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
      },
      level: 2,

      imgsrc: require("../assets/02.jpg"),

      visible: false,
      //滑块x轴数据
      slider: {
        mx: 0,
        bx: 0
      },
      //拼图是否正确
      puzzle: false
    };
  },
  watch: {
    visible(e) {
      if (e === true) {
        this.canvasInit();
        this.puzzle = false;
      }
    }
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {},
  //   }
  // },
  methods: {
    handleSubmit(){
      if (this.ruleForm.username && this.ruleForm.password) this.visible = !this.visible;
      this.$refs.ruleForm.validate((valid) => {
        if(valid) {
          this.logining = true
          let _this = this
          if (this.puzzle) {
            axios.get('http://localhost:8181/systemAdmin/login', {params: _this.ruleForm}).then(function (resp) {
              _this.logining = false
              // if (_this.ruleForm.checkcode !== "1234") {
              //   _this.$alert('验证码错误', '提示', {
              //     confirmButtonText: '确定'
              //   })
              // }
              // else {
              if (resp.data.code === -1) {
                _this.$alert('用户名不存在', '提示', {
                  confirmButtonText: '确定'
                })
                _this.puzzle = false;
                _this.tips = "拖动左边滑块完成上方拼图";
              }
              if (resp.data.code === -2) {
                _this.$alert('密码错误', '提示', {
                  confirmButtonText: '确定'
                })
                _this.puzzle = false;
                _this.tips = "拖动左边滑块完成上方拼图";
              }
              if (resp.data.code === 0) {
                //跳转到SystemAdmin
                //展示当前登录用户信息
                localStorage.setItem('systemAdmin', JSON.stringify(resp.data.data));
                _this.$router.replace({path: '/systemAdmin'})
              }
              // }
            })

          }
        }
      })
    },

    // sendCheckCode(){
    //   let TIME_COUNT = 60;
    //   if (!this.timer) {
    //     this.count = TIME_COUNT;
    //     this.show = false;
    //     this.timer = setInterval(() => {
    //       if (this.count > 0 && this.count <= TIME_COUNT) {
    //         this.count--;
    //       } else {
    //         this.show = true;
    //         clearInterval(this.timer);
    //         this.timer = null;
    //       }
    //     }, 1000);
    //
    //   }
    // },

    //拼图验证码初始化
    canvasInit() {
      //生成指定区间的随机数
      const random = (min, max) => {
        return Math.floor(Math.random() * (max - min + 1) + min);
      };
      //x: 254, y: 109
      let mx = random(150, 220),
          bx = 10,
          y = random(10, 99);
      console.log("bx=", bx);
      this.slider = { mx, bx };
      this.draw(mx, bx, y);
    },
    resetPos() {
      let slider = document.querySelector("#sliderBlock"); //滑块dom
      slider.style.left = 10 + "px";
    },
    getPic(dirr) {
      this.imgsrc = require("../assets/0"+dirr+".jpg")
    },
    shuaxin() {
      this.level++;
      if(this.level > 6) this.level -= 5;
      this.getPic(this.level);
      this.canvasInit();
    },
    //鼠标按下
    drag(e) {
      console.log("鼠标按下", e);
      let dom = e.target; //dom元素
      let slider = document.querySelector("#sliderBlock"); //滑块dom
      const downCoordinate = { x: e.x, y: e.y };
      //正确的滑块数据
      let checkx = Number(this.slider.mx) - Number(this.slider.bx);
      //x轴数据
      let x = 0;
      const move = moveEV => {
        x = moveEV.x - downCoordinate.x;
        //y = moveEV.y - downCoordinate.y;
        if (x >= 251 || x <= 0) return false;
        dom.style.left = x + "px";
        //dom.style.top = y + "px";
        slider.style.left = x + "px";
        console.log(slider.style.left)
      };
      const up = () => {
        document.removeEventListener("mousemove", move);
        document.removeEventListener("mouseup", up);
        dom.style.left = "";
        console.log(x, checkx);
        let max = checkx + 10;
        let min = checkx - 10;
        //允许正负误差1
        if ((max >= x && x >= min) || x === checkx) {
          console.log("滑动解锁成功");
          this.puzzle = true;
          this.tips = "验证成功";
          setTimeout(() => {
            this.handleSubmit();
            this.resetPos();
          }, 500);

        } else {
          console.log("拼图位置不正确");
          this.tips = "验证失败，请重试";
          this.puzzle = false;
          this.resetPos();
          this.canvasInit();
        }
      };
      document.addEventListener("mousemove", move);
      document.addEventListener("mouseup", up);
    },
    draw(mx, bx, y) {
      let mainDom = document.querySelector("#codeImg");
      let bg = mainDom.getContext("2d");
      let width = mainDom.width;
      let height = mainDom.height;
      let blockDom = document.querySelector("#sliderBlock");
      let block = blockDom.getContext("2d");
      //重新赋值，让canvas进行重新绘制
      blockDom.height = height;
      mainDom.height = height;
      //let imgsrc = require("../assets/02.jpg");
      let img = document.createElement("img");
      img.style.objectFit = "scale-down";
      img.src = this.imgsrc;
      img.onload = function() {
        bg.drawImage(img, 0, 0, width, height);
        block.drawImage(img, 0, 0, width, height);
      };
      let mainxy = { x: mx, y: y, r: 9 };
      let blockxy = { x: bx, y: y, r: 9 };
      // console.log("mainxy"+mainxy.x);
      // console.log("blockxy"+blockxy.x);
      this.drawBlock(bg, mainxy, "fill");
      this.drawBlock(block, mainxy, "clip");
    },
    //绘制拼图
    drawBlock(ctx, xy, type) {
      if (type === "clip") {
        ctx.translate(10-xy.x,0);
        console.log("gan")
      }
      let x = xy.x,
          y = xy.y,
          r = xy.r,
          w = 40;
      // console.log(ctx,x);
      let PI = Math.PI;
      //绘制
      ctx.beginPath();
      //left
      ctx.moveTo(x, y);
      //top
      ctx.arc(x + (w + 5) / 2, y, r, -PI, 0, true);
      ctx.lineTo(x + w + 5, y);
      //right
      ctx.arc(x + w + 5, y + w / 2, r, 1.5 * PI, 0.5 * PI, false);
      ctx.lineTo(x + w + 5, y + w);
      //bottom
      ctx.arc(x + (w + 5) / 2, y + w, r, 0, PI, false);
      ctx.lineTo(x, y + w);
      ctx.arc(x, y + w / 2, r, 0.5 * PI, 1.5 * PI, true);
      ctx.lineTo(x, y);

      //修饰，没有会看不出效果
      ctx.lineWidth = 1;
      ctx.fillStyle = "rgba(255, 255, 255, 0.5)";
      ctx.strokeStyle = "rgba(255, 255, 255, 0.5)";
      ctx.stroke();
      ctx[type]();
      ctx.globalCompositeOperation = "xor";

    }
  }


};
</script>
<style>
.slidingPictures {
  padding: 0;
  width: 300px;
  border-radius: 2px;
}
</style>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.login-page {
  position: fixed;
  left: 160px;
  -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  /*background: #fff;*/
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login-img {
  position: fixed;
  left: 560px;
  z-index: -1;
}
label.el-checkbox.rememberme {
  margin: 0px 0px 15px;
  text-align: left;
}

/*该样式最终是以弹窗插入*/
.sliding-pictures {
  width: 100%;
}
.vimg {
  width: 100%;
  height: 170px;
}
#codeImg,
#sliderBlock {
  padding: 7px 7px 0 7px;
  width: inherit;
  height: inherit;
}
#codeImg {
  /*display: none;*/
}
#sliderBlock {
  position: absolute;
  z-index: 4000;
}

.slider {
  width: 100%;
  height: 65px;
  border-bottom: #c7c9d0 1px solid;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.track {
  margin-left: 7px;
  width: 286px;
  height: 38px;
  background: rgba(28, 136, 188, 0.5);
  border-radius: 25px;
  font-size: 14px;
  line-height: 38px;
  padding-right: 15px;
  padding-left: 70px;
}
.pintuTrue {
  background: #67c23a;
  color: #ffffff;
}
.button {
  position: absolute;
  width: 50px;
  height: 50px;
  line-height: 48px;
  background: #ffffff;
  box-shadow: #b9bdc8 0 0 3px;
  border-radius: 50%;
  left: 7px;
  text-align: center;
  font-size: 28px;
  color: #3e5d8b;
}
/*:hover {*/
/*   color: #2181bd;*/
/* }*/

.operation {
  width: 100%;
  height: 40px;
}
> span {
  color: #9fa3ac;
  display: inline-block;
  width: 40px;
  font-size: 25px;
  line-height: 40px;
  text-align: center;
}
/*:hover {*/
/*   background: #e2e8f5;*/
/* }*/

</style>