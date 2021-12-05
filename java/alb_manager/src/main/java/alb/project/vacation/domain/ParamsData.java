package alb.project.vacation.domain;

import alb.framework.aspectj.lang.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Parameter Setting object params_data
 *
 * @date 2020-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParamsData implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** The name */
    @Excel(name = "The name")
    private String name;

    /** value */
    @Excel(name = "value")
    private String value;

    /** The parameter types(1Proportion of down payment2Inventory alarm setting3Operator return call time4Time for optometrist return visit5Make an appointment to source6Image of login page7Technical Customer Service Telephone8Performance appraisal standard of branch company9Store Assessment Standard10Telephone operator assessment standards) */
    @Excel(name = "The parameter types", readConverterExp = "1=Proportion of down payment2Inventory alarm setting3Operator return call time4Time for optometrist return visit5Make an appointment to source6Image of login page7Technical Customer Service Telephone8Performance appraisal standard of branch company9Store Assessment Standard10Telephone operator assessment standards")
    private Integer type;

    /** Front-end grouping */
    private Integer groupType;

    /** Time of last operation */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Time of last operation", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastDate;

}
