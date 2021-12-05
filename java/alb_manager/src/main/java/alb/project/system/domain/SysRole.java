package alb.project.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * Character sheet sys_role
 *
 */
public class SysRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** roleID */
    @Excel(name = "Part number", cellType = ColumnType.NUMERIC)
    private Long roleId;

    /** Character name */
    @Excel(name = "Character name")
    private String roleName;

    /** Role authorization */
    @Excel(name = "Role authorization")
    private String roleKey;

    /** Character sorting */
    @Excel(name = "Character sorting")
    private String roleSort;

    /** Data range(1:All data permissions;2:Custom data permissions;3:Data permission of the department;4:Department and the following data rights 5:Only personal data permission) */
    @Excel(name = "Data range", readConverterExp = "1=All data permissions,2=Custom data permissions,3=Data permission of the department,4=Department and the following data rights,5=Only personal data permission")
    private String dataScope;

    /** A status(0normal 1disable) */
    @Excel(name = "A status", readConverterExp = "0=normal,1=disable")
    private String status;

    /** Delete logo(0On behalf of there 2On behalf of the delete) */
    private String delFlag;

    /** Whether the user has the role id Default does not exist */
    private boolean flag = false;

    /** Menus group */
    private Long[] menuIds;

    /** The department group(Data access) */
    private Long[] deptIds;

    public SysRole()
    {

    }

    public SysRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

    @NotBlank(message = "The role name cannot be empty")
    @Size(min = 0, max = 30, message = "The role name cannot exceed the maximum length30A character")
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @NotBlank(message = "Permission characters cannot be empty")
    @Size(min = 0, max = 100, message = "Permission characters cannot exceed the maximum length100A character")
    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey(String roleKey)
    {
        this.roleKey = roleKey;
    }

    @NotBlank(message = "The display order cannot be empty")
    public String getRoleSort()
    {
        return roleSort;
    }

    public void setRoleSort(String roleSort)
    {
        this.roleSort = roleSort;
    }

    public String getDataScope()
    {
        return dataScope;
    }

    public void setDataScope(String dataScope)
    {
        this.dataScope = dataScope;
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

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public Long[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds)
    {
        this.menuIds = menuIds;
    }

    public Long[] getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(Long[] deptIds)
    {
        this.deptIds = deptIds;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .append("roleKey", getRoleKey())
            .append("roleSort", getRoleSort())
            .append("dataScope", getDataScope())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
