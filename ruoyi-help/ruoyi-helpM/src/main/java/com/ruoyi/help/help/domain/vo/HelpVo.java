package com.ruoyi.help.help.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 帮扶管理视图对象
 *
 * @author meizhouwu
 * @date 2023-07-03
 */
@Data
@ExcelIgnoreUnannotated
public class HelpVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 学生姓名
     */
    @ExcelProperty(value = "学生姓名")
    private String name;

    /**
     * 学生邮箱
     */
    @ExcelProperty(value = "学生邮箱")
    private String email;

    /**
     * 学生电话
     */
    @ExcelProperty(value = "学生电话")
    private String tel;

    /**
     * 班级
     */
    @ExcelProperty(value = "班级")
    private Long classId;

    /**
     * 预约时间
     */
    @ExcelProperty(value = "预约时间")
    private Date startDate;

    /**
     * 帮扶老师
     */
    @ExcelProperty(value = "帮扶老师")
    private Long teacherId;

    /**
     * 问题类型
     */
    @ExcelProperty(value = "问题类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "help_student_err")
    private Integer errType;

    /**
     * 问题描述
     */
    @ExcelProperty(value = "问题描述")
    private String errNote;

    /**
     * 处理状态
     */
    @ExcelProperty(value = "处理状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "help_status")
    private Integer dealStatus;

    /**
     * 老师备注
     */
    @ExcelProperty(value = "老师备注")
    private String teacherNote;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private Date endDate;


}
