package com.ruoyi.help.help.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.HelpReport;
import com.ruoyi.help.api.domain.bo.HelpReportBo;
import com.ruoyi.help.api.domain.vo.HelpReportVo;
import com.ruoyi.help.help.mapper.HelpAppraiseMapper;
import com.ruoyi.help.help.mapper.HelpReportMapper;
import com.ruoyi.help.help.service.IHelpReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 帮扶报表中心Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-07-15
 */
@RequiredArgsConstructor
@Service
public class HelpReportServiceImpl implements IHelpReportService {

    private final HelpReportMapper baseMapper;


    private final HelpAppraiseMapper helpAppraiseMapper;

    /**
     * 查询帮扶报表中心
     */
    @Override
    public HelpReportVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询帮扶报表中心列表
     */
    @Override
    public TableDataInfo<HelpReportVo> queryPageList(HelpReportBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HelpReport> lqw = buildQueryWrapper(bo);
        Page<HelpReportVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询帮扶报表中心列表
     */
    @Override
    public List<HelpReportVo> queryList(HelpReportBo bo) {
        LambdaQueryWrapper<HelpReport> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HelpReport> buildQueryWrapper(HelpReportBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HelpReport> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTeacherId() != null, HelpReport::getTeacherId, bo.getTeacherId());
//        lqw.eq(bo.getTime() != null, HelpReport::getTime, bo.getTime());
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null,
            HelpReport::getTime ,params.get("beginTime"), params.get("endTime"));
        return lqw;
    }

    /**
     * 新增帮扶报表中心
     */
    @Override
    public Boolean insertByBo(HelpReportBo bo) {
        HelpReport add = BeanUtil.toBean(bo, HelpReport.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改帮扶报表中心
     */
    @Override
    public Boolean updateByBo(HelpReportBo bo) {
        HelpReport update = BeanUtil.toBean(bo, HelpReport.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HelpReport entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除帮扶报表中心
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public R report() {
        List<HelpReport> list =  helpAppraiseMapper.report();
        boolean b = baseMapper.insertBatch(list);
        if (!b){
            return R.fail("失败!");
        }
        return R.ok("成功！");
    }
}
