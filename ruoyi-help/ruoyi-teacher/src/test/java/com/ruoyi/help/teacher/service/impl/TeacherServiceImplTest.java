package com.ruoyi.help.teacher.service.impl;

import com.ruoyi.help.teacher.service.ITeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TeacherServiceImplTest {
    @Autowired
    private ITeacherService teacherService;


    @Test
    public void teacherList() {
        System.out.println(teacherService.teacherList());
        System.out.println(teacherService.empTeacherList());
        System.out.println(teacherService.helpTeacherList());
    }
}
