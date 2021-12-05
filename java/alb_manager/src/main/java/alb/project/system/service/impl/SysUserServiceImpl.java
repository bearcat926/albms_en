package alb.project.system.service.impl;

import alb.common.constant.UserConstants;
import alb.common.exception.CustomException;
import alb.project.system.domain.*;
import alb.project.system.mapper.*;
import alb.project.system.service.ISysConfigService;
import alb.project.system.service.ISysUserService;
import alb.common.utils.SecurityUtils;
import alb.common.utils.StringUtils;
import alb.framework.aspectj.lang.annotation.DataScope;
import alb.project.system.domain.*;
import alb.project.system.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The user Business layer processing
 *
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * Query the user list by conditional paging
     *
     * @param user The user information
     * @return User information Set information
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * Query users by user name
     *
     * @param userName The user name
     * @return User Object Information
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * By the userIDQuery the user
     *
     * @param userId The userID
     * @return User Object Information
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * Query users by user ID
     * @param userCode
     * @return
     */
    @Override
    public SysUser selectUserByUserCode(String userCode){
        return userMapper.selectUserByUserCode(userCode);
    }

    /**
     * Example Query the role group of a user
     *
     * @param userName The user name
     * @return The results of
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * Example Query the job group to which a user belongs
     *
     * @param userName The user name
     * @return The results of
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * Verify that the user name is unique
     *
     * @param userName The user name
     * @return The results of
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Verify that the user name is unique
     *
     * @param user The user information
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * checkemailWhether or not the only
     *
     * @param user The user information
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Verify whether the user is allowed to perform operations
     *
     * @param user The user information
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new CustomException("Do not operate the super administrator");
        }
    }

    /**
     * Added Saving user information
     *
     * @param user The user information
     * @return The results of
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // Adding User Information
        int rows = userMapper.insertUser(user);
        // New user post association
        insertUserPost(user);
        // Added user and role management
        insertUserRole(user);
        return rows;
    }

    /**
     * Modify Save user information
     *
     * @param user The user information
     * @return The results of
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // Delete the association between a user and a role
        userRoleMapper.deleteUserRoleByUserId(userId);
        // Added user and role management
        insertUserRole(user);
        // Delete the association between a user and a job
        userPostMapper.deleteUserPostByUserId(userId);
        // New users and post management
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * Modifying user Status
     *
     * @param user The user information
     * @return The results of
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * Example Modify basic user information
     *
     * @param user The user information
     * @return The results of
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * Modifying a User's Profile picture
     *
     * @param userName The user name
     * @param avatar   Head address
     * @return The results of
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * Resetting a user password
     *
     * @param user The user information
     * @return The results of
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * Resetting a user password
     *
     * @param userName The user name
     * @param password password
     * @return The results of
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * Added user role information
     *
     * @param user The user object
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // Added user and role management
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * Added user post information
     *
     * @param user The user object
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // New users and post management
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * By the userIDDelete user
     *
     * @param userId The userID
     * @return The results of
     */
    @Override
    public int deleteUserById(Long userId) {
        // Delete the association between a user and a role
        userRoleMapper.deleteUserRoleByUserId(userId);
        // Delete the user and job table
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * Delete user information in batches
     *
     * @param userIds The user to be deletedID
     * @return The results of
     */
    @Override
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * Importing User Data
     *
     * @param userList        User data list
     * @param isUpdateSupport Whether to update support,If it already exists,The data is updated
     * @param operName        The user operation
     * @return The results of
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new CustomException("The imported user data cannot be empty!");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // Querying Department Information
                SysDept dept = deptMapper.selectDeptByCode(user.getDeptCode());
                if (dept == null) {
                    throw new CustomException("The id is not queried" + user.getDeptCode() + "The department in charge of the。");
                }
                user.setDeptId(dept.getDeptId());
                // Verify that the user exists
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、account ").append(user.getUserName()).append(" Import success");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、account ").append(user.getUserName()).append(" The update is successful");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、account ").append(user.getUserName()).append(" existing");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、account " + user.getUserName() + " Import failure:";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "I'm sorry,Import failure!A total of " + failureNum + " The data format is incorrect,Error is as follows:");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "Congratulations to you,All data has been imported successfully!A total of " + successNum + " article,The following data:");
        }
        return successMsg.toString();
    }

    @Override
    public SysUser selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public List<SysUser> selectDeptEmployeesList(Long deptId) {
        return userMapper.selectDeptEmployeesList(deptId);
    }

    /**
     * Get the number of employees by month
     * @param month
     * @return
     */
    @Override
    public Integer getTotalNumByMonth(String month) {
        return userMapper.getTotalNumByMonth(month);
    }

    /**
     * According to the number of optometrists per month
     * @param month
     * @return
     */
    @Override
    public Integer getTotalOptometristNumByMonth(String month) {
        return userMapper.getTotalOptometristNumByMonth(month);
    }

    /**
     * Get all user numbers
     * @return
     */
    @Override
    public Integer getTotalNum() {
        return userMapper.getTotalNum();
    }

    /**
     * Get all optometrists
     * @return
     */
    @Override
    public Integer getTotalOptometristNum() {
        return userMapper.getTotalOptometristNum();
    }

    @Override
    public String getLastUserCode() {
        String userCode = userMapper.getLastUserCode().toString();
        StringBuilder newPre = new StringBuilder();
        int num = 4;
        for(int i = 0; i < num - userCode.length(); i ++){
            newPre.append("0");
        }
        return newPre + userCode;
    }
}
