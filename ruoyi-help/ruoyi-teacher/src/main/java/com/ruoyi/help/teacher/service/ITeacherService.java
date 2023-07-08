package com.ruoyi.help.teacher.service;

import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.teacher.api.domain.bo.TeacherBo;
import com.ruoyi.teacher.api.domain.vo.TeacherVo;

import java.util.Collection;
import java.util.List;

/**
 * 老师管理Service接口
 *
 * @author meizhouwu
 * @date 2023-06-28
 */
public interface ITeacherService {

    /**
     * 查询老师管理
     */
    TeacherVo queryById(Long id);

    /**
     * 查询老师管理列表
     */
    TableDataInfo<TeacherVo> queryPageList(TeacherBo bo, PageQuery pageQuery);

    /**
     * 查询老师管理列表
     */
    List<TeacherVo> queryList(TeacherBo bo);

    /**
     * 修改老师管理
     */
    Boolean insertByBo(TeacherBo bo);

    /**
     * 修改老师管理
     */
    Boolean updateByBo(TeacherBo bo);

    /**
     * 校验并批量删除老师管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询所有班主任
     */
    List<TeacherVo> teacherList();

    List<TeacherVo> empTeacherList();

    List<TeacherVo> helpTeacherList();

    List<TeacherVo> techTeacherList();

    List<TeacherVo> talkTeacherList();


    TeacherVo queryByUserId(Long id);
}
