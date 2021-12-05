package alb.project.monitor.service;

import java.util.List;

import alb.project.monitor.domain.SysJobLog;

/**
 * Log information about scheduled task scheduling The service layer
 *
 */
public interface ISysJobLogService
{
    /**
     * To obtainquartzSchedule tasks for scheduler logs
     * 
     * @param jobLog Scheduling Log Information
     * @return Scheduling task log collection
     */
    List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    /**
     * By scheduling task logsIDQuerying Scheduling Information
     * 
     * @param jobLogId Scheduling Task LogsID
     * @return Information about a scheduling task log object
     */
    SysJobLog selectJobLogById(Long jobLogId);

    /**
     * Adding Task Logs
     * 
     * @param jobLog Scheduling Log Information
     */
    void addJobLog(SysJobLog jobLog);

    /**
     * Example Delete scheduling logs in batches
     * 
     * @param logIds Logs to be deletedID
     * @return The results of
     */
    int deleteJobLogByIds(Long[] logIds);

    /**
     * Deleting Task Logs
     * 
     * @param jobId Operation logID
     * @return The results of
     */
    int deleteJobLogById(Long jobId);

    /**
     * Clearing task Logs
     */
    void cleanJobLog();
}
