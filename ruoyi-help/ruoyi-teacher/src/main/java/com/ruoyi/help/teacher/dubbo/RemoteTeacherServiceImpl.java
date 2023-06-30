package com.ruoyi.help.teacher.dubbo;


import com.ruoyi.help.teacher.domain.bo.TeacherBo;
import com.ruoyi.help.teacher.service.ITeacherService;
import com.ruoyi.teacher.api.RemoteTeacherService;
import com.ruoyi.teacher.api.domain.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

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

        TeacherBo teacher = new TeacherBo();
        teacher.setUserId(userId);
        teacher.setName(name);
        teacher.setTel(tel);
        teacher.setSex(sex);
        teacherService.insertByBo(teacher);
    }
}
