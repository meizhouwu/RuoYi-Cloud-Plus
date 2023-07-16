package com.ruoyi.help.help.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.help.api.domain.HelpAppraise;
import com.ruoyi.help.help.mapper.HelpAppraiseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HelpAppraiseServiceImplTest {


    @Autowired
    private HelpAppraiseMapper helpAppraiseMapper;

    @Test
    public void report() {
        QueryWrapper<HelpAppraise> wrapper = new QueryWrapper<>();
        wrapper.groupBy("teacher_id");
        System.out.println(helpAppraiseMapper.selectList(wrapper));


    }
}
