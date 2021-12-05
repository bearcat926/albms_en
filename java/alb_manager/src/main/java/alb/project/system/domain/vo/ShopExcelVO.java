package alb.project.system.domain.vo;

import alb.framework.aspectj.lang.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ShopExcelVO
 * @Description The export of stores
 * @Date 2020/7/30 9:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopExcelVO implements Serializable {

    @Excel(name = "Number of the subsidiary of the store")
    private String parentDeptCode;

    /** Subsidiaries of stores */
    @Excel(name = "Subsidiaries of stores",type = Excel.Type.EXPORT)
    private String parentDeptName;

    /** Department code */
    @Excel(name = "Department code")
    private String deptCode;

    /** Department name */
    @Excel(name = "Department name")
    private String deptName;

    @Excel(name = "Store type1=Own stores,2=Its franchisees",readConverterExp = "1=Own stores,2=Its franchisees")
    private Integer deptType;

    /** head */
    @Excel(name = "The manager")
    private String leader;

    /** Contact phone number */
    @Excel(name = "The manager phone")
    private String phone;

    /** email */
    @Excel(name = "The manager email account")
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

