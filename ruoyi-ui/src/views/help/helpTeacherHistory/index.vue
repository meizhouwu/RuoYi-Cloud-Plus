<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="旧老师" prop="oldId">
        <el-input
          v-model="queryParams.oldId"
          placeholder="请输入旧老师"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="新老师" prop="newId">
        <el-input
          v-model="queryParams.newId"
          placeholder="请输入新老师"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="帮扶表id" prop="helpId">
        <el-input
          v-model="queryParams.helpId"
          placeholder="请输入帮扶表id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="移交备注" prop="note">
        <el-input
          v-model="queryParams.note"
          placeholder="请输入移交备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="移交时间" prop="changeTime">
        <el-date-picker clearable
          v-model="queryParams.changeTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择移交时间">
        </el-date-picker>
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
          v-hasPermi="['help:helpTeacherHistory:add']"
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
          v-hasPermi="['help:helpTeacherHistory:edit']"
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
          v-hasPermi="['help:helpTeacherHistory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['help:helpTeacherHistory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="helpTeacherHistoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="" align="center" prop="id" v-if="true"/>
      <el-table-column label="旧老师" align="center" prop="oldId" />
      <el-table-column label="新老师" align="center" prop="newId" />
      <el-table-column label="帮扶表id" align="center" prop="helpId" />
      <el-table-column label="移交备注" align="center" prop="note" />
      <el-table-column label="移交时间" align="center" prop="changeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.changeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['help:helpTeacherHistory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['help:helpTeacherHistory:remove']"
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

    <!-- 添加或修改帮扶移交历史对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="旧老师" prop="oldId">
          <el-input v-model="form.oldId" placeholder="请输入旧老师" />
        </el-form-item>
        <el-form-item label="新老师" prop="newId">
          <el-input v-model="form.newId" placeholder="请输入新老师" />
        </el-form-item>
        <el-form-item label="帮扶表id" prop="helpId">
          <el-input v-model="form.helpId" placeholder="请输入帮扶表id" />
        </el-form-item>
        <el-form-item label="移交备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入移交备注" type="textarea"/>
        </el-form-item>
        <el-form-item label="移交时间" prop="changeTime">
          <el-date-picker clearable
            v-model="form.changeTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择移交时间">
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
import { listHelpTeacherHistory, getHelpTeacherHistory, delHelpTeacherHistory, addHelpTeacherHistory, updateHelpTeacherHistory } from "@/api/help/helpTeacherHistory";

export default {
  name: "HelpTeacherHistory",
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
      // 帮扶移交历史表格数据
      helpTeacherHistoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        oldId: undefined,
        newId: undefined,
        helpId: undefined,
        note: undefined,
        changeTime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        oldId: [
          { required: true, message: "旧老师不能为空", trigger: "blur" }
        ],
        newId: [
          { required: true, message: "新老师不能为空", trigger: "blur" }
        ],
        helpId: [
          { required: true, message: "帮扶表id不能为空", trigger: "blur" }
        ],
        note: [
          { required: true, message: "移交备注不能为空", trigger: "blur" }
        ],
        changeTime: [
          { required: true, message: "移交时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询帮扶移交历史列表 */
    getList() {
      this.loading = true;
      listHelpTeacherHistory(this.queryParams).then(response => {
        console.log(response)
        this.helpTeacherHistoryList = response.rows;
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
        oldId: undefined,
        newId: undefined,
        helpId: undefined,
        note: undefined,
        changeTime: undefined,
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
      this.title = "添加帮扶移交历史";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getHelpTeacherHistory(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改帮扶移交历史";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateHelpTeacherHistory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addHelpTeacherHistory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除帮扶移交历史编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delHelpTeacherHistory(ids);
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
      this.download('help/helpTeacherHistory/export', {
        ...this.queryParams
      }, `helpTeacherHistory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
