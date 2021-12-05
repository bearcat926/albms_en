package alb.project.vacation.domain;

import alb.framework.web.domain.BaseEntity;
import alb.project.vacation.utils.LongJsonDeserializer;
import alb.project.vacation.utils.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.Date;

/**
 * @author HaoHao
 * Created on 2021/10/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class HolidayItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /* The primary key holiday */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long holidayId;

    /* The approverId */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long approvedUserId;
    private String approvedUserName;

    /* Approval number from1Start counting */
    private Integer approvedIndex;

    /* The examination and approval status 0untreated,1through,2rejected */
    private Integer status;

    /* The examination and approval time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    /* Instructions for examination and approval of */
    private String approveInstruction;

    /**
     * Delete the state0not1has
     */
    private Integer delFlag;
}
