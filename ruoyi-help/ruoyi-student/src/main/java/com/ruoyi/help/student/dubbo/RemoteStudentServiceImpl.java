package com.ruoyi.help.student.dubbo;


import com.ruoyi.help.student.service.IStudentService;
import com.ruoyi.student.api.RemoteStudentService;
import com.ruoyi.student.api.domain.bo.StudentBo;
import com.ruoyi.student.api.domain.vo.StudentVo;
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
public class RemoteStudentServiceImpl implements RemoteStudentService {

    private final IStudentService studentService;


    @Override
    public StudentVo queryByName(String name) {
        return studentService.queryByName(name);
    }

    @Override
    public void insertByBo(StudentBo bo) {
        studentService.insertByBo(bo);
    }
}
