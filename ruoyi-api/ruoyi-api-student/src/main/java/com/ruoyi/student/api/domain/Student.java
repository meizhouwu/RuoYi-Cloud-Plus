package com.ruoyi.student.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生管理对象 help_student
 *
 * @author meizhouwu
 * @date 2023-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("help_student")
public class Student extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 学历
     */
    private Integer edu;
    /**
     * 专业
     */
    private String major;
    /**
     * 班级
     */
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
    private String tel;
    /**
     * 父母电话
     */
    private String familyTel;

}
