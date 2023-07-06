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
import com.ruoyi.help.help.domain.bo.HelpBo;
import com.ruoyi.help.help.domain.vo.HelpVo;
import com.ruoyi.help.help.service.IHelpService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 帮扶管理控制器
 * 前端访问路由地址为:/help/help
 *
 * @author meizhouwu
 * @date 2023-07-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/help")
public class HelpController extends BaseController {

    private final IHelpService iHelpService;

    /**
     * 查询帮扶管理列表
     */
    @SaCheckPermission("help:help:list")
    @GetMapping("/list")
    public TableDataInfo<HelpVo> list(HelpBo bo, PageQuery pageQuery) {
        return iHelpService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出帮扶管理列表
     */
    @SaCheckPermission("help:help:export")
    @Log(title = "帮扶管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HelpBo bo, HttpServletResponse response) {
        List<HelpVo> list = iHelpService.queryList(bo);
        ExcelUtil.exportExcel(list, "帮扶管理", HelpVo.class, response);
    }

    /**
     * 获取帮扶管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("help:help:query")
    @GetMapping("/{id}")
    public R<HelpVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iHelpService.queryById(id));
    }

    /**
     * 新增帮扶管理
     */
//    @SaCheckPermission("help:help:add")
    @Log(title = "帮扶管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HelpBo bo) {
        return toAjax(iHelpService.insertByBo(bo));
    }

    /**
     * 修改帮扶管理
     */
    @SaCheckPermission("help:help:edit")
    @Log(title = "帮扶管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HelpBo bo) {
        return toAjax(iHelpService.updateByBo(bo));
    }

    /**
     * 删除帮扶管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("help:help:remove")
    @Log(title = "帮扶管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iHelpService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
