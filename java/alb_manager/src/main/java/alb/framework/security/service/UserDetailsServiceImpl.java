package alb.framework.security.service;

import alb.common.exception.BaseException;
import alb.project.system.domain.SysUser;
import alb.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import alb.common.enums.UserStatus;
import alb.common.utils.StringUtils;
import alb.framework.security.LoginUser;

/**
 * User authentication processing
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user))
        {
            log.info("The logged in user:{} There is no.", username);
            throw new UsernameNotFoundException("The logged in user:" + username + " There is no");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("The logged in user:{} Has been deleted.", username);
            throw new BaseException("I'm sorry,Your account number:" + username + " Has been deleted");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("The logged in user:{} Has been discontinued.", username);
            throw new BaseException("I'm sorry,Your account number:" + username + " Has been discontinued");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
