package com.ruoyi.teacher.api;


import com.ruoyi.teacher.api.domain.TeacherDto;
import com.ruoyi.teacher.api.domain.vo.TeacherVo;

import java.util.List;

/**
 * 老师服务
 *
 * @author Lion Li
 */
public interface RemoteTeacherService {
    void addTeacher(TeacherDto teacherDto);

    List<TeacherVo> teacherList();

    List<TeacherVo> empTeacherList();

    List<TeacherVo> helpTeacherList();

    List<TeacherVo> tecTeacherList();

    List<TeacherVo> talkTeacherList();

    /**
     * 查询老师管理
     */
    TeacherVo queryById(Long id);

    TeacherVo queryByUserId(Long id);
}
