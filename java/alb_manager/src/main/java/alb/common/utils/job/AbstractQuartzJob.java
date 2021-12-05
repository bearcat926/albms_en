package alb.common.utils.job;

import java.util.Date;

import alb.common.constant.Constants;
import alb.common.constant.ScheduleConstants;
import alb.common.utils.ExceptionUtil;
import alb.common.utils.StringUtils;
import alb.common.utils.bean.BeanUtils;
import alb.common.utils.spring.SpringUtils;
import alb.project.monitor.domain.SysJob;
import alb.project.monitor.domain.SysJobLog;
import alb.project.monitor.service.ISysJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * abstractquartzcall
 *
 */
public abstract class AbstractQuartzJob implements Job
{
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * Thread local variable
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        SysJob sysJob = new SysJob();
        BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
        try
        {
            before(context, sysJob);
            if (sysJob != null)
            {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        }
        catch (Exception e)
        {
            log.error("Task execution exception  - :", e);
            after(context, sysJob, e);
        }
    }

    /**
     * Perform before
     *
     * @param context Work execution context object
     * @param sysJob System Scheduled Tasks
     */
    protected void before(JobExecutionContext context, SysJob sysJob)
    {
        threadLocal.set(new Date());
    }

    /**
     * After performing
     *
     * @param context Work execution context object
     * @param sysScheduleJob System Scheduled Tasks
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Exception e)
    {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(new Date());
        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " The total time consuming:" + runMs + "ms");
        if (e != null)
        {
            sysJobLog.setStatus(Constants.FAIL);
            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        }
        else
        {
            sysJobLog.setStatus(Constants.SUCCESS);
        }

        // Write to the database
        SpringUtils.getBean(ISysJobLogService.class).addJobLog(sysJobLog);
    }

    /**
     * Execution method,Reload by subclass
     *
     * @param context Work execution context object
     * @param sysJob System Scheduled Tasks
     * @throws Exception Exceptions during execution
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
