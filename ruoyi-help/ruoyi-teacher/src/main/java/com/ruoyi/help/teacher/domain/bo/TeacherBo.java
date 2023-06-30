package com.ruoyi.help.teacher.domain.bo;

import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 老师管理业务对象
 *
 * @author meizhouwu
 * @date 2023-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 电话
     */
    private String tel;

    /**
     * 帮扶优先级
     */
    private Integer priority;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 用户id
     */
    private Long userId;


}
