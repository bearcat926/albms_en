package alb.project.system.mapper;

import java.util.List;
import alb.project.system.domain.SysRole;

/**
 * Character sheet The data layer
 *
 */
public interface SysRoleMapper
{
    /**
     * Query role data based on conditional paging
     * 
     * @param role The role of information
     * @return Role data set information
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * According to the userIDQuery role
     * 
     * @param userId The userID
     * @return The role list
     */
    List<SysRole> selectRolePermissionByUserId(Long userId);

    /**
     * Querying All Roles
     * 
     * @return The role list
     */
    List<SysRole> selectRoleAll();

    /**
     * According to the userIDGets the list of role selection boxes
     * 
     * @param userId The userID
     * @return Select the roleIDThe list of
     */
    List<Integer> selectRoleListByUserId(Long userId);

    /**
     * Through the roleIDQuery role
     * 
     * @param roleId roleID
     * @return Role Object Information
     */
    SysRole selectRoleById(Long roleId);

    /**
     * According to the userIDQuery role
     * 
     * @param userName The user name
     * @return The role list
     */
    List<SysRole> selectRolesByUserName(String userName);

    /**
     * Verify that the role name is unique
     * 
     * @param roleName Character name
     * @return The role of information
     */
    SysRole checkRoleNameUnique(String roleName);

    /**
     * Verify that the role has unique permissions
     * 
     * @param roleKey Role authorization
     * @return The role of information
     */
    SysRole checkRoleKeyUnique(String roleKey);

    /**
     * Modifying Role Information
     * 
     * @param role The role of information
     * @return The results of
     */
    int updateRole(SysRole role);

    /**
     * Adding Role Information
     * 
     * @param role The role of information
     * @return The results of
     */
    int insertRole(SysRole role);

    /**
     * Through the roleIDDelete the role
     * 
     * @param roleId roleID
     * @return The results of
     */
    int deleteRoleById(Long roleId);

    /**
     * Delete role information in batches
     * 
     * @param roleIds Role that you want to deleteID
     * @return The results of
     */
    int deleteRoleByIds(Long[] roleIds);
}
