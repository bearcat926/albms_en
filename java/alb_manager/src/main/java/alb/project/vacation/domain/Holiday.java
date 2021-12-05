package alb.project.vacation.domain;

import alb.framework.web.domain.BaseEntity;
import alb.project.vacation.utils.LongJsonDeserializer;
import alb.project.vacation.utils.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author HaoHao
 * Created on 2021/10/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Holiday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /* The primary key holiday */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long holidayId;

    /* Holiday types */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long holidayTypeId;

    private String typeName;

    private String typeValue;

    /* The vacation time */
    private Double holidayDuration;

    /* Leave that/Instructions for examination and approval of */
    private String holidayInstruction;

    /* Holiday List */
    private List<HolidayItem> items;

    /* The applicantID */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long proposerId;
    private String proposerName;

    /* Current approverID */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long currentApproverId;
    private String currentApproverName;

    /* Current Approval Number from1Start counting */
    private Integer currentApprovedIndex;

    /* Current Approval Status 0In the examination and approval,1through,2rejected */
    private Integer status;

    /**
     * Start time of vacation
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date holidayStartDate;

    /**
     * End of vacation
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date holidayEndDate;

    /**
     * Querying holiday hours - The start time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectStartDate;

    /**
     * Querying holiday hours - The end of time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectEndDate;

    /**
     * Delete the state0not1has
     */
    private Integer delFlag;
}
