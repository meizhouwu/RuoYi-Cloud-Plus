package com.ruoyi.help.help.service;

import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.help.domain.bo.HelpBo;
import com.ruoyi.help.help.domain.vo.HelpVo;

import java.util.Collection;
import java.util.List;

/**
 * 帮扶管理Service接口
 *
 * @author meizhouwu
 * @date 2023-07-03
 */
public interface IHelpService {

    /**
     * 查询帮扶管理
     */
    HelpVo queryById(Long id);

    /**
     * 查询帮扶管理列表
     */
    TableDataInfo<HelpVo> queryPageList(HelpBo bo, PageQuery pageQuery);

    /**
     * 查询帮扶管理列表
     */
    List<HelpVo> queryList(HelpBo bo);

    /**
     * 修改帮扶管理
     */
    Boolean insertByBo(HelpBo bo);

    /**
     * 修改帮扶管理
     */
    Boolean updateByBo(HelpBo bo);

    /**
     * 校验并批量删除帮扶管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
