package alb.project.system.mapper;

import java.util.List;

import alb.project.system.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;

/**
 * The association table between users and roles The data layer
 *
 */
public interface SysUserRoleMapper
{
    /**
     * By the userIDDelete the association between a user and a role
     * 
     * @param userId The userID
     * @return The results of
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * Delete associations between users and roles in batches
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteUserRole(Long[] ids);

    /**
     * Through the roleIDExample Query the number of roles
     * 
     * @param roleId roleID
     * @return The results of
     */
    int countUserRoleByRoleId(Long roleId);

    /**
     * Add user role information in batches
     * 
     * @param userRoleList User Role List
     * @return The results of
     */
    int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * Example Delete the association between a user and a role
     * 
     * @param userRole User and role association information
     * @return The results of
     */
    int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * Cancel the roles of authorized users in batches
     * 
     * @param roleId roleID
     * @param userIds User data to be deletedID
     * @return The results of
     */
    int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
