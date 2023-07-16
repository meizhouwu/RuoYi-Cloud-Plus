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
import com.ruoyi.help.api.domain.bo.HelpReportBo;
import com.ruoyi.help.api.domain.vo.HelpReportVo;
import com.ruoyi.help.help.service.IHelpReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 帮扶报表中心控制器
 * 前端访问路由地址为:/help/helpReport
 *
 * @author meizhouwu
 * @date 2023-07-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/helpReport")
public class HelpReportController extends BaseController {

    private final IHelpReportService iHelpReportService;

    /**
     * 查询帮扶报表中心列表
     */
    @SaCheckPermission("help:helpReport:list")
    @GetMapping("/list")
    public TableDataInfo<HelpReportVo> list(HelpReportBo bo, PageQuery pageQuery) {
        return iHelpReportService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出帮扶报表中心列表
     */
    @SaCheckPermission("help:helpReport:export")
    @Log(title = "帮扶报表中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HelpReportBo bo, HttpServletResponse response) {
        List<HelpReportVo> list = iHelpReportService.queryList(bo);
        ExcelUtil.exportExcel(list, "帮扶报表中心", HelpReportVo.class, response);
    }

    /**
     * 获取帮扶报表中心详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("help:helpReport:query")
    @GetMapping("/{id}")
    public R<HelpReportVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iHelpReportService.queryById(id));
    }

    /**
     * 新增帮扶报表中心
     */
    @SaCheckPermission("help:helpReport:add")
    @Log(title = "帮扶报表中心", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HelpReportBo bo) {
        return toAjax(iHelpReportService.insertByBo(bo));
    }

    /**
     * 修改帮扶报表中心
     */
    @SaCheckPermission("help:helpReport:edit")
    @Log(title = "帮扶报表中心", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HelpReportBo bo) {
        return toAjax(iHelpReportService.updateByBo(bo));
    }

    /**
     * 删除帮扶报表中心
     *
     * @param ids 主键串
     */
    @SaCheckPermission("help:helpReport:remove")
    @Log(title = "帮扶报表中心", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iHelpReportService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
