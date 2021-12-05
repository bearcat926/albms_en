package alb.project.system.domain;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonProperty;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.aspectj.lang.annotation.Excel.Type;
import alb.framework.aspectj.lang.annotation.Excels;
import alb.framework.web.domain.BaseEntity;
import org.springframework.data.annotation.Transient;

/**
 * The user object sys_user
 *
 */
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * The userID
     */
    @Excel(name = "User number", cellType = ColumnType.NUMERIC, prompt = "The user id")
    private Long userId;

    /**
     * departmentID
     */
    private Long deptId;

    @Excel(name = "Department number", type = Type.IMPORT)
    @Transient
    private String deptCode;


    @Excel(name = "Work number")
    private String userCode;

    /**
     * Position name
     */
    @Excel(name = "position",type = Type.EXPORT)
    @Transient
    private String postName;

    /**
     * The user account
     */
    @Excel(name = "The login name")
    private String userName;

    /**
     * The user nickname
     */
    @Excel(name = "The user name")
    private String nickName;

    /**
     * User mailbox
     */
    @Excel(name = "User mailbox")
    private String email;

    /**
     * Mobile phone number
     */
    @Excel(name = "Mobile phone number")
    private String phonenumber;

    /**
     * User's gender
     */
    @Excel(name = "User's gender", readConverterExp = "0=male,1=female,2=The unknown")
    private String sex;

    /**
     * The avatars
     */
    private String avatar;

    @Excel(name = "age")
    private Integer age;

    @Excel(name = "province")
    private String province;

    @Excel(name = "The city")
    private String city;

    @Excel(name = "area")
    private String district;

    /**
     * password
     */
    private String password;

    /**
     * Salt encryption
     */
    private String salt;

    /**
     * Account status(0normal 1disable)
     */
    @Excel(name = "Account status", readConverterExp = "0=normal,1=disable")
    private String status;

    /**
     * Delete logo(0On behalf of there 2On behalf of the delete)
     */
    private String delFlag;

    /**
     * The final landingIP
     */
    @Excel(name = "The final landingIP", type = Type.EXPORT)
    private String loginIp;

    /**
     * Last landing time
     */
    @Excel(name = "Last landing time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /**
     * Department of object
     */
    @Excels({
            @Excel(name = "Department number", targetAttr = "deptCode",type = Type.EXPORT),
            @Excel(name = "Department name", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "Department head", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    /**
     * Role object
     */
    private List<SysRole> roles;

    /**
     * Character set
     */
    private Long[] roleIds;

    /**
     * Post group
     */
    private Long[] postIds;

    private Long postId;

    public SysUser() {

    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Size(min = 0, max = 30, message = "The user nickname cannot exceed the maximum length30A character")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @NotBlank(message = "The user account cannot be empty")
    @Size(min = 0, max = 30, message = "The length of the user account cannot exceed30A character")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "The mailbox format is incorrect")
    @Size(min = 0, max = 50, message = "The length of the mailbox cannot exceed50A character")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "The length of the mobile phone number cannot exceed11A character")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("userCode", getUserCode())
                .append("age", getAge())
                .append("province", getProvince())
                .append("city", getCity())
                .append("district", getDistrict())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("dept", getDept())
                .toString();
    }
}
