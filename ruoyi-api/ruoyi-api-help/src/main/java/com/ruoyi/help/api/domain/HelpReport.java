package com.ruoyi.help.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 帮扶报表中心对象 help_help_report
 *
 * @author meizhouwu
 * @date 2023-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("help_help_report")
public class HelpReport extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 老师id
     */
    private Long teacherId;
    /**
     * 帮扶数量
     */
    private Integer helpCount;
    /**
     * 平均分数
     */
    private Double averageScore;
    /**
     * 报表创建时间
     */
    private Date time = new Date();


}
