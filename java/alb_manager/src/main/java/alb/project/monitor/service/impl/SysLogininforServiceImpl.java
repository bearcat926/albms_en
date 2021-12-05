package alb.project.monitor.service.impl;

import java.util.List;

import alb.project.monitor.domain.SysLogininfor;
import alb.project.monitor.mapper.SysLogininforMapper;
import alb.project.monitor.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Information about system access logs Service layer processing
 *
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService
{

    @Autowired
    private SysLogininforMapper logininforMapper;

    /**
     * Added system login logs
     * 
     * @param logininfor Accessing log objects
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * Example Query a set of system login logs
     * 
     * @param logininfor Accessing log objects
     * @return Collection of login records
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor)
    {
        return logininforMapper.selectLogininforList(logininfor);
    }

    /**
     * Delete system login logs in batches
     * 
     * @param infoIds Login logs to be deletedID
     * @return
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds)
    {
        return logininforMapper.deleteLogininforByIds(infoIds);
    }

    /**
     * None example Clear system login logs
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
