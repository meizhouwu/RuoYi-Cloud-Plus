package com.ruoyi.help.classes.controller;

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
import com.ruoyi.help.classes.domain.bo.ClassesBo;
import com.ruoyi.help.classes.domain.vo.ClassesVo;
import com.ruoyi.help.classes.service.IClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 班级管理控制器
 * 前端访问路由地址为:/class/class
 *
 * @author meizhouwu
 * @date 2023-06-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/class")
public class ClassesController extends BaseController {

    private final IClassesService iClassesService;

    /**
     * 查询班级管理列表
     */
    @SaCheckPermission("class:class:list")
    @GetMapping("/list")
    public TableDataInfo<ClassesVo> list(ClassesBo bo, PageQuery pageQuery) {
        return iClassesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出班级管理列表
     */
    @SaCheckPermission("class:class:export")
    @Log(title = "班级管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ClassesBo bo, HttpServletResponse response) {
        List<ClassesVo> list = iClassesService.queryList(bo);
        ExcelUtil.exportExcel(list, "班级管理", ClassesVo.class, response);
    }

    /**
     * 获取班级管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("class:class:query")
    @GetMapping("/{id}")
    public R<ClassesVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iClassesService.queryById(id));
    }

    /**
     * 新增班级管理
     */
    @SaCheckPermission("class:class:add")
    @Log(title = "班级管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ClassesBo bo) {
        return toAjax(iClassesService.insertByBo(bo));
    }

    /**
     * 修改班级管理
     */
    @SaCheckPermission("class:class:edit")
    @Log(title = "班级管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ClassesBo bo) {
        return toAjax(iClassesService.updateByBo(bo));
    }

    /**
     * 删除班级管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("class:class:remove")
    @Log(title = "班级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iClassesService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询班级列表
     */
    @GetMapping("/classList")
    public List<ClassesVo> classList(){
        return iClassesService.classList();
    }
}
