package com.ruoyi.help.help.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.classes.api.RemoteClassService;
import com.ruoyi.classes.api.domain.vo.ClassesVo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.help.api.domain.Help;
import com.ruoyi.help.api.domain.bo.HelpBo;
import com.ruoyi.help.api.domain.vo.HelpVo;
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
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public R<T> insertByBo(HelpBo bo) {
        Long teacherId = bo.getTeacherId();
        Long classId = bo.getClassId();
        String name = bo.getName();
        String email = bo.getEmail();
        String tel = bo.getTel();
        String errNote = bo.getErrNote();
        Date startDate = bo.getStartDate();
        //校验输入姓名
        if (StringUtils.isEmpty(name.trim())){
            return R.fail("姓名输入异常！请确认后再输入！");
        }
        //校验邮箱格式
        String trimEmail = email.trim();
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (StringUtils.isEmpty(trimEmail) || !trimEmail.matches(emailRegex)){
           return R.fail("邮箱格式异常！请确认后再输入");
        }
        //校验电话
        String trimTel = tel.trim();
        if (StringUtils.isEmpty(trimTel) || trimTel.length() != 11){
            return R.fail("电话输入异常，请确认后再输入");
        }
        //校验预约时间
        Date date = new Date();
        if (startDate.compareTo(date) >= 3){
            return R.fail("预约时间不得小于3天");
        }
        //校验问题描述
        String trimErrNote = errNote.trim();
        if (StringUtils.isEmpty(trimErrNote)){
            return R.fail("问题描述异常！请确认后再输入！");
        }
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
        bo.setDealStatus(1);
        Help add = BeanUtil.toBean(bo, Help.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        StudentVo studentVo = remoteStudentService.queryByName(name);
        if (studentVo == null){
            studentBo.setName(name);
            studentBo.setEmail(email);
            studentBo.setTel(tel);
            remoteStudentService.insertByBo(studentBo);
        }
        Long teacherId1 = add.getTeacherId();
        String teacherEmail = remoteTeacherService.queryById(teacherId1).getEmail();
        String subject = "学员"+add.getName()+"需要你的帮助";
        String text = "具体情况请登录帮扶管理后台查看 http://localhost/help/help";
        EmailUtil.sendEmail(teacherEmail,subject,text);
        return R.ok();
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

    /**
     * 通过老师id查询列表
     */
    @Override
    public List<HelpVo> listByTeacherId() {
        Long userId = LoginHelper.getUserId();
        TeacherVo teacherVo = remoteTeacherService.queryByUserId(userId);
        Long id = teacherVo.getId();
        LambdaQueryWrapper<Help> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Help::getTeacherId,id).and(w->w.eq(Help::getDealStatus,1).or().eq(Help::getDealStatus,4));
        return baseMapper.selectVoList(wrapper, HelpVo.class);
    }



}
