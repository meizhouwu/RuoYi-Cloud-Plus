package com.ruoyi.help.api.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 帮扶报表中心视图对象
 *
 * @author meizhouwu
 * @date 2023-07-15
 */
@Data
@ExcelIgnoreUnannotated
public class HelpReportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 老师id
     */
    @ExcelProperty(value = "老师id")
    private Long teacherId;

    /**
     * 帮扶数量
     */
    @ExcelProperty(value = "帮扶数量")
    private Integer helpCount;

    /**
     * 平均分数
     */
    @ExcelProperty(value = "平均分数")
    private Double averageScore;

    /**
     * 报表创建时间
     */
    @ExcelProperty(value = "报表创建时间")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date time;


}
