package alb.project.vacation.mapper;

import alb.project.system.domain.SysRole;
import alb.project.vacation.domain.HolidayApproval;

import java.util.List;

/**
 * Vacation to-do list database access layer
 */
public interface HolidayApprovalMapper {

    /**
     * Check whether the next node exists by entity
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    Long hasNext(HolidayApproval holidayApproval);

    /**
     * throughIDExample Query a single piece of data
     *
     * @param holidayApprovalId A primary key
     * @return Instance objects
     */
    HolidayApproval queryOne(Long holidayApprovalId);

    /**
     * Query by entity as filter criteria
     *
     * @param holidayApproval Instance objects
     * @return The object list
     */
    List<HolidayApproval> queryAll(HolidayApproval holidayApproval);

    /**
     * The new data
     *
     * @param holidayApproval Instance objects
     * @return Affect the number of rows
     */
    int insert(HolidayApproval holidayApproval);

    /**
     * Modify the data
     *
     * @param holidayApproval Instance objects
     * @return Affect the number of rows
     */
    int update(HolidayApproval holidayApproval);

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return Affect the number of rows
     */
    int deleteById(Long holidayId);

    /**
     * Querying the Role List
     *
     * @return The object list
     */
    List<SysRole> selectRoleList();
}
