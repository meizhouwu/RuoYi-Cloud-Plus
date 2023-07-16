package com.ruoyi.help.help.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.HelpAppraise;
import com.ruoyi.help.api.domain.bo.HelpAppraiseBo;
import com.ruoyi.help.api.domain.vo.HelpAppraiseVo;
import com.ruoyi.help.help.mapper.HelpAppraiseMapper;
import com.ruoyi.help.help.service.IHelpAppraiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 帮扶学生评价Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-07-10
 */
@RequiredArgsConstructor
@Service
public class HelpAppraiseServiceImpl implements IHelpAppraiseService {

    private final HelpAppraiseMapper baseMapper;

    /**
     * 查询帮扶学生评价
     */
    @Override
    public HelpAppraiseVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询帮扶学生评价列表
     */
    @Override
    public TableDataInfo<HelpAppraiseVo> queryPageList(HelpAppraiseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HelpAppraise> lqw = buildQueryWrapper(bo);
        Page<HelpAppraiseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询帮扶学生评价列表
     */
    @Override
    public List<HelpAppraiseVo> queryList(HelpAppraiseBo bo) {
        LambdaQueryWrapper<HelpAppraise> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HelpAppraise> buildQueryWrapper(HelpAppraiseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HelpAppraise> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getHelpId() != null, HelpAppraise::getHelpId, bo.getHelpId());
        lqw.eq(bo.getTeacherId() != null, HelpAppraise::getTeacherId, bo.getTeacherId());
        lqw.eq(bo.getScore() != null, HelpAppraise::getScore, bo.getScore());
        lqw.eq(StringUtils.isNotBlank(bo.getAppraise()), HelpAppraise::getAppraise, bo.getAppraise());
        return lqw;
    }

    /**
     * 新增帮扶学生评价
     */
    @Override
    public Boolean insertByBo(HelpAppraiseBo bo) {
        HelpAppraise add = BeanUtil.toBean(bo, HelpAppraise.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改帮扶学生评价
     */
    @Override
    public Boolean updateByBo(HelpAppraiseBo bo) {
        HelpAppraise update = BeanUtil.toBean(bo, HelpAppraise.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HelpAppraise entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除帮扶学生评价
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


}




