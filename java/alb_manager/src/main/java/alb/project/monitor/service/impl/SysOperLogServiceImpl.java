package alb.project.monitor.service.impl;

import java.util.List;

import alb.project.monitor.domain.SysOperLog;
import alb.project.monitor.mapper.SysOperLogMapper;
import alb.project.monitor.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The operation log Service layer processing
 *
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService
{
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * Adding Operation Logs
     * 
     * @param operLog Operation Log Object
     */
    @Override
    public void insertOperlog(SysOperLog operLog)
    {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * Example Query a set of system operation logs
     * 
     * @param operLog Operation Log Object
     * @return Operation log collection
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog)
    {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * Delete system operation logs in batches
     * 
     * @param operIds Operation logs that you want to deleteID
     * @return The results of
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds)
    {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * Query details about operation logs
     * 
     * @param operId operationID
     * @return Operation Log Object
     */
    @Override
    public SysOperLog selectOperLogById(Long operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * Clearing Operation Logs
     */
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
