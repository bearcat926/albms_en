package alb.project.monitor.service;

import java.util.List;
import alb.project.monitor.domain.SysLogininfor;

/**
 * Information about system access logs The service layer
 *
 */
public interface ISysLogininforService
{
    /**
     * Added system login logs
     * 
     * @param logininfor Accessing log objects
     */
    void insertLogininfor(SysLogininfor logininfor);

    /**
     * Example Query a set of system login logs
     * 
     * @param logininfor Accessing log objects
     * @return Collection of login records
     */
    List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * Delete system login logs in batches
     * 
     * @param infoIds Login logs to be deletedID
     * @return
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * None example Clear system login logs
     */
    void cleanLogininfor();
}
