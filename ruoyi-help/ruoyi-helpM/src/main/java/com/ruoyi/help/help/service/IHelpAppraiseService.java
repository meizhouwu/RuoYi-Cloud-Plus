package com.ruoyi.help.help.service;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.bo.HelpAppraiseBo;
import com.ruoyi.help.api.domain.vo.HelpAppraiseVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;
import java.util.List;

/**
 * 帮扶学生评价Service接口
 *
 * @author meizhouwu
 * @date 2023-07-10
 */
public interface IHelpAppraiseService {

    /**
     * 查询帮扶学生评价
     */
    HelpAppraiseVo queryById(Long id);

    /**
     * 查询帮扶学生评价列表
     */
    TableDataInfo<HelpAppraiseVo> queryPageList(HelpAppraiseBo bo, PageQuery pageQuery);

    /**
     * 查询帮扶学生评价列表
     */
    List<HelpAppraiseVo> queryList(HelpAppraiseBo bo);

    /**
     * 修改帮扶学生评价
     */
    R<T> insertByBo(HelpAppraiseBo bo);

    /**
     * 修改帮扶学生评价
     */
    Boolean updateByBo(HelpAppraiseBo bo);

    /**
     * 校验并批量删除帮扶学生评价信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}
