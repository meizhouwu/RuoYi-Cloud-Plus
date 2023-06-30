package com.ruoyi.help.student.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;



/**
 * 学生管理视图对象
 *
 * @author meizhouwu
 * @date 2023-06-30
 */
@Data
@ExcelIgnoreUnannotated
public class StudentVo {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 名字
     */
    @ExcelProperty(value = "名字")
    private String name;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private Integer sex;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "help_student_edu")
    private Integer edu;

    /**
     * 专业
     */
    @ExcelProperty(value = "专业")
    private String major;

    /**
     * 班级
     */
    @ExcelProperty(value = "班级")
    private Long classId;

    /**
     * 学校
     */
    @ExcelProperty(value = "学校")
    private String school;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String tel;

    /**
     * 父母电话
     */
    @ExcelProperty(value = "父母电话")
    private String familyTel;


}
