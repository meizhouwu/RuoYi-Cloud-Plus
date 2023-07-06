package com.ruoyi.classes.api.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 班级管理业务对象
 *
 * @author meizhouwu
 * @date 2023-06-29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassesBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 班级名
     */
    @NotBlank(message = "班级名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 开班日期
     */
    @NotNull(message = "开班日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startClass;

    /**
     * 结课日期
     */
    @NotNull(message = "结课日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endClass;

    /**
     * 班主任
     */
    @NotNull(message = "班主任不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teacher;

    /**
     * 就业老师
     */
    @NotNull(message = "就业老师不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long employmentTeacher;

    /**
     * 助教老师
     */
    @NotNull(message = "助教老师不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long helpTeacher;


    /**
     * 技术老师
     */
    @NotNull(message = "技术老师不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long techTeacher;


    /**
     * 讲师
     */
    @NotNull(message = "讲师不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long talkTeacher;

}
