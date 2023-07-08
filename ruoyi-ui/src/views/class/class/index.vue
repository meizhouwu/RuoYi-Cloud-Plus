<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="班级名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入班级名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班主任" prop="teacher">
        <el-select v-model="queryParams.teacher" placeholder="请选择班主任" clearable>
          <el-option
            v-for="item in teacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="就业老师" prop="employmentTeacher">
        <el-select v-model="queryParams.employmentTeacher" placeholder="请选择就业老师" clearable>
          <el-option
            v-for="item in empTeacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="助教老师" prop="helpTeacher">
        <el-select v-model="queryParams.helpTeacher" placeholder="请选择助教老师" clearable>
          <el-option
            v-for="item in helpTeacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师" prop="talkTeacher">
        <el-select v-model="queryParams.talkTeacher" placeholder="请选择讲师" clearable>
          <el-option
            v-for="item in talkTeacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="技术老师" prop="techTeacher">
        <el-select v-model="queryParams.techTeacher" placeholder="请选择技术老师" clearable>
          <el-option
            v-for="item in techTeacherList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['class:class:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['class:class:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['class:class:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['class:class:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="" align="center" prop="id" v-if="true"/>
      <el-table-column label="班级名" align="center" prop="name" />
      <el-table-column label="开班日期" align="center" prop="startClass" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startClass, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结课日期" align="center" prop="endClass" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endClass, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="班主任" align="center" prop="teacher">
        <template slot-scope="scope">
          <span>{{getTeacherListName(scope.row.teacher)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="就业老师" align="center" prop="employmentTeacher">
        <template slot-scope="scope">
          <span>{{getEmpTeacherListName(scope.row.employmentTeacher)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="助教老师" align="center" prop="helpTeacher">
        <template slot-scope="scope">
          <span>{{getHelpTeacherListName(scope.row.helpTeacher)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="讲师" align="center" prop="talkTeacher">
        <template slot-scope="scope">
          <span>{{getTalkTeacherListName(scope.row.talkTeacher)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="技术老师" align="center" prop="techTeacher">
        <template slot-scope="scope">
          <span>{{getTechTeacherListName(scope.row.techTeacher)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['class:class:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['class:class:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改班级管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班级名" prop="name">
          <el-input v-model="form.name" placeholder="请输入班级名" />
        </el-form-item>
        <el-form-item label="开班日期" prop="startClass">
          <el-date-picker clearable
            v-model="form.startClass"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择开班日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结课日期" prop="endClass">
          <el-date-picker clearable
            v-model="form.endClass"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择结课日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="班主任" prop="teacher">
          <el-select v-model="form.teacher" placeholder="请选择班主任">
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="就业老师" prop="employmentTeacher">
          <el-select v-model="form.employmentTeacher" placeholder="请选择就业老师">
            <el-option
              v-for="item in empTeacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="助教老师" prop="helpTeacher">
          <el-select v-model="form.helpTeacher" placeholder="请选择助教老师">
            <el-option
              v-for="item in helpTeacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="讲师" prop="talkTeacher">
          <el-select v-model="form.talkTeacher" placeholder="请选择讲师">
            <el-option
              v-for="item in talkTeacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="技术老师" prop="techTeacher">
          <el-select v-model="form.techTeacher" placeholder="请选择技术老师">
            <el-option
              v-for="item in techTeacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClass, getClass, delClass, addClass, updateClass } from "@/api/class/class";
import {empTeacherList, helpTeacherList, talkTeacherList, teacherList, techTeacherList} from "@/api/teacher/teacher";

export default {
  name: "Class",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 班级管理表格数据
      classList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        teacher: undefined,
        employmentTeacher: undefined,
        helpTeacher: undefined,
        talkTeacher: undefined,
        techTeacher: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "班级名不能为空", trigger: "blur" }
        ],
        startClass: [
          { required: true, message: "开班日期不能为空", trigger: "blur" }
        ],
        endClass: [
          { required: true, message: "结课日期不能为空", trigger: "blur" }
        ],
        teacher: [
          { required: true, message: "班主任不能为空", trigger: "change" }
        ],
        employmentTeacher: [
          { required: true, message: "就业老师不能为空", trigger: "change" }
        ],
        helpTeacher: [
          { required: true, message: "助教老师不能为空", trigger: "change" }
        ],
        talkTeacher: [
          { required: true, message: "讲师不能为空", trigger: "change" }
        ],
        techTeacher: [
          { required: true, message: "技术老师不能为空", trigger: "change" }
        ],
      },
      //班主任
      teacherList: [],
      //就业老师
      empTeacherList: [],
      //助教老师
      helpTeacherList: [],
      //讲师
      talkTeacherList: [],
      //技术老师
      techTeacherList: []
    };
  },
  created() {
    this.getList();
    this.getTeacherList();
    this.getEmpTeacherList();
    this.getHelpTeacherList();
    this.getTalkTeacherList();
    this.getTechTeacherList();
  },
  methods: {
    /*查询所有班主任*/
    getTalkTeacherList(){
      this.loading = true;
      talkTeacherList().then(response => {
        this.talkTeacherList = response;
        console.log(response)
        console.log(this.teacherList)
        this.loading = false;
      })
    },
    /** 老师姓名，显示 */
    getTalkTeacherListName(id) {
      for (let i = 0; i < this.talkTeacherList.length; i++) {
        if (this.talkTeacherList[i].id === id) {
          return this.talkTeacherList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /*查询所有班主任*/
    getTechTeacherList(){
      this.loading = true;
      techTeacherList().then(response => {
        this.techTeacherList = response;
        console.log(response)
        console.log(this.teacherList)
        this.loading = false;
      })
    },
    /** 老师姓名，显示 */
    getTechTeacherListName(id) {
      for (let i = 0; i < this.techTeacherList.length; i++) {
        if (this.techTeacherList[i].id === id) {
          return this.techTeacherList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /*查询所有班主任*/
    getTeacherList(){
      this.loading = true;
      teacherList().then(response => {
        this.teacherList = response;
        console.log(response)
        console.log(this.teacherList)
        this.loading = false;
      })
    },
    /** 老师姓名，显示 */
    getTeacherListName(id) {
      for (let i = 0; i < this.teacherList.length; i++) {
        if (this.teacherList[i].id === id) {
          return this.teacherList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /*查询所有就业老师*/
    getEmpTeacherList(){
      this.loading = true;
      empTeacherList().then(response => {
        this.empTeacherList = response;
        this.loading = false;
      })
    },
    /** 老师姓名，显示 */
    getEmpTeacherListName(id) {
      for (let i = 0; i < this.empTeacherList.length; i++) {
        if (this.empTeacherList[i].id === id) {
          return this.empTeacherList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /*查询所有助教老师*/
    getHelpTeacherList(){
      this.loading = true;
      helpTeacherList().then(response => {
        this.helpTeacherList = response;
        this.loading = false;
      })
    },
    /** 老师姓名，显示 */
    getHelpTeacherListName(id) {
      for (let i = 0; i < this.helpTeacherList.length; i++) {
        if (this.helpTeacherList[i].id === id) {
          return this.helpTeacherList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /** 查询班级管理列表 */
    getList() {
      this.loading = true;
      listClass(this.queryParams).then(response => {
        this.classList = response.rows;
        console.log(this.classList)
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        startClass: undefined,
        endClass: undefined,
        teacher: undefined,
        employmentTeacher: undefined,
        helpTeacher: undefined,
        createTime: undefined,
        createBy: undefined,
        updateTime: undefined,
        updateBy: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加班级管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getClass(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改班级管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateClass(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addClass(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除班级管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delClass(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('class/class/export', {
        ...this.queryParams
      }, `class_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
