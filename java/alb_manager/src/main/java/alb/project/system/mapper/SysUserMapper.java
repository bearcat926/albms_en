package alb.project.system.mapper;

import alb.project.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * The users table The data layer
 *
 */
public interface SysUserMapper {
    /**
     * Query the user list by conditional paging
     *
     * @param sysUser The user information
     * @return User information Set information
     */
    List<SysUser> selectUserList(SysUser sysUser);

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
     * Modifying a User's Profile picture
     *
     * @param userName The user name
     * @param avatar   Head address
     * @return The results of
     */
    int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * Resetting a user password
     *
     * @param userName The user name
     * @param password password
     * @return The results of
     */
    int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

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
     * Verify that the user name is unique
     *
     * @param userName The user name
     * @return The results of
     */
    int checkUserNameUnique(String userName);

    /**
     * Verify that the mobile phone number is unique
     *
     * @param phonenumber Mobile phone number
     * @return The results of
     */
    SysUser checkPhoneUnique(String phonenumber);

    /**
     * checkemailWhether or not the only
     *
     * @param email User mailbox
     * @return The results of
     */
    SysUser checkEmailUnique(String email);

    SysUser selectUserByPhone(@Param("phone") String phone);

    List<SysUser> selectDeptEmployeesList(@Param("deptId") Long deptId);

    /**
     * Get the number of employees by month
     *
     * @param month
     * @return
     */
    Integer getTotalNumByMonth(@Param("month") String month);

    /**
     * According to the number of optometrists per month
     *
     * @param month
     * @return
     */
    Integer getTotalOptometristNumByMonth(@Param("month") String month);

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
     * Get the latest user id
     * @return
     */
    Integer getLastUserCode();
}
