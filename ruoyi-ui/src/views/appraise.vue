<template>
  <div class="login">
    <el-form ref="form" :model="form" :rules="rules" class="login-form">
      <h3 class="title">我要评价</h3>
      <el-form-item label="老师名字">
        <el-input v-model="form.teacherName" type="text" auto-complete="off" disabled>
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item label="学生名字">
        <el-input v-model="form.studentName" type="text" auto-complete="off" disabled>
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-form-item label="处理时间">
        <el-date-picker clearable
                        v-model="form.endTime"
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



      <!--打分-->
      <el-form-item prop="score">
        <el-rate
          style="text-align: center"
          v-model="form.score"
          show-text>
        </el-rate>
      </el-form-item>




      <el-form-item prop="appraise">
        <el-input type="textarea"
                  v-model="form.appraise"
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





import {listStudent} from "@/api/student/student";
import {addHelpAppraise} from "@/api/help/helpAppraise";
import {getHelpByCode} from "@/api/help/help";
import {getTeacher, listTeacher} from "@/api/teacher/teacher";

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
      teacher: {},
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
    let code = location.href.split("=")[1];
    console.log(code)
    this.getHelp(code);
  },
  methods: {
    getHelp(code){
      getHelpByCode(code).then(response => {
        debugger
        console.log(response);
        this.form = {
          teacherName: getTeacher(response.data.teacherId).then(res => {this.form.teacherName = res.data.name}),
          studentName: response.data.name,
          endTime: response.data.endDate,
          errType: response.data.errType,
          teacherId: response.data.teacherId,
          score: '',
          appraise: '',
          id: response.data.id
        }
        console.log(this.form)
      })
    },


    handle() {
      let params = {
        teacherId: this.form.teacherId,
        helpId: this.form.id,
        score: this.form.score,
        appraise: this.form.appraise,
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          addHelpAppraise(params).then(response => {
            debugger
            console.log(response)
            this.$modal.msgSuccess("评价成功");

          }).catch()
        }
      });
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
