package com.ruoyi.help.help.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.classes.api.RemoteClassService;
import com.ruoyi.classes.api.domain.vo.ClassesVo;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.help.domain.Help;
import com.ruoyi.help.help.domain.bo.HelpBo;
import com.ruoyi.help.help.domain.vo.HelpVo;
import com.ruoyi.help.help.mapper.HelpMapper;
import com.ruoyi.help.help.service.IHelpService;
import com.ruoyi.help.help.utils.EmailUtil;
import com.ruoyi.resource.api.RemoteMailService;
import com.ruoyi.student.api.RemoteStudentService;
import com.ruoyi.student.api.domain.bo.StudentBo;
import com.ruoyi.student.api.domain.vo.StudentVo;
import com.ruoyi.teacher.api.RemoteTeacherService;
import com.ruoyi.teacher.api.domain.vo.TeacherVo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 帮扶管理Service业务层处理
 *
 * @author meizhouwu
 * @date 2023-07-03
 */
@RequiredArgsConstructor
@Service
public class HelpServiceImpl implements IHelpService {

    private final HelpMapper baseMapper;

    @DubboReference
    private RemoteTeacherService remoteTeacherService;

    @DubboReference
    private RemoteClassService remoteClassService;

    @DubboReference
    private RemoteStudentService remoteStudentService;

    @DubboReference
    private RemoteMailService remoteMailService;



    /**
     * 查询帮扶管理
     */
    @Override
    public HelpVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询帮扶管理列表
     */
    @Override
    public TableDataInfo<HelpVo> queryPageList(HelpBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Help> lqw = buildQueryWrapper(bo);
        Page<HelpVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询帮扶管理列表
     */
    @Override
    public List<HelpVo> queryList(HelpBo bo) {
        LambdaQueryWrapper<Help> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Help> buildQueryWrapper(HelpBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Help> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Help::getName, bo.getName());
        lqw.eq(bo.getClassId() != null, Help::getClassId, bo.getClassId());
        lqw.eq(bo.getErrType() != null, Help::getErrType, bo.getErrType());
        lqw.eq(bo.getDealStatus() != null, Help::getDealStatus, bo.getDealStatus());
        return lqw;
    }

    /**
     * 新增帮扶管理
     */
    @Override
    public Boolean insertByBo(HelpBo bo) {
        Long teacherId = bo.getTeacherId();
        Long classId = bo.getClassId();
        String name = bo.getName();
        StudentBo studentBo = new StudentBo();
        if (teacherId == null){
            if (classId != null){
                ClassesVo classesVo = remoteClassService.queryById(classId);
                Long techTeacher = classesVo.getTechTeacher();
                bo.setTeacherId(techTeacher);
                studentBo.setClassId(classId);
            } else {
                List<TeacherVo> teacherVos = remoteTeacherService.tecTeacherList();
                int i = new Random().nextInt(teacherVos.size());
                Long id = teacherVos.get(i).getId();
                bo.setTeacherId(id);
            }
        }
        Help add = BeanUtil.toBean(bo, Help.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        StudentVo studentVo = remoteStudentService.queryByName(name);
        if (studentVo == null){
            studentBo.setName(name);
            studentBo.setEmail(bo.getEmail());
            studentBo.setTel(bo.getTel());
            remoteStudentService.insertByBo(studentBo);
        }
        Long teacherId1 = add.getTeacherId();
        String email = remoteTeacherService.queryById(teacherId1).getEmail();
        String subject = "学员"+add.getName()+"需要你的帮助";
        String text = "具体情况请登录帮扶管理后台查看 http://localhost/help/help";
        EmailUtil.sendEmail(email,subject,text);
        return flag;
    }

    /**
     * 修改帮扶管理
     */
    @Override
    public Boolean updateByBo(HelpBo bo) {
        Help update = BeanUtil.toBean(bo, Help.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Help entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除帮扶管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
