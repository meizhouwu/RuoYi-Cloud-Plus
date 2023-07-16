package com.ruoyi.help.help.service;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.bo.HelpReportBo;
import com.ruoyi.help.api.domain.vo.HelpReportVo;

import java.util.Collection;
import java.util.List;

/**
 * 帮扶报表中心Service接口
 *
 * @author meizhouwu
 * @date 2023-07-15
 */
public interface IHelpReportService {

    /**
     * 查询帮扶报表中心
     */
    HelpReportVo queryById(Long id);

    /**
     * 查询帮扶报表中心列表
     */
    TableDataInfo<HelpReportVo> queryPageList(HelpReportBo bo, PageQuery pageQuery);

    /**
     * 查询帮扶报表中心列表
     */
    List<HelpReportVo> queryList(HelpReportBo bo);

    /**
     * 修改帮扶报表中心
     */
    Boolean insertByBo(HelpReportBo bo);

    /**
     * 修改帮扶报表中心
     */
    Boolean updateByBo(HelpReportBo bo);

    /**
     * 校验并批量删除帮扶报表中心信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    R report();
}
