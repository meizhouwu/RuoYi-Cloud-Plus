package com.ruoyi.help.help.controller;

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
import com.ruoyi.help.api.domain.bo.HelpTeacherHistoryBo;
import com.ruoyi.help.api.domain.vo.HelpTeacherHistoryVo;
import com.ruoyi.help.help.service.IHelpTeacherHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 帮扶移交历史控制器
 * 前端访问路由地址为:/help/helpTeacherHistory
 *
 * @author meizhouwu
 * @date 2023-07-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/helpTeacherHistory")
public class HelpTeacherHistoryController extends BaseController {

    private final IHelpTeacherHistoryService iHelpTeacherHistoryService;

    /**
     * 查询帮扶移交历史列表
     */
    @SaCheckPermission("help:helpTeacherHistory:list")
    @GetMapping("/list")
    public TableDataInfo<HelpTeacherHistoryVo> list(HelpTeacherHistoryBo bo, PageQuery pageQuery) {
        return iHelpTeacherHistoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出帮扶移交历史列表
     */
    @SaCheckPermission("help:helpTeacherHistory:export")
    @Log(title = "帮扶移交历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HelpTeacherHistoryBo bo, HttpServletResponse response) {
        List<HelpTeacherHistoryVo> list = iHelpTeacherHistoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "帮扶移交历史", HelpTeacherHistoryVo.class, response);
    }

    /**
     * 获取帮扶移交历史详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("help:helpTeacherHistory:query")
    @GetMapping("/{id}")
    public R<HelpTeacherHistoryVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iHelpTeacherHistoryService.queryById(id));
    }

    /**
     * 新增帮扶移交历史
     */
    @SaCheckPermission("help:helpTeacherHistory:add")
    @Log(title = "帮扶移交历史", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HelpTeacherHistoryBo bo) {
        return toAjax(iHelpTeacherHistoryService.insertByBo(bo));
    }

    /**
     * 修改帮扶移交历史
     */
    @SaCheckPermission("help:helpTeacherHistory:edit")
    @Log(title = "帮扶移交历史", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HelpTeacherHistoryBo bo) {
        return toAjax(iHelpTeacherHistoryService.updateByBo(bo));
    }

    /**
     * 删除帮扶移交历史
     *
     * @param ids 主键串
     */
    @SaCheckPermission("help:helpTeacherHistory:remove")
    @Log(title = "帮扶移交历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iHelpTeacherHistoryService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
