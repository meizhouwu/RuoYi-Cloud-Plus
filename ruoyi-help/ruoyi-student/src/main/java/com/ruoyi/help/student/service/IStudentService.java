package com.ruoyi.help.student.service;

import com.ruoyi.help.student.domain.Student;
import com.ruoyi.help.student.domain.vo.StudentVo;
import com.ruoyi.help.student.domain.bo.StudentBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 学生管理Service接口
 *
 * @author meizhouwu
 * @date 2023-06-30
 */
public interface IStudentService {

    /**
     * 查询学生管理
     */
    StudentVo queryById(Long id);

    /**
     * 查询学生管理列表
     */
    TableDataInfo<StudentVo> queryPageList(StudentBo bo, PageQuery pageQuery);

    /**
     * 查询学生管理列表
     */
    List<StudentVo> queryList(StudentBo bo);

    /**
     * 修改学生管理
     */
    Boolean insertByBo(StudentBo bo);

    /**
     * 修改学生管理
     */
    Boolean updateByBo(StudentBo bo);

    /**
     * 校验并批量删除学生管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
