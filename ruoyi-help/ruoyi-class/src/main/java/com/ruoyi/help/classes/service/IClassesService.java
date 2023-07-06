package com.ruoyi.help.classes.service;

import com.ruoyi.classes.api.domain.bo.ClassesBo;
import com.ruoyi.classes.api.domain.vo.ClassesVo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 班级管理Service接口
 *
 * @author meizhouwu
 * @date 2023-06-29
 */
public interface IClassesService {

    /**
     * 查询班级管理
     */
    ClassesVo queryById(Long id);

    /**
     * 查询班级管理列表
     */
    TableDataInfo<ClassesVo> queryPageList(ClassesBo bo, PageQuery pageQuery);

    /**
     * 查询班级管理列表
     */
    List<ClassesVo> queryList(ClassesBo bo);

    /**
     * 修改班级管理
     */
    Boolean insertByBo(ClassesBo bo);

    /**
     * 修改班级管理
     */
    Boolean updateByBo(ClassesBo bo);

    /**
     * 校验并批量删除班级管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}
