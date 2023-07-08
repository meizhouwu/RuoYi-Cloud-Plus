package com.ruoyi.help.api.domain.bo;

import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 帮扶管理业务对象
 *
 * @author meizhouwu
 * @date 2023-07-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HelpBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生邮箱
     */
    private String email;

    /**
     * 学生电话
     */
    private String tel;

    /**
     * 班级
     */
    private Long classId;

    /**
     * 预约时间
     */
    private Date startDate;

    /**
     * 帮扶老师
     */
    private Long teacherId;

    /**
     * 问题类型
     */
    private Integer errType;

    /**
     * 问题描述
     */
    private String errNote;

    /**
     * 处理状态
     */
    private Integer dealStatus;

    /**
     * 老师备注
     */
    private String teacherNote;

    /**
     * 完成时间
     */
    private Date endDate;


}
