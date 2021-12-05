package alb.project.system.service;

import alb.framework.security.LoginUser;
import alb.project.monitor.domain.SysUserOnline;

/**
 * Online users The service layer
 *
 */
public interface ISysUserOnlineService
{
    /**
     * Query information by login address
     * 
     * @param ipaddr The login address
     * @param user The user information
     * @return Online User Information
     */
    SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    /**
     * Query information by user name
     * 
     * @param userName The user name
     * @param user The user information
     * @return Online User Information
     */
    SysUserOnline selectOnlineByUserName(String userName, LoginUser user);

    /**
     * By login address/User name Query information
     * 
     * @param ipaddr The login address
     * @param userName The user name
     * @param user The user information
     * @return Online User Information
     */
    SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

    /**
     * Example Set online user information
     * 
     * @param user The user information
     * @return Online users
     */
    SysUserOnline loginUserToUserOnline(LoginUser user);
}
