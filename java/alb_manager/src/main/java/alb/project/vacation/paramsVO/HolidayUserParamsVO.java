package alb.project.vacation.paramsVO;

import lombok.*;

/**
 * @author HaoHao
 * Created on 2021/11/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayUserParamsVO {

    /**
     * departmentID
     */
    private Long deptId;

    /**
     * roleID
     */
    private Long roleId;
}
