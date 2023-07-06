package com.ruoyi.student.api.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 学生管理业务对象
 *
 * @author meizhouwu
 * @date 2023-06-30
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 名字
     */
    @NotBlank(message = "名字不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer sex;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer age;

    /**
     * 学历
     */
    @NotNull(message = "学历不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer edu;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    @NotNull(message = "班级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long classId;

    /**
     * 学校
     */
    private String school;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    @NotBlank(message = "电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tel;

    /**
     * 父母电话
     */
    @NotBlank(message = "父母电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String familyTel;


}
