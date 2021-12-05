package alb.project.monitor.mapper;

import java.util.List;
import alb.project.monitor.domain.SysLogininfor;

/**
 * Information about system access logs The data layer
 *
 */
public interface SysLogininforMapper
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
     * @return The results of
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * None example Clear system login logs
     * 
     * @return The results of
     */
    int cleanLogininfor();
}
