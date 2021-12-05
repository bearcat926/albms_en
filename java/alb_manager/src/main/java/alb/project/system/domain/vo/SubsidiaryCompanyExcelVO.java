package alb.project.system.domain.vo;

import alb.framework.aspectj.lang.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName DeptExcelVO
 * @Description Department Import and Export
 * @Date 2020/7/29 19:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubsidiaryCompanyExcelVO implements Serializable {

    /** Department code */
    @Excel(name = "Department code")
    private String deptCode;

    /** Department name */
    @Excel(name = "Department name")
    private String deptName;

    /** head */
    @Excel(name = "head")
    private String leader;

    /** Contact phone number */
    @Excel(name = "Contact phone number")
    private String phone;

    /** email */
    @Excel(name = "email")
    private String email;

    /** province .*/
    @Excel(name = "province")
    private String province;

    /** The city .*/
    @Excel(name = "The city")
    private String city;

    /** area .*/
    @Excel(name = "area")
    private String district;

    /** Detailed address .*/
    @Excel(name = "Detailed address")
    private String address;

    /** According to the order */
    @Excel(name = "According to the order(Default from small to large)")
    private Integer orderNum;

    @Excel(name = "Creation time",dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

}
