package alb.project.vacation.domain;

import alb.framework.web.domain.BaseEntity;
import alb.project.vacation.utils.LongJsonDeserializer;
import alb.project.vacation.utils.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class HolidayApproval extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /* Vacation approvalID */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long holidayApprovalId;

    /* Holiday types */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long holidayTypeId;

    private String typeName;

    private String typeValue;

    /* Role of the person awaiting approvalID */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long roleId;

    private String roleName;

    /* Approver roleID */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long approvedRoleId;

    private String approvedRoleName;

    /* Current Approval Number from1Start counting */
    private Integer currentApprovedIndex;

    /* The role of the next level of approvalID,If it is the last level of approval0 */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long nextApprovalId;

}
