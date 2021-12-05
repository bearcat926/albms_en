package alb.project.monitor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * Operation log record table oper_log
 *
 */
public class SysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Log primary key */
    @Excel(name = "Operation sequence number", cellType = ColumnType.NUMERIC)
    private Long operId;

    /** Operation module */
    @Excel(name = "Operation module")
    private String title;

    /** Business types(0other 1new 2Modify the 3delete) */
    @Excel(name = "Business types", readConverterExp = "0=other,1=new,2=Modify the,3=delete,4=authorization,5=export,6=The import,7=Strong back,8=The generated code,9=Empty data")
    private Integer businessType;

    /** Business type array */
    private Integer[] businessTypes;

    /** Request method */
    @Excel(name = "Request method")
    private String method;

    /** Request way */
    @Excel(name = "Request way")
    private String requestMethod;

    /** Operating categories(0other 1Background the user 2Mobile terminal user) */
    @Excel(name = "Operating categories", readConverterExp = "0=other,1=Background the user,2=Mobile terminal user")
    private Integer operatorType;

    /** The operator */
    @Excel(name = "The operator")
    private String operName;

    /** Department name */
    @Excel(name = "Department name")
    private String deptName;

    /** requesturl */
    @Excel(name = "Request the address")
    private String operUrl;

    /** The operating address */
    @Excel(name = "The operating address")
    private String operIp;

    /** Operation place */
    @Excel(name = "Operation place")
    private String operLocation;

    /** Request parameters */
    @Excel(name = "Request parameters")
    private String operParam;

    /** Returns the parameter */
    @Excel(name = "Returns the parameter")
    private String jsonResult;

    /** Operating state(0normal 1abnormal) */
    @Excel(name = "state", readConverterExp = "0=normal,1=abnormal")
    private Integer status;

    /** The error message */
    @Excel(name = "The error message")
    private String errorMsg;

    /** Operating time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Operating time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    public Long getOperId()
    {
        return operId;
    }

    public void setOperId(Long operId)
    {
        this.operId = operId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }

    public Integer[] getBusinessTypes()
    {
        return businessTypes;
    }

    public void setBusinessTypes(Integer[] businessTypes)
    {
        this.businessTypes = businessTypes;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType()
    {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType)
    {
        this.operatorType = operatorType;
    }

    public String getOperName()
    {
        return operName;
    }

    public void setOperName(String operName)
    {
        this.operName = operName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOperUrl()
    {
        return operUrl;
    }

    public void setOperUrl(String operUrl)
    {
        this.operUrl = operUrl;
    }

    public String getOperIp()
    {
        return operIp;
    }

    public void setOperIp(String operIp)
    {
        this.operIp = operIp;
    }

    public String getOperLocation()
    {
        return operLocation;
    }

    public void setOperLocation(String operLocation)
    {
        this.operLocation = operLocation;
    }

    public String getOperParam()
    {
        return operParam;
    }

    public void setOperParam(String operParam)
    {
        this.operParam = operParam;
    }

    public String getJsonResult()
    {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult)
    {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime()
    {
        return operTime;
    }

    public void setOperTime(Date operTime)
    {
        this.operTime = operTime;
    }
}
