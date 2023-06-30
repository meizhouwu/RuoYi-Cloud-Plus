package com.ruoyi.help.teacher.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;



/**
 * 老师管理视图对象
 *
 * @author meizhouwu
 * @date 2023-06-28
 */
@Data
@ExcelIgnoreUnannotated
public class TeacherVo {

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
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;


}
