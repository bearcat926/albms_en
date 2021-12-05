package alb.project.monitor.service.impl;

import java.util.List;

import alb.project.monitor.domain.SysJobLog;
import alb.project.monitor.mapper.SysJobLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alb.project.monitor.service.ISysJobLogService;

/**
 * Log information about scheduled task scheduling The service layer
 *
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService
{
    @Autowired
    private SysJobLogMapper jobLogMapper;

    /**
     * To obtainquartzSchedule tasks for scheduler logs
     * 
     * @param jobLog Scheduling Log Information
     * @return Scheduling task log collection
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog)
    {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * By scheduling task logsIDQuerying Scheduling Information
     * 
     * @param jobLogId Scheduling Task LogsID
     * @return Information about a scheduling task log object
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * Adding Task Logs
     * 
     * @param jobLog Scheduling Log Information
     */
    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * Example Delete scheduling logs in batches
     * 
     * @param logIds Data to be deletedID
     * @return The results of
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds)
    {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * Deleting Task Logs
     * 
     * @param jobId Operation logID
     */
    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * Clearing task Logs
     */
    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }
}
