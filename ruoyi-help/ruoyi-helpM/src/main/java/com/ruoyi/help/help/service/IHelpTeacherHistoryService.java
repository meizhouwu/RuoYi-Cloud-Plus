package com.ruoyi.help.help.service;


import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.bo.HelpTeacherHistoryBo;
import com.ruoyi.help.api.domain.vo.HelpTeacherHistoryVo;

import java.util.Collection;
import java.util.List;

/**
 * 帮扶移交历史Service接口
 *
 * @author meizhouwu
 * @date 2023-07-09
 */
public interface IHelpTeacherHistoryService {

    /**
     * 查询帮扶移交历史
     */
    HelpTeacherHistoryVo queryById(Long id);

    /**
     * 查询帮扶移交历史列表
     */
    TableDataInfo<HelpTeacherHistoryVo> queryPageList(HelpTeacherHistoryBo bo, PageQuery pageQuery);

    /**
     * 查询帮扶移交历史列表
     */
    List<HelpTeacherHistoryVo> queryList(HelpTeacherHistoryBo bo);

    /**
     * 修改帮扶移交历史
     */
    Boolean insertByBo(HelpTeacherHistoryBo bo);

    /**
     * 修改帮扶移交历史
     */
    Boolean updateByBo(HelpTeacherHistoryBo bo);

    /**
     * 校验并批量删除帮扶移交历史信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
