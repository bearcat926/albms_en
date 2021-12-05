package alb.project.monitor.service;

import java.util.List;
import alb.project.monitor.domain.SysOperLog;

/**
 * The operation log The service layer
 *
 */
public interface ISysOperLogService
{
    /**
     * Adding Operation Logs
     * 
     * @param operLog Operation Log Object
     */
    void insertOperlog(SysOperLog operLog);

    /**
     * Example Query a set of system operation logs
     * 
     * @param operLog Operation Log Object
     * @return Operation log collection
     */
    List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * Delete system operation logs in batches
     * 
     * @param operIds Operation logs that you want to deleteID
     * @return The results of
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * Query details about operation logs
     * 
     * @param operId operationID
     * @return Operation Log Object
     */
    SysOperLog selectOperLogById(Long operId);

    /**
     * Clearing Operation Logs
     */
    void cleanOperLog();
}
