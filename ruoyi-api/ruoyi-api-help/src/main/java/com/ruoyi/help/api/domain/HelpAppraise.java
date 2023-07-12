package com.ruoyi.help.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帮扶学生评价对象 help_help_appraise
 *
 * @author meizhouwu
 * @date 2023-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("help_help_appraise")
public class HelpAppraise extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 帮扶表id
     */
    private Long helpId;
    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 打分
     */
    private Integer score;
    /**
     * 评价
     */
    private String appraise;

}
