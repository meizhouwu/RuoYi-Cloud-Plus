<template>
  <div class="login">
    <el-form ref="form" :model="form" :rules="rules" class="login-form">
      <h3 class="title">我要评价</h3>
      <el-form-item label="老师名字">
        <el-input v-model="help.teacherName" type="text" auto-complete="off" disabled>
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item label="学生名字">
        <el-input v-model="help.studentName" type="text" auto-complete="off" disabled>
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item label="处理时间">
        <el-date-picker clearable
                        v-model="help.endTime"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        disabled="true"
                        style="width: 100%"/>
      </el-form-item>



      <el-form-item label="问题类型">
        <el-select v-model="form.errType" disabled style="width: 100%">
          <el-option
            v-for="dict in dict.type.help_student_err"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          ></el-option>
        </el-select>
      </el-form-item>





      <!--    实现五颗星星进行评价-->
      <el-form-item prop="score">
        <div class="star">
          <el-rate
            ref="form"
            v-model="form.score"
            disabled-void-class
          >
          </el-rate>
          <div>
            <span v-if="form.score==1">伤心</span>
            <span v-if="form.score==2">难过</span>
            <span v-if="form.score==3">一般</span>
            <span v-if="form.score==4">不错</span>
            <span v-if="form.score==5">优秀</span>
          </div>
        </div>
      </el-form-item>

      <el-form-item prop="appraise">
        <el-input type="textarea"
                  resize="none"
                  v-model="help.appraise"
                  placeholder="请输入评价内容"/>
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
      form: {},
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
