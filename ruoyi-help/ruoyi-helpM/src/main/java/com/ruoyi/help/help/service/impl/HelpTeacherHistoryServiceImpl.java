package com.ruoyi.help.help.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.Help;
import com.ruoyi.help.api.domain.HelpTeacherHistory;
import com.ruoyi.help.api.domain.bo.HelpTeacherHistoryBo;
import com.ruoyi.help.api.domain.vo.HelpTeacherHistoryVo;
import com.ruoyi.help.help.mapper.HelpMapper;
import com.ruoyi.help.help.mapper.HelpTeacherHistoryMapper;
import com.ruoyi.help.help.service.IHelpTeacherHistoryService;
import com.ruoyi.help.help.utils.EmailUtil;
import com.ruoyi.teacher.api.RemoteTeacherService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 帮扶移交历史Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-07-09
 */
@RequiredArgsConstructor
@Service
public class HelpTeacherHistoryServiceImpl implements IHelpTeacherHistoryService {

    private final HelpTeacherHistoryMapper baseMapper;


    private final HelpMapper helpMapper;


    @DubboReference
    private RemoteTeacherService remoteTeacherService;

    /**
     * 查询帮扶移交历史
     */
    @Override
    public HelpTeacherHistoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询帮扶移交历史列表
     */
    @Override
    public TableDataInfo<HelpTeacherHistoryVo> queryPageList(HelpTeacherHistoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<HelpTeacherHistory> lqw = buildQueryWrapper(bo);
        Page<HelpTeacherHistoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询帮扶移交历史列表
     */
    @Override
    public List<HelpTeacherHistoryVo> queryList(HelpTeacherHistoryBo bo) {
        LambdaQueryWrapper<HelpTeacherHistory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<HelpTeacherHistory> buildQueryWrapper(HelpTeacherHistoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<HelpTeacherHistory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOldId() != null, HelpTeacherHistory::getOldId, bo.getOldId());
        lqw.eq(bo.getNewId() != null, HelpTeacherHistory::getNewId, bo.getNewId());
        lqw.eq(bo.getHelpId() != null, HelpTeacherHistory::getHelpId, bo.getHelpId());
        lqw.eq(StringUtils.isNotBlank(bo.getNote()), HelpTeacherHistory::getNote, bo.getNote());
        lqw.eq(bo.getChangeTime() != null, HelpTeacherHistory::getChangeTime, bo.getChangeTime());
        return lqw;
    }

    /**
     * 新增帮扶移交历史
     */
    @Override
    public Boolean insertByBo(HelpTeacherHistoryBo bo) {
        HelpTeacherHistory add = BeanUtil.toBean(bo, HelpTeacherHistory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        // 修改帮扶老师id以及状态
        Long helpId = add.getHelpId();
        Long newId = add.getNewId();
        String note = add.getNote();
        Help help = helpMapper.selectById(helpId);
        help.setTeacherId(newId);
        help.setDealStatus(1);
        helpMapper.updateById(help);
        // 发送邮件通知新老师
        String email = remoteTeacherService.queryById(newId).getEmail();
        EmailUtil.sendEmail(email,"帮扶移交通知",note);
        return flag;
    }

    /**
     * 修改帮扶移交历史
     */
    @Override
    public Boolean updateByBo(HelpTeacherHistoryBo bo) {
        HelpTeacherHistory update = BeanUtil.toBean(bo, HelpTeacherHistory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(HelpTeacherHistory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除帮扶移交历史
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
