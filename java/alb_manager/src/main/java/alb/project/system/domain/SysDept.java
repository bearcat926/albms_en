package alb.project.system.domain;

import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Department of table sys_dept
 *
 */
public class SysDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** departmentID */
    private Long deptId;

    /** Department code */
    private String deptCode;


    /** The parent departmentID */
    private Long parentId;

    /** Face level list */
    private String ancestors;

    /** Department name */
    private String deptName;

    /** Department of the phone */
    private String deptPhone;

    /** According to the order */
    private String orderNum;

    /** head */
    private String leader;

    /** Contact phone number */
    private String phone;

    /** email */
    private String email;

    /** province .*/
    private String province;

    /** The city .*/
    private String city;

    /** area .*/
    private String district;

    /** Detailed address .*/
    private String address;

    /** Store type The data fieldsdept_type 1stores2Its franchisees .*/
    private Integer deptType;

    /** The default0other 1branch 2stores */
    private Integer type;

    /** Department of state:0normal,1disable */
    private String status;

    /** Delete logo(0On behalf of there 2On behalf of the delete) */
    private String delFlag;

    /**
     * longitude
     */
    @Excel(name = "longitude")
    private String longitude;

    /**
     * latitude
     */
    @Excel(name = "latitude")
    private String latitude;

    /** Name of Parent Department */
    @Transient
    private String parentName;
    
    /** A division */
    private List<SysDept> children = new ArrayList<SysDept>();


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "The department name cannot be empty")
    @Size(min = 0, max = 30, message = "The department name cannot exceed the maximum length30A character")
    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    @NotBlank(message = "The display order cannot be empty")
    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    @Size(min = 0, max = 11, message = "The number length cannot exceed11A character")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "The mailbox format is incorrect")
    @Size(min = 0, max = 50, message = "The length of the mailbox cannot exceed50A character")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDeptType() {
        return deptType;
    }

    public void setDeptType(Integer deptType) {
        this.deptType = deptType;
    }

    public List<SysDept> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysDept> children)
    {
        this.children = children;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("deptName", getDeptName())
            .append("deptCode", getDeptCode())
            .append("deptPhone", getDeptPhone())
            .append("province", getProvince())
            .append("city", getCity())
            .append("district", getDistrict())
            .append("address", getAddress())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("deptType", getDeptType())
            .append("type", getType())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
