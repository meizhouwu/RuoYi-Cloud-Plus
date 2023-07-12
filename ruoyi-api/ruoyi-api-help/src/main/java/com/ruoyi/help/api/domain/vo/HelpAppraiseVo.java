package com.ruoyi.help.api.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 帮扶学生评价视图对象
 *
 * @author meizhouwu
 * @date 2023-07-10
 */
@Data
@ExcelIgnoreUnannotated
public class HelpAppraiseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 帮扶表id
     */
    @ExcelProperty(value = "帮扶表id")
    private Long helpId;

    /**
     * 老师id
     */
    @ExcelProperty(value = "老师id")
    private Long teacherId;

    /**
     * 打分
     */
    @ExcelProperty(value = "打分")
    private Integer score;

    /**
     * 评价
     */
    @ExcelProperty(value = "评价")
    private String appraise;


}
