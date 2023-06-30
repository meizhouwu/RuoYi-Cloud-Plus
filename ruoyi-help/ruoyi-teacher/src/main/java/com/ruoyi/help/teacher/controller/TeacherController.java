package com.ruoyi.help.teacher.controller;

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
import com.ruoyi.help.teacher.domain.bo.TeacherBo;
import com.ruoyi.help.teacher.domain.vo.TeacherVo;
import com.ruoyi.help.teacher.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 老师管理控制器
 * 前端访问路由地址为:/teacher/teacher
 *
 * @author meizhouwu
 * @date 2023-06-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    private final ITeacherService iTeacherService;

    /**
     * 查询老师管理列表
     */
    @SaCheckPermission("teacher:teacher:list")
    @GetMapping("/list")
    public TableDataInfo<TeacherVo> list(TeacherBo bo, PageQuery pageQuery) {
        return iTeacherService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出老师管理列表
     */
    @SaCheckPermission("teacher:teacher:export")
    @Log(title = "老师管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TeacherBo bo, HttpServletResponse response) {
        List<TeacherVo> list = iTeacherService.queryList(bo);
        ExcelUtil.exportExcel(list, "老师管理", TeacherVo.class, response);
    }

    /**
     * 获取老师管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("teacher:teacher:query")
    @GetMapping("/{id}")
    public R<TeacherVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iTeacherService.queryById(id));
    }

    /**
     * 新增老师管理
     */
    @SaCheckPermission("teacher:teacher:add")
    @Log(title = "老师管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TeacherBo bo) {
        return toAjax(iTeacherService.insertByBo(bo));
    }

    /**
     * 修改老师管理
     */
    @SaCheckPermission("teacher:teacher:edit")
    @Log(title = "老师管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TeacherBo bo) {
        return toAjax(iTeacherService.updateByBo(bo));
    }

    /**
     * 删除老师管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("teacher:teacher:remove")
    @Log(title = "老师管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iTeacherService.deleteWithValidByIds(Arrays.asList(ids), true));
    }


    /**
     * 查询所有班主任
     */
    @GetMapping("/teacherList")
    public List<TeacherVo> teacherList() {
        return iTeacherService.teacherList();
    }


    /**
     * 查询所有就业老师
     */
    @GetMapping("/empTeacherList")
    public List<TeacherVo> empTeacherList() {
        return iTeacherService.empTeacherList();
    }

    /**
     * 查询所有助教老师
     */
    @GetMapping("/helpTeacherList")
    public List<TeacherVo> helpTeacherList() {
        return iTeacherService.helpTeacherList();
    }
}
