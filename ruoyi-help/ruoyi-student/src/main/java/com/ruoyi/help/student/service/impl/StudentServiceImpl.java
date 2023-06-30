package com.ruoyi.help.student.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.help.student.domain.bo.StudentBo;
import com.ruoyi.help.student.domain.vo.StudentVo;
import com.ruoyi.help.student.domain.Student;
import com.ruoyi.help.student.mapper.StudentMapper;
import com.ruoyi.help.student.service.IStudentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 学生管理Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-06-30
 */
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements IStudentService {

    private final StudentMapper baseMapper;

    /**
     * 查询学生管理
     */
    @Override
    public StudentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询学生管理列表
     */
    @Override
    public TableDataInfo<StudentVo> queryPageList(StudentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Student> lqw = buildQueryWrapper(bo);
        Page<StudentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询学生管理列表
     */
    @Override
    public List<StudentVo> queryList(StudentBo bo) {
        LambdaQueryWrapper<Student> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Student> buildQueryWrapper(StudentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Student> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Student::getName, bo.getName());
        lqw.eq(bo.getSex() != null, Student::getSex, bo.getSex());
        lqw.eq(bo.getEdu() != null, Student::getEdu, bo.getEdu());
        lqw.eq(bo.getClassId() != null, Student::getClassId, bo.getClassId());
        return lqw;
    }

    /**
     * 新增学生管理
     */
    @Override
    public Boolean insertByBo(StudentBo bo) {
        Student add = BeanUtil.toBean(bo, Student.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改学生管理
     */
    @Override
    public Boolean updateByBo(StudentBo bo) {
        Student update = BeanUtil.toBean(bo, Student.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Student entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除学生管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
