package com.ruoyi.help.api.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 帮扶移交历史业务对象
 *
 * @author meizhouwu
 * @date 2023-07-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HelpTeacherHistoryBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 旧老师
     */
    @NotNull(message = "旧老师不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long oldId;

    /**
     * 新老师
     */
    @NotNull(message = "新老师不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long newId;

    /**
     * 帮扶表id
     */
    @NotNull(message = "帮扶表id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long helpId;

    /**
     * 移交备注
     */
    @NotBlank(message = "移交备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String note;

    /**
     * 移交时间
     */
//    @NotNull(message = "移交时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date changeTime;


}
