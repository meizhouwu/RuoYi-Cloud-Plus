<template>
  <div class="login">
    <el-form ref="helpForm" :model="helpForm" :rules="loginRules" class="login-form">
      <h3 class="title">我要问问题</h3>
      <el-form-item prop="name">
        <el-input
          v-model="helpForm.name"
          type="text"
          auto-complete="off"
          placeholder="请填写真实姓名（必填）"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          v-model="helpForm.email"
          type="email"
          auto-complete="off"
          placeholder="请填写正确的邮箱（必填）">
          <svg-icon slot="prefix" icon-class="email" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="tel">
        <el-input
          v-model="helpForm.tel"
          type="text"
          auto-complete="off"
          placeholder="请填写正确的电话（必填）"
        >
          <svg-icon slot="prefix" icon-class="validwx" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item prop="classId">
        <el-select v-model="helpForm.classId" placeholder="请选择班级" style="width: 100%" clearable>
          <el-option
            v-for="item in classesList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="startDate" >
        <el-date-picker clearable
                        v-model="helpForm.startDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择预约日期，提前三天预约（必填）"
                        :picker-options="afterDate"
                        style="width: 100%"
                        >
        </el-date-picker>
      </el-form-item>


      <el-form-item prop="errType">
        <el-select v-model="helpForm.errType" placeholder="请选择问题（必填）" style="width: 100%" clearable>
          <el-option
            v-for="dict in dict.type.help_student_err"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          ></el-option>
        </el-select>
      </el-form-item>


      <el-form-item prop="teacherId">
        <el-select v-model="helpForm.teacherId" placeholder="请选择意向老师" style="width: 100%" clearable>
          <el-option
            v-for="item in teacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="errNote">
        <el-input
          v-model="helpForm.errNote"
          type="textarea"
          auto-complete="off"
          placeholder="请填写问题描述（必填）"
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
          @click.native.prevent="handle"
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
import {addHelp} from "@/api/help/help";


export default {
  name: "help",
  dicts: ['help_student_err'],
  data() {
    return {
      afterDate: {
        disabledDate(time) {
          // 禁止选择以前的时间
          return time.getTime() < Date.now() + 8.64e7 * 3;
        },
      },
      classesList: [],
      teacherList: [],
      helpForm: {
        name: "",
        email: "",
        tel: '',
        classId: '',
        startDate: '',
        errType: '',
        teacherId: '',
        errNote: '',
      },
      loginRules: {
        name: [
          { required: true, trigger: "blur", message: "请输入您的姓名" }
        ],
        email: [
          { required: true, trigger: "blur", message: "请输入您的邮箱" }
        ],
        tel: [
          { required: true, trigger: "blur", message: "请输入您的电话" }
        ],
        startDate: [
          { required: true, trigger: "blur", message: "请选择预约时间" }
        ],
        errType: [
          { required: true, trigger: "blur", message: "请选择问题类型" }
        ],
        errNote: [
          { required: true, trigger: "blur", message: "请选择填写问题描述" }
        ],
      },
      loading: false,
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

    handle() {
      this.$refs["helpForm"].validate(valid => {
        if (valid) {
          addHelp(this.helpForm).then(response => {
            debugger
            console.log(response)
            this.$modal.msgSuccess("预约成功");
          }).catch()
        }
        this.reset();
      });
    },
    reset() {
      this.helpForm = {
        name: "",
        email: "",
        tel: '',
        classId: '',
        startDate: '',
        errType: '',
        teacherId: '',
        errNote: '',
      };
    },
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
