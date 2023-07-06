package com.ruoyi.classes.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 班级管理对象 help_class
 *
 * @author meizhouwu
 * @date 2023-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("help_class")
public class Classes extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 班级名
     */
    private String name;
    /**
     * 开班日期
     */
    private Date startClass;
    /**
     * 结课日期
     */
    private Date endClass;

    /**
     * 讲师
     */
    private Long talkTeacher;

    /**
     * 班主任
     */
    private Long teacher;
    /**
     * 就业老师
     */
    private Long employmentTeacher;
    /**
     * 助教老师
     */
    private Long helpTeacher;

    /**
     * 技术老师
     */
    private Long techTeacher;

}
