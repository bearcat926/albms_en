package alb.project.monitor.mapper;

import java.util.List;
import alb.project.monitor.domain.SysJobLog;

/**
 * Logs about scheduling tasks The data layer
 *
 */
public interface SysJobLogMapper
{
    /**
     * To obtainquartzSchedule tasks for scheduler logs
     * 
     * @param jobLog Scheduling Log Information
     * @return Scheduling task log collection
     */
    List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    /**
     * Example Query the logs of all scheduling tasks
     *
     * @return Scheduling task log list
     */
    List<SysJobLog> selectJobLogAll();

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
     * @return The results of
     */
    int insertJobLog(SysJobLog jobLog);

    /**
     * Example Delete scheduling logs in batches
     * 
     * @param logIds Data to be deletedID
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
