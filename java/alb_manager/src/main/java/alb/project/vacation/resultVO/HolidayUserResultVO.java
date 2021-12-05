package alb.project.vacation.resultVO;

import lombok.*;

/**
 * @author HaoHao
 * Created on 2021/11/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayUserResultVO {

    /**
     * The userID
     */
    private Long userId;

    /**
     * The user nickname(Work number)
     */
    private String displayName;

}
