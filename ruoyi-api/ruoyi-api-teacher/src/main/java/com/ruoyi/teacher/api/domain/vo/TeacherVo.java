package com.ruoyi.teacher.api.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;

import java.io.Serializable;


/**
 * 老师管理视图对象
 *
 * @author meizhouwu
 * @date 2023-06-28
 */
@Data
@ExcelIgnoreUnannotated
public class TeacherVo implements Serializable {

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
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String tel;

    /**
     * 帮扶优先级
     */
    @ExcelProperty(value = "帮扶优先级", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "help_teacher_priority")
    private Integer priority;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "help_teacher_type")
    private Integer type;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private Integer sex;
    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;


    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;


}
