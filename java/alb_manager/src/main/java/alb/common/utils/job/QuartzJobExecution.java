package alb.common.utils.job;

import alb.project.monitor.domain.SysJob;
import org.quartz.JobExecutionContext;

/**
 * Scheduled Task Processing(Allow concurrent execution)
 *
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
