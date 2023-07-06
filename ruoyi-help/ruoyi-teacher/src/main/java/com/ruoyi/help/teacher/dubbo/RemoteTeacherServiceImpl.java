package com.ruoyi.help.teacher.dubbo;


import com.ruoyi.help.teacher.service.ITeacherService;
import com.ruoyi.teacher.api.RemoteTeacherService;
import com.ruoyi.teacher.api.domain.TeacherDto;
import com.ruoyi.teacher.api.domain.bo.TeacherBo;
import com.ruoyi.teacher.api.domain.vo.TeacherVo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteTeacherServiceImpl implements RemoteTeacherService {

    private final ITeacherService teacherService;


    @Override
    public void addTeacher(TeacherDto teacherDto) {
        Long userId = teacherDto.getUserId();
        String name = teacherDto.getName();
        Integer sex = teacherDto.getSex();
        String tel = teacherDto.getTel();
        String email = teacherDto.getEmail();

        TeacherBo teacher = new TeacherBo();
        teacher.setUserId(userId);
        teacher.setName(name);
        teacher.setTel(tel);
        teacher.setSex(sex);
        teacher.setEmail(email);
        teacherService.insertByBo(teacher);
    }

    @Override
    public List<TeacherVo> teacherList() {
        return teacherService.teacherList();
    }

    @Override
    public List<TeacherVo> empTeacherList() {
        return teacherService.empTeacherList();
    }

    @Override
    public List<TeacherVo> helpTeacherList() {
        return teacherService.helpTeacherList();
    }

    @Override
    public List<TeacherVo> tecTeacherList() {
        return teacherService.techTeacherList();
    }

    @Override
    public List<TeacherVo> talkTeacherList() {
        return teacherService.talkTeacherList();
    }

    @Override
    public TeacherVo queryById(Long id) {
        return teacherService.queryById(id);
    }
}
