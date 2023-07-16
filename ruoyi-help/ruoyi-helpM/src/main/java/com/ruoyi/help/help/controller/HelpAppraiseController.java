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
import com.ruoyi.help.api.domain.bo.HelpAppraiseBo;
import com.ruoyi.help.api.domain.vo.HelpAppraiseVo;
import com.ruoyi.help.help.service.IHelpAppraiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 帮扶学生评价控制器
 * 前端访问路由地址为:/help/helpAppraise
 *
 * @author meizhouwu
 * @date 2023-07-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/helpAppraise")
public class HelpAppraiseController extends BaseController {

    private final IHelpAppraiseService iHelpAppraiseService;

    /**
     * 查询帮扶学生评价列表
     */
    @SaCheckPermission("help:helpAppraise:list")
    @GetMapping("/list")
    public TableDataInfo<HelpAppraiseVo> list(HelpAppraiseBo bo, PageQuery pageQuery) {
        return iHelpAppraiseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出帮扶学生评价列表
     */
    @SaCheckPermission("help:helpAppraise:export")
    @Log(title = "帮扶学生评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HelpAppraiseBo bo, HttpServletResponse response) {
        List<HelpAppraiseVo> list = iHelpAppraiseService.queryList(bo);
        ExcelUtil.exportExcel(list, "帮扶学生评价", HelpAppraiseVo.class, response);
    }

    /**
     * 获取帮扶学生评价详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("help:helpAppraise:query")
    @GetMapping("/{id}")
    public R<HelpAppraiseVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iHelpAppraiseService.queryById(id));
    }

    /**
     * 新增帮扶学生评价
     */
//    @SaCheckPermission("help:helpAppraise:add")
    @Log(title = "帮扶学生评价", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HelpAppraiseBo bo) {
        return toAjax(iHelpAppraiseService.insertByBo(bo));
    }

    /**
     * 修改帮扶学生评价
     */
    @SaCheckPermission("help:helpAppraise:edit")
    @Log(title = "帮扶学生评价", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HelpAppraiseBo bo) {
        return toAjax(iHelpAppraiseService.updateByBo(bo));
    }

    /**
     * 删除帮扶学生评价
     *
     * @param ids 主键串
     */
    @SaCheckPermission("help:helpAppraise:remove")
    @Log(title = "帮扶学生评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iHelpAppraiseService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

}
