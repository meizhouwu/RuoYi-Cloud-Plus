package com.ruoyi.help.classes.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.classes.api.domain.Classes;
import com.ruoyi.classes.api.domain.bo.ClassesBo;
import com.ruoyi.classes.api.domain.vo.ClassesVo;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.classes.mapper.ClassesMapper;
import com.ruoyi.help.classes.service.IClassesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 班级管理Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-06-29
 */
@RequiredArgsConstructor
@Service
public class ClassesServiceImpl implements IClassesService {

    private final ClassesMapper baseMapper;

    /**
     * 查询班级管理
     */
    @Override
    public ClassesVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询班级管理列表
     */
    @Override
    public TableDataInfo<ClassesVo> queryPageList(ClassesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Classes> lqw = buildQueryWrapper(bo);
        Page<ClassesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询班级管理列表
     */
    @Override
    public List<ClassesVo> queryList(ClassesBo bo) {
        LambdaQueryWrapper<Classes> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Classes> buildQueryWrapper(ClassesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Classes> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Classes::getName, bo.getName());
        lqw.eq(bo.getTeacher() != null, Classes::getTeacher, bo.getTeacher());
        lqw.eq(bo.getEmploymentTeacher() != null, Classes::getEmploymentTeacher, bo.getEmploymentTeacher());
        lqw.eq(bo.getHelpTeacher() != null, Classes::getHelpTeacher, bo.getHelpTeacher());
        lqw.eq(bo.getTalkTeacher() != null, Classes::getTalkTeacher, bo.getTalkTeacher());
        lqw.eq(bo.getTechTeacher() != null, Classes::getTechTeacher, bo.getTechTeacher());
        return lqw;
    }

    /**
     * 新增班级管理
     */
    @Override
    public Boolean insertByBo(ClassesBo bo) {
        Classes add = BeanUtil.toBean(bo, Classes.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改班级管理
     */
    @Override
    public Boolean updateByBo(ClassesBo bo) {
        Classes update = BeanUtil.toBean(bo, Classes.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Classes entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除班级管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


}
