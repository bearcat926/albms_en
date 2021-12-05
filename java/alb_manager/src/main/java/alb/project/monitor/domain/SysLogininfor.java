package alb.project.monitor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * System access record form sys_logininfor
 *
 */
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "The serial number", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /** The user account */
    @Excel(name = "The user account")
    private String userName;

    /** The login status 0successful 1failure */
    @Excel(name = "The login status", readConverterExp = "0=successful,1=failure")
    private String status;

    /** The loginIPaddress */
    @Excel(name = "The login address")
    private String ipaddr;

    /** The login site */
    @Excel(name = "The login site")
    private String loginLocation;

    /** Browser Type */
    @Excel(name = "The browser")
    private String browser;

    /** The operating system */
    @Excel(name = "The operating system")
    private String os;

    /** The prompt message */
    @Excel(name = "The prompt message")
    private String msg;

    /** Access time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Access time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    public Long getInfoId()
    {
        return infoId;
    }

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }
}