package com.ruoyi.help.api.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 帮扶移交历史视图对象
 *
 * @author meizhouwu
 * @date 2023-07-09
 */
@Data
@ExcelIgnoreUnannotated
public class HelpTeacherHistoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 旧老师
     */
    @ExcelProperty(value = "旧老师")
    private Long oldId;

    /**
     * 新老师
     */
    @ExcelProperty(value = "新老师")
    private Long newId;

    /**
     * 帮扶表id
     */
    @ExcelProperty(value = "帮扶表id")
    private Long helpId;

    /**
     * 移交备注
     */
    @ExcelProperty(value = "移交备注")
    private String note;

    /**
     * 移交时间
     */
    @ExcelProperty(value = "移交时间")
    private Date changeTime;


}
