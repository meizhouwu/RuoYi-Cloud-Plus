<template>
  <div class="login">
    <el-form ref="helpForm" :model="helpForm" :rules="loginRules" class="login-form">
      <h3 class="title">我要问问题</h3>
      <el-form-item prop="name">
        <el-input
          v-model="helpForm.name"
          type="text"
          auto-complete="off"
          placeholder="请填写真实姓名"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          v-model="helpForm.email"
          type="email"
          auto-complete="off"
          placeholder="请填写正确的邮箱">
          <svg-icon slot="prefix" icon-class="email" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="wx">
        <el-input
          v-model="helpForm.wx"
          type="text"
          auto-complete="off"
          placeholder="请填写正确的微信，否则老师无法联系你"
        >
          <svg-icon slot="prefix" icon-class="validwx" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="tel">
        <el-input
          v-model="helpForm.tel"
          type="text"
          auto-complete="off"
          placeholder="请填写正确的电话，否则老师无法联系你"
        >
          <svg-icon slot="prefix" icon-class="validwx" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item prop="classId">
        <el-select v-model="helpForm.classId" placeholder="请选择班级" style="width: 100%">
          <el-option
            v-for="item in classesList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="startClass" >
        <el-date-picker clearable
                        v-model="helpForm.startClass"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择预约日期"
                        style="width: 100%">
        </el-date-picker>
      </el-form-item>


      <el-form-item prop="err">
        <el-select v-model="helpForm.err" placeholder="请选择问题" style="width: 100%">
          <el-option
            v-for="dict in dict.type.help_student_err"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          ></el-option>
        </el-select>
      </el-form-item>


      <el-form-item prop="teacherId">
        <el-select v-model="helpForm.teacherId" placeholder="请选择意向老师" style="width: 100%">
          <el-option
            v-for="item in teacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="note">
        <el-input
          v-model="helpForm.note"
          type="textarea"
          auto-complete="off"
          placeholder="请填写问题描述"
        >
          <svg-icon slot="prefix" icon-class="validwx" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>


      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">提交预约</span>
          <span v-else>预约中...</span>
        </el-button>
      </el-form-item>


    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2023 疯狂的狮子Li All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>


import {listClass} from "@/api/class/class";
import {listTeacher} from "@/api/teacher/teacher";

export default {
  name: "help",
  dicts: ['help_student_err'],
  data() {
    return {
      classesList: [],
      teacherList: [],
      wxUrl: "",
      helpForm: {
        name: "",
        email: "",
        tel: '',
        rememberMe: false,
        wx: "",
        teacherId: '',
        uuid: "",
        note: ''
      },
      loginRules: {
        name: [
          { required: true, trigger: "blur", message: "请输入您的姓名" }
        ],
        email: [
          { required: true, trigger: "blur", message: "请输入您的邮箱" }
        ],
        tel: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        wx: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getListTeacher();
    this.getListClass();

  },
  methods: {
    getListTeacher() {
      listTeacher(this.queryParams).then(response => {
        this.teacherList = response.rows;
      });
    },
    getListClass() {
      listClass(this.queryParams).then(response => {
        this.classesList = response.rows;
      });
    },

    handleLogin() {
      this.$refs.helpForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.helpForm.rememberMe) {
            Cookies.set("name", this.helpForm.name, { expires: 30 });
            Cookies.set("email", encrypt(this.helpForm.email), { expires: 30 });
            Cookies.set('rememberMe', this.helpForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("name");
            Cookies.remove("email");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.helpForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getwx();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-wx {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-wx-img {
  height: 38px;
}
</style>
