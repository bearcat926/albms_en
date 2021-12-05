package alb.project.system.service;

import alb.project.system.domain.SysUser;

import java.util.List;

/**
 * The user The business layer
 *
 */
public interface ISysUserService
{
    /**
     * Query the user list by conditional paging
     * 
     * @param user The user information
     * @return User information Set information
     */
    List<SysUser> selectUserList(SysUser user);

    /**
     * Query users by user name
     * 
     * @param userName The user name
     * @return User Object Information
     */
    SysUser selectUserByUserName(String userName);

    /**
     * By the userIDQuery the user
     * 
     * @param userId The userID
     * @return User Object Information
     */
    SysUser selectUserById(Long userId);

    /**
     * Query users by user ID
     * @param userCode
     * @return
     */
    SysUser selectUserByUserCode(String userCode);

    /**
     * According to the userIDExample Query the role group of a user
     * 
     * @param userName The user name
     * @return The results of
     */
    String selectUserRoleGroup(String userName);

    /**
     * According to the userIDExample Query the job group to which a user belongs
     * 
     * @param userName The user name
     * @return The results of
     */
    String selectUserPostGroup(String userName);

    /**
     * Verify that the user name is unique
     * 
     * @param userName The user name
     * @return The results of
     */
    String checkUserNameUnique(String userName);

    /**
     * Verify that the mobile phone number is unique
     *
     * @param user The user information
     * @return The results of
     */
    String checkPhoneUnique(SysUser user);

    /**
     * checkemailWhether or not the only
     *
     * @param user The user information
     * @return The results of
     */
    String checkEmailUnique(SysUser user);

    /**
     * Verify whether the user is allowed to perform operations
     * 
     * @param user The user information
     */
    void checkUserAllowed(SysUser user);

    /**
     * Adding User Information
     * 
     * @param user The user information
     * @return The results of
     */
    int insertUser(SysUser user);

    /**
     * Modifying User Information
     * 
     * @param user The user information
     * @return The results of
     */
    int updateUser(SysUser user);

    /**
     * Modifying user Status
     * 
     * @param user The user information
     * @return The results of
     */
    int updateUserStatus(SysUser user);

    /**
     * Example Modify basic user information
     * 
     * @param user The user information
     * @return The results of
     */
    int updateUserProfile(SysUser user);

    /**
     * Modifying a User's Profile picture
     * 
     * @param userName The user name
     * @param avatar Head address
     * @return The results of
     */
    boolean updateUserAvatar(String userName, String avatar);

    /**
     * Resetting a user password
     * 
     * @param user The user information
     * @return The results of
     */
    int resetPwd(SysUser user);

    /**
     * Resetting a user password
     * 
     * @param userName The user name
     * @param password password
     * @return The results of
     */
    int resetUserPwd(String userName, String password);

    /**
     * By the userIDDelete user
     * 
     * @param userId The userID
     * @return The results of
     */
    int deleteUserById(Long userId);

    /**
     * Delete user information in batches
     * 
     * @param userIds The user to be deletedID
     * @return The results of
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * Importing User Data
     * 
     * @param userList User data list
     * @param isUpdateSupport Whether to update support,If it already exists,The data is updated
     * @param operName The user operation
     * @return The results of
     */
    String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * Query user information by mobile phone number
     * @param phone
     * @return SysUser
     */
    SysUser selectUserByPhone(String phone);

    List<SysUser> selectDeptEmployeesList(Long deptId);

    /**
     * Get the number of employees by month
     * @param month
     * @return
     */
    Integer getTotalNumByMonth(String month);

    /**
     * According to the number of optometrists per month
     * @param month
     * @return
     */
    Integer getTotalOptometristNumByMonth(String month);

    /**
     * Get all user numbers
     * @return
     */
    Integer getTotalNum();

    /**
     * Get all optometrists
     * @return
     */
    Integer getTotalOptometristNum();

    /**
     * Get a new id
     * @return
     */
    String getLastUserCode();
}
