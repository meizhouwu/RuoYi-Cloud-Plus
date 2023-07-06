package com.ruoyi.student.api;


import com.ruoyi.student.api.domain.bo.StudentBo;
import com.ruoyi.student.api.domain.vo.StudentVo;

/**
 * 老师服务
 *
 * @author Lion Li
 */
public interface RemoteStudentService {

    /**
     * 通过姓名查询学生
     */
    StudentVo queryByName(String name);


    void insertByBo(StudentBo bo);
}
