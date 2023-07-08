package com.ruoyi.help.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 帮扶移交历史对象 help_help_teacher_history
 *
 * @author meizhouwu
 * @date 2023-07-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("help_help_teacher_history")
public class HelpTeacherHistory extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 旧老师
     */
    private Long oldId;
    /**
     * 新老师
     */
    private Long newId;
    /**
     * 帮扶表id
     */
    private Long helpId;
    /**
     * 移交备注
     */
    private String note;
    /**
     * 移交时间
     */
    private Date changeTime;

}
