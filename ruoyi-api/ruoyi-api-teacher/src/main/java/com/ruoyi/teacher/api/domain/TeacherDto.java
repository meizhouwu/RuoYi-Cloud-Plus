package com.ruoyi.teacher.api.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto extends BaseEntity {

    private static final long serialVersionUID=1L;

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
     * 邮箱
     */
    private String email;


    private Long userId;

}
