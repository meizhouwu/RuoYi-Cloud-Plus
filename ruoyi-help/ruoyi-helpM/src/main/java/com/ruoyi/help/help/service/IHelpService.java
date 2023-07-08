package com.ruoyi.help.help.service;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.help.api.domain.bo.HelpBo;
import com.ruoyi.help.api.domain.vo.HelpVo;
import org.apache.poi.ss.formula.functions.T;

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
    R<T> insertByBo(HelpBo bo);

    /**
     * 修改帮扶管理
     */
    Boolean updateByBo(HelpBo bo);

    /**
     * 校验并批量删除帮扶管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<HelpVo> listByTeacherId();
}
