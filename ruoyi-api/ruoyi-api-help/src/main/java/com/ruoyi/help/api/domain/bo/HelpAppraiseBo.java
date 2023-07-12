package com.ruoyi.help.api.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 帮扶学生评价业务对象
 *
 * @author meizhouwu
 * @date 2023-07-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HelpAppraiseBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 帮扶表id
     */
    @NotNull(message = "帮扶表id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long helpId;

    /**
     * 老师id
     */
    @NotNull(message = "老师id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teacherId;

    /**
     * 打分
     */
    @NotNull(message = "打分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer score;

    /**
     * 评价
     */
    @NotBlank(message = "评价不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appraise;


}
