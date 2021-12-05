package alb.project.monitor.service;

import java.util.List;

import alb.common.exception.job.TaskException;
import org.quartz.SchedulerException;
import alb.project.monitor.domain.SysJob;

/**
 * Periodic task scheduling information The service layer
 *
 */
public interface ISysJobService
{
    /**
     * To obtainquartzSchedule tasks for the scheduler
     * 
     * @param job Scheduling information
     * @return Scheduling task set
     */
    List<SysJob> selectJobList(SysJob job);

    /**
     * By scheduling tasksIDQuerying Scheduling Information
     * 
     * @param jobId Scheduling tasksID
     * @return Scheduling task object information
     */
    SysJob selectJobById(Long jobId);

    /**
     * Suspended task
     * 
     * @param job Scheduling information
     * @return The results of
     */
    int pauseJob(SysJob job) throws SchedulerException;

    /**
     * The restore task
     * 
     * @param job Scheduling information
     * @return The results of
     */
    int resumeJob(SysJob job) throws SchedulerException;

    /**
     * After deleting a Task,The correspondingtriggerWill also be deleted
     * 
     * @param job Scheduling information
     * @return The results of
     */
    int deleteJob(SysJob job) throws SchedulerException;

    /**
     * Delete scheduling information in batches
     * 
     * @param jobIds Tasks that need to be deletedID
     * @return The results of
     */
    void deleteJobByIds(Long[] jobIds) throws SchedulerException;

    /**
     * Description The task scheduling status is changed
     * 
     * @param job Scheduling information
     * @return The results of
     */
    int changeStatus(SysJob job) throws SchedulerException;

    /**
     * Run task now
     * 
     * @param job Scheduling information
     * @return The results of
     */
    void run(SysJob job) throws SchedulerException;

    /**
     * The new task
     * 
     * @param job Scheduling information
     * @return The results of
     */
    int insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * The update task
     * 
     * @param job Scheduling information
     * @return The results of
     */
    int updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * checkcronWhether the expression is valid
     * 
     * @param cronExpression expression
     * @return The results of
     */
    boolean checkCronExpressionIsValid(String cronExpression);
}