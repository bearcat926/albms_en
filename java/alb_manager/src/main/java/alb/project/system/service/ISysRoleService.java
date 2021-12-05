package alb.project.system.service;

import java.util.List;
import java.util.Set;
import alb.project.system.domain.SysRole;

/**
 * Role business layer
 *
 */
public interface ISysRoleService
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
     * @return Permissions list
     */
    Set<String> selectRolePermissionByUserId(Long userId);

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
     * Verify that the role name is unique
     * 
     * @param role The role of information
     * @return The results of
     */
    String checkRoleNameUnique(SysRole role);

    /**
     * Verify that the role has unique permissions
     * 
     * @param role The role of information
     * @return The results of
     */
    String checkRoleKeyUnique(SysRole role);

    /**
     * Verify whether the role allows operations
     * 
     * @param role The role of information
     */
    void checkRoleAllowed(SysRole role);

    /**
     * Through the roleIDExample Query the number of roles
     * 
     * @param roleId roleID
     * @return The results of
     */
    int countUserRoleByRoleId(Long roleId);

    /**
     * Added Saving role information
     * 
     * @param role The role of information
     * @return The results of
     */
    int insertRole(SysRole role);

    /**
     * Modify Save role information
     * 
     * @param role The role of information
     * @return The results of
     */
    int updateRole(SysRole role);

    /**
     * Modifying role Status
     * 
     * @param role The role of information
     * @return The results of
     */
    int updateRoleStatus(SysRole role);

    /**
     * Example Modify data permission information
     * 
     * @param role The role of information
     * @return The results of
     */
    int authDataScope(SysRole role);

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
