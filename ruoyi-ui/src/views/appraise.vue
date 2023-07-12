<template>
  <div class="login">
    <el-form ref="form" :model="form" :rules="rules" class="login-form">
      <h3 class="title">我要评价</h3>
      <span style="font-weight: bold">老师名字</span>
      <el-form-item prop="teacherId">
        <el-select v-model="form.teacherId" placeholder="请选择意向老师" style="width: 100%" clearable>
          <el-option
            v-for="item in teacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            :disabled="edit"
            v-if="item.id == help.teacherId"
          ></el-option>
        </el-select>
      </el-form-item>

      <span style="font-weight: bold">学生名字</span>
      <el-form-item>
        <el-input
          v-model="form.studentId"
          type="email"
          auto-complete="off"
          placeholder="请填写正确的邮箱（必填）"
          :disabled="edit">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <span style="font-weight: bold">处理时间</span>
      <el-form-item>
        <el-date-picker clearable
                        v-model="form.doneDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择预约日期，提前三天预约（必填）"
                        :picker-options="afterDate"
                        style="width: 100%"
                        :disabled="edit"
        >
        </el-date-picker>
      </el-form-item>

      <span style="font-weight: bold">问题类型</span>
      <el-form-item>
        <el-select v-model="form.errType" placeholder="请选择班级" style="width: 100%" :disabled="edit" clearable>
          <el-option
            v-for="dict in dict.type.help_student_err"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          ></el-option>
        </el-select>
      </el-form-item>

      <span style="font-weight: bold">打分</span>
      <el-form-item prop="score">
        <el-select v-model="form.score" placeholder="请选择问题（必填）" style="width: 100%" clearable>
          <el-option
            v-for="dict in dict.type.help_student_err"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          ></el-option>
        </el-select>
      </el-form-item>

      <span style="font-weight: bold">服务评价</span>
      <el-form-item prop="appraise">
        <el-input
          v-model="form.appraise"
          type="textarea"
          auto-complete="off"
          placeholder="请对本次服务进行评价"
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
          <span v-if="!loading">提交评价</span>
          <span v-else>评价中...</span>
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




import {listTeacher} from "@/api/teacher/teacher";
import {listStudent} from "@/api/student/student";
import {addHelpAppraise} from "@/api/help/helpAppraise";
import {getHelpByCode} from "@/api/help/help";

export default {
  name: "appraise",
  dicts: ['help_student_err'],
  data() {
    return {
      form: {
        teacherId: "",
        studentId: "",
        doneDate: '',
        errType: '',
        score: '',
        appraise: '',
      },
      rules: {
        score: [
          { required: true, trigger: "blur", message: "请不要忘记打分" }
        ],
        appraise: [
          { required: true, trigger: "blur", message: "请不要忘记评价" }
        ],
      },
      loading: false,
      edit: true,
      teacherList: [],
      studentList: [],
      help: []

    };
  },
  // watch: {
  //   $route: {
  //     handler: function(route) {
  //       this.redirect = route.query && route.query.redirect;
  //     },
  //     immediate: true
  //   }
  // },
  created() {
    this.getListTeacher();
    this.getListStudent();
    let code = location.href.split("=")[1];
    console.log(code)
    this.getHelp(code);
  },
  methods: {
    getListTeacher() {
      listTeacher().then(response => {
        this.teacherList = response.rows;
        console.log(this.teacherList)
      });
    },
    getListStudent() {
      listStudent().then(response => {
        this.studentList = response.rows;
      });
    },

    getHelp(code){
      getHelpByCode(code).then(response => {
        this.help = response.data;
        console.log(this.help)
      })
    },

    handle() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addHelpAppraise(this.form).then(response => {
            debugger
            console.log(response)
            this.$modal.msgSuccess("评价成功");
          }).catch()
        }
        this.reset();
      });
    },
    reset() {
      this.form = {
        teacherId: "",
        studentId: "",
        doneDate: '',
        errType: '',
        score: '',
        appraise: '',
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
