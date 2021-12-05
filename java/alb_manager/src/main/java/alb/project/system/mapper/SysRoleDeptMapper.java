package alb.project.system.mapper;

import java.util.List;

import alb.project.system.domain.SysRoleDept;

/**
 * Association table between roles and departments The data layer
 *
 */
public interface SysRoleDeptMapper
{
    /**
     * Through the roleIDDelete the role and department association
     * 
     * @param roleId roleID
     * @return The results of
     */
    int deleteRoleDeptByRoleId(Long roleId);

    /**
     * Delete departments associated with roles in batches
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteRoleDept(Long[] ids);

    /**
     * Query the number of departments used
     * 
     * @param deptId departmentID
     * @return The results of
     */
    int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * Add department information about roles in batches
     * 
     * @param roleDeptList Role Department List
     * @return The results of
     */
    int batchRoleDept(List<SysRoleDept> roleDeptList);
}
