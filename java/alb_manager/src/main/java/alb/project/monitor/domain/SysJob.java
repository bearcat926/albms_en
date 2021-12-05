package alb.project.monitor.domain;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import alb.common.constant.ScheduleConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import alb.common.utils.StringUtils;
import alb.common.utils.job.CronUtils;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * Scheduled task scheduling table sys_job
 *
 */
public class SysJob extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** taskID */
    @Excel(name = "The task sequence number", cellType = ColumnType.NUMERIC)
    private Long jobId;

    /** The name of the task */
    @Excel(name = "The name of the task")
    private String jobName;

    /** The task group name */
    @Excel(name = "The task group name")
    private String jobGroup;

    /** Calling the target string */
    @Excel(name = "Calling the target string")
    private String invokeTarget;

    /** cronExecute expression */
    @Excel(name = "Execute expression ")
    private String cronExpression;

    /** cronPlanning strategy */
    @Excel(name = "Planning strategy ", readConverterExp = "0=The default,1=Trigger execution immediately,2=Trigger an execution,3=Immediate execution is not triggered")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /** Concurrent execution(0allow 1ban) */
    @Excel(name = "Concurrent execution", readConverterExp = "0=allow,1=ban")
    private String concurrent;

    /** Task status(0normal 1suspended) */
    @Excel(name = "Task status", readConverterExp = "0=normal,1=suspended")
    private String status;

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    @NotBlank(message = "The task name cannot be empty")
    @Size(min = 0, max = 64, message = "The task name cannot exceed64A character")
    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobGroup()
    {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    @NotBlank(message = "The call target string cannot be empty")
    @Size(min = 0, max = 1000, message = "The length of the call target string cannot exceed500A character")
    public String getInvokeTarget()
    {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget)
    {
        this.invokeTarget = invokeTarget;
    }

    @NotBlank(message = "CronExecution expressions cannot be empty")
    @Size(min = 0, max = 255, message = "CronThe execution expression cannot exceed255A character")
    public String getCronExpression()
    {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getNextValidTime()
    {
        if (StringUtils.isNotEmpty(cronExpression))
        {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

    public String getMisfirePolicy()
    {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy)
    {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent()
    {
        return concurrent;
    }

    public void setConcurrent(String concurrent)
    {
        this.concurrent = concurrent;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("jobId", getJobId())
            .append("jobName", getJobName())
            .append("jobGroup", getJobGroup())
            .append("cronExpression", getCronExpression())
            .append("nextValidTime", getNextValidTime())
            .append("misfirePolicy", getMisfirePolicy())
            .append("concurrent", getConcurrent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}