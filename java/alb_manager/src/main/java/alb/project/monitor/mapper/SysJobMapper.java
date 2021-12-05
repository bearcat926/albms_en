package alb.project.monitor.mapper;

import java.util.List;
import alb.project.monitor.domain.SysJob;

/**
 * Scheduling Task Information The data layer
 *
 */
public interface SysJobMapper
{
    /**
     * Example Query scheduling task logs
     * 
     * @param job Scheduling information
     * @return Operation log collection
     */
    List<SysJob> selectJobList(SysJob job);

    /**
     * Example Query all scheduling tasks
     * 
     * @return Scheduling task list
     */
    List<SysJob> selectJobAll();

    /**
     * By schedulingIDExample Query information about scheduling tasks
     * 
     * @param jobId schedulingID
     * @return Role Object Information
     */
    SysJob selectJobById(Long jobId);

    /**
     * By schedulingIDExample Delete scheduling tasks
     * 
     * @param jobId schedulingID
     * @return The results of
     */
    int deleteJobById(Long jobId);

    /**
     * Example Delete scheduling tasks in batches
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteJobByIds(Long[] ids);

    /**
     * Example Modify scheduling task information
     * 
     * @param job Scheduling Task Information
     * @return The results of
     */
    int updateJob(SysJob job);

    /**
     * Added scheduling task information
     * 
     * @param job Scheduling Task Information
     * @return The results of
     */
    int insertJob(SysJob job);
}
