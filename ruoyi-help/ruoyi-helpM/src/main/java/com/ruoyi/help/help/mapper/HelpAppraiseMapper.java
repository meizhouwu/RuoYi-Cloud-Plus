package com.ruoyi.help.help.mapper;

import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.help.api.domain.HelpAppraise;
import com.ruoyi.help.api.domain.HelpReport;
import com.ruoyi.help.api.domain.vo.HelpAppraiseVo;

import java.util.List;


/**
 * 帮扶学生评价Mapper接口
 *
 * @author meizhouwu
 * @date 2023-07-10
 */
public interface HelpAppraiseMapper extends BaseMapperPlus<HelpAppraiseMapper, HelpAppraise, HelpAppraiseVo> {

    List<HelpReport> report();
}
