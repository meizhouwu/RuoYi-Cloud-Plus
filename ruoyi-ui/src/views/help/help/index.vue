<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级" prop="classId">
        <el-input
          v-model="queryParams.classId"
          placeholder="请输入班级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="问题类型" prop="errType">
        <el-select v-model="queryParams.errType" placeholder="请选择问题类型" clearable>
          <el-option
            v-for="dict in dict.type.help_student_err"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="dealStatus">
        <el-select v-model="queryParams.dealStatus" placeholder="请选择处理状态" clearable>
          <el-option
            v-for="dict in dict.type.help_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          v-hasPermi="['help:help:add']"
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
          v-hasPermi="['help:help:edit']"
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
          v-hasPermi="['help:help:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['help:help:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="helpList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="" align="center" prop="id" v-if="true"/>
      <el-table-column label="学生姓名" align="center" prop="name" />
      <el-table-column label="学生邮箱" align="center" prop="email" />
      <el-table-column label="学生电话" align="center" prop="tel" />
      <el-table-column label="班级" align="center" prop="classId">
        <template slot-scope="scope">
          <span>{{getClassListName(scope.row.classId)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="预约时间" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="帮扶老师" align="center" prop="teacherId">
        <template slot-scope="scope">
          <span>{{getTeacherListName(scope.row.teacherId)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="问题类型" align="center" prop="errType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.help_student_err" :value="scope.row.errType"/>
        </template>
      </el-table-column>
      <el-table-column label="问题描述" align="center" prop="errNote" />
      <el-table-column label="处理状态" align="center" prop="dealStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.help_status" :value="scope.row.dealStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="老师备注" align="center" prop="teacherNote" />
      <el-table-column label="完成时间" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['help:help:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['help:help:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleOver(scope.row)"
          >帮扶移交</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDone(scope.row)"
          >完成帮扶</el-button>
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

    <!-- 添加或修改帮扶管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="学生邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入学生邮箱" />
        </el-form-item>
        <el-form-item label="学生电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入学生电话" />
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-input v-model="form.classId" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="预约时间" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择预约时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="帮扶老师" prop="teacherId">
          <el-input v-model="form.teacherId" placeholder="请输入帮扶老师" />
        </el-form-item>
        <el-form-item label="问题类型" prop="errType">
          <el-select v-model="form.errType" placeholder="请选择问题类型">
            <el-option
              v-for="dict in dict.type.help_student_err"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述" prop="errNote">
          <el-input v-model="form.errNote" placeholder="请输入问题描述" type="textarea"/>
        </el-form-item>
        <el-form-item label="处理状态" prop="dealStatus">
          <el-select v-model="form.dealStatus" placeholder="请选择处理状态">
            <el-option
              v-for="dict in dict.type.help_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="老师备注" prop="teacherNote">
          <el-input v-model="form.teacherNote" placeholder="请输入老师备注" type="textarea"/>
        </el-form-item>
        <el-form-item label="完成时间" prop="endDate">
          <el-date-picker clearable
            v-model="form.endDate"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择完成时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--帮扶移交界面-->
    <el-dialog title="帮扶移交" :visible.sync="overFormOpen" width="500px" append-to-body>
      <el-form ref="overForm" :model="overForm" :rules="overFormRules" label-width="80px">
        <el-form-item label="学生姓名" prop="stuName">
          <el-input v-model="overForm.stuName" placeholder="请输入学生姓名" :disabled="edit"/>
        </el-form-item>
        <el-form-item label="移交老师" prop="newId">
          <el-select v-model="overForm.newId" placeholder="请选择移交老师">
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              v-if="item.id != overForm.oldId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="移交备注" prop="note">
          <el-input v-model="overForm.note" placeholder="请输入移交备注" type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitOverForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--完成帮扶界面-->
    <el-dialog :title="title" :visible.sync="DoneOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入学生姓名" :disabled="edit"/>
        </el-form-item>
        <el-form-item label="处理状态" prop="dealStatus">
          <el-select v-model="form.dealStatus" placeholder="请选择处理状态">
            <el-option
              v-for="dict in dict.type.help_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="老师备注" prop="teacherNote">
          <el-input v-model="form.teacherNote" placeholder="请输入老师备注" type="textarea"/>
        </el-form-item>
        <el-form-item label="完成时间" prop="endDate">
          <el-date-picker clearable
                          v-model="form.endDate"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择完成时间">
          </el-date-picker>
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
import { listHelp, getHelp, delHelp, addHelp, updateHelp } from "@/api/help/help";
import {listClass} from "@/api/class/class";
import {listTeacher} from "@/api/teacher/teacher";
import {addHelpTeacherHistory} from "@/api/help/helpTeacherHistory";

export default {
  name: "Help",
  dicts: ['help_student_err', 'help_status'],
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
      // 帮扶管理表格数据
      helpList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        classId: undefined,
        errType: undefined,
        dealStatus: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        dealStatus: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        teacherNote: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        endDate: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
      },
      classList: [],
      teacherList: [],
      // 移交表单
      overForm: {},
      overFormOpen: false,
      overFormRules: {
        newId: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        note: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
      },
      edit: true,
      DoneOpen: false
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getTeacherList();
  },
  methods: {
    /*查询班级列表*/
    getClassList(){

      this.loading = true;
      listClass().then(response => {
        this.classList = response.rows;
        this.loading = false;
      })
    },
    /** 班级名称，显示 */
    getClassListName(id) {
      for (let i = 0; i < this.classList.length; i++) {
        if (this.classList[i].id === id) {
          return this.classList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /*查询班级列表*/
    getTeacherList(){
      this.loading = true;
      listTeacher().then(response => {
        console.log(response)
        this.teacherList = response.rows;
        this.loading = false;
      })
    },
    /** 班级名称，显示 */
    getTeacherListName(id) {
      for (let i = 0; i < this.teacherList.length; i++) {
        if (this.teacherList[i].id === id) {
          return this.teacherList[i].name;
        }
      }
      return null; // 如果没有找到对应的教师，则返回 null
    },
    /** 查询帮扶管理列表 */
    getList() {
      this.loading = true;
      listHelp(this.queryParams).then(response => {
        this.helpList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.overFormOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        email: undefined,
        tel: undefined,
        classId: undefined,
        startDate: undefined,
        teacherId: undefined,
        errType: undefined,
        errNote: undefined,
        dealStatus: undefined,
        teacherNote: undefined,
        endDate: undefined,
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
      this.title = "添加帮扶管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getHelp(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改帮扶管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateHelp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.DoneOpen = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addHelp(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除帮扶管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delHelp(ids);
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
      this.download('help/help/export', {
        ...this.queryParams
      }, `help_${new Date().getTime()}.xlsx`)
    },
    /** 帮扶移交界面*/
    handleOver(row){
      this.overFormOpen = true;
      this.overForm = {
        stuName: row.name,
        helpId: row.id,
        oldId: row.teacherId,
        newId: '',
        note: ''
      }
    },
    /** 帮扶移交提交*/
    submitOverForm() {
      let param = {
        oldId: this.overForm.oldId,
        newId: this.overForm.newId,
        helpId: this.overForm.helpId,
        note: this.overForm.note,
        // changeTime: ''
      }
      this.$refs["overForm"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          addHelpTeacherHistory(param).then(response => {
            this.$modal.msgSuccess("移交成功");
            this.overFormOpen = false;
            this.getList();
          }).finally(() => {
            this.buttonLoading = false;
          });
        }
      });
    },
    /** 完成帮扶弹出框*/
    handleDone(row){
      // this.loading = true;
      // this.reset();
      this.DoneOpen = true;
      this.title = "完成帮扶";
      const id = row.id || this.ids
      getHelp(id).then(response => {
        // this.loading = false;
        this.form = response.data;
      });
    }
  }
};
</script>
