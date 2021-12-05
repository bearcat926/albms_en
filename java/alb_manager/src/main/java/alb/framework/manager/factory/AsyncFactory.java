package alb.framework.manager.factory;

import java.util.TimerTask;

import alb.common.constant.Constants;
import alb.common.utils.spring.SpringUtils;
import alb.project.monitor.domain.SysLogininfor;
import alb.project.monitor.domain.SysOperLog;
import alb.project.monitor.service.ISysLogininforService;
import alb.project.monitor.service.ISysOperLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import alb.common.utils.LogUtils;
import alb.common.utils.ServletUtils;
import alb.common.utils.ip.AddressUtils;
import alb.common.utils.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * Asynchronous factory(Task generation)
 *
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * Record login information
     * 
     * @param username The user name
     * @param status state
     * @param message The message
     * @param args The list of
     * @return tasktask
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
            final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // Prints information to logs
                sys_user_logger.info(s.toString(), args);
                // Obtain the client operating system
                String os = userAgent.getOperatingSystem().getName();
                // Get the client browser
                String browser = userAgent.getBrowser().getName();
                // Encapsulated object
                SysLogininfor logininfor = new SysLogininfor();
                logininfor.setUserName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // State of the log
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status))
                {
                    logininfor.setStatus(Constants.SUCCESS);
                }
                else if (Constants.LOGIN_FAIL.equals(status))
                {
                    logininfor.setStatus(Constants.FAIL);
                }
                // Insert data
                SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * Operation Log
     * 
     * @param operLog Operation Log Information
     * @return tasktask
     */
    public static TimerTask recordOper(final SysOperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // Remote query operation location
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
