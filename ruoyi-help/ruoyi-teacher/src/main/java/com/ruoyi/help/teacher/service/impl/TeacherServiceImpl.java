package com.ruoyi.help.teacher.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.teacher.domain.Teacher;
import com.ruoyi.help.teacher.domain.bo.TeacherBo;
import com.ruoyi.help.teacher.domain.vo.TeacherVo;
import com.ruoyi.help.teacher.mapper.TeacherMapper;
import com.ruoyi.help.teacher.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 老师管理Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-06-28
 */
@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherMapper baseMapper;

    /**
     * 查询老师管理
     */
    @Override
    public TeacherVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询老师管理列表
     */
    @Override
    public TableDataInfo<TeacherVo> queryPageList(TeacherBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Teacher> lqw = buildQueryWrapper(bo);
        Page<TeacherVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询老师管理列表
     */
    @Override
    public List<TeacherVo> queryList(TeacherBo bo) {
        LambdaQueryWrapper<Teacher> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Teacher> buildQueryWrapper(TeacherBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Teacher> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Teacher::getName, bo.getName());
        lqw.eq(bo.getType() != null, Teacher::getType, bo.getType());
        return lqw;
    }

    /**
     * 新增老师管理
     */
    @Override
    public Boolean insertByBo(TeacherBo bo) {
        Teacher add = BeanUtil.toBean(bo, Teacher.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改老师管理
     */
    @Override
    public Boolean updateByBo(TeacherBo bo) {
        Teacher update = BeanUtil.toBean(bo, Teacher.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Teacher entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除老师管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 查询所有班主任
     */
    @Override
    public List<TeacherVo> teacherList() {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getType,3);
        List<TeacherVo> teacherVos = baseMapper.selectVoList(wrapper, TeacherVo.class);
        return teacherVos;
    }

    @Override
    public List<TeacherVo> empTeacherList() {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getType,4);
        List<TeacherVo> teacherVos = baseMapper.selectVoList(wrapper, TeacherVo.class);
        return teacherVos;
    }

    @Override
    public List<TeacherVo> helpTeacherList() {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getType,5);
        List<TeacherVo> teacherVos = baseMapper.selectVoList(wrapper, TeacherVo.class);
        return teacherVos;
    }
}
