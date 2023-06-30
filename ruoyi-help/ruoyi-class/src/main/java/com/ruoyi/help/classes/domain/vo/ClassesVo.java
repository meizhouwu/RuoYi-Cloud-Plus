package com.ruoyi.help.classes.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;



/**
 * 班级管理视图对象
 *
 * @author meizhouwu
 * @date 2023-06-29
 */
@Data
@ExcelIgnoreUnannotated
public class ClassesVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 班级名
     */
    @ExcelProperty(value = "班级名")
    private String name;

    /**
     * 开班日期
     */
    @ExcelProperty(value = "开班日期")
    private Date startClass;

    /**
     * 结课日期
     */
    @ExcelProperty(value = "结课日期")
    private Date endClass;

    /**
     * 班主任
     */
    @ExcelProperty(value = "班主任")
    private Long teacher;

    /**
     * 就业老师
     */
    @ExcelProperty(value = "就业老师")
    private Long employmentTeacher;

    /**
     * 助教老师
     */
    @ExcelProperty(value = "助教老师")
    private Long helpTeacher;


}
