package com.ruoyi.help.api.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 帮扶报表中心业务对象
 *
 * @author meizhouwu
 * @date 2023-07-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HelpReportBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 老师id
     */
    @NotNull(message = "老师id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teacherId;

    /**
     * 帮扶数量
     */
    @NotNull(message = "帮扶数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer helpCount;

    /**
     * 平均分数
     */
    @NotNull(message = "平均分数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Double averageScore;

    /**
     * 报表创建时间
     */
//    @NotNull(message = "报表创建时间不能为空", groups = { AddGroup.class, EditGroup.class })

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;


}
