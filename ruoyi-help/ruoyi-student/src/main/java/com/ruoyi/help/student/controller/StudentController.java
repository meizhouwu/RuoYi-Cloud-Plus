package com.ruoyi.help.student.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.student.service.IStudentService;
import com.ruoyi.student.api.domain.bo.StudentBo;
import com.ruoyi.student.api.domain.vo.StudentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 学生管理控制器
 * 前端访问路由地址为:/student/student
 *
 * @author meizhouwu
 * @date 2023-06-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    private final IStudentService iStudentService;

    /**
     * 查询学生管理列表
     */
    @SaCheckPermission("student:student:list")
    @GetMapping("/list")
    public TableDataInfo<StudentVo> list(StudentBo bo, PageQuery pageQuery) {
        return iStudentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出学生管理列表
     */
    @SaCheckPermission("student:student:export")
    @Log(title = "学生管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(StudentBo bo, HttpServletResponse response) {
        List<StudentVo> list = iStudentService.queryList(bo);
        ExcelUtil.exportExcel(list, "学生管理", StudentVo.class, response);
    }

    /**
     * 获取学生管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("student:student:query")
    @GetMapping("/{id}")
    public R<StudentVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iStudentService.queryById(id));
    }

    /**
     * 新增学生管理
     */
    @SaCheckPermission("student:student:add")
    @Log(title = "学生管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody StudentBo bo) {
        return toAjax(iStudentService.insertByBo(bo));
    }

    /**
     * 修改学生管理
     */
    @SaCheckPermission("student:student:edit")
    @Log(title = "学生管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody StudentBo bo) {
        return toAjax(iStudentService.updateByBo(bo));
    }

    /**
     * 删除学生管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("student:student:remove")
    @Log(title = "学生管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iStudentService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
