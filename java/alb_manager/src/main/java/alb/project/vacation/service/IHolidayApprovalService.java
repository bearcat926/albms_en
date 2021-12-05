package alb.project.vacation.service;

import alb.project.system.domain.SysRole;
import alb.project.vacation.domain.HolidayApproval;

import java.util.List;

/**
 * Vacation table service interface
 */
public interface IHolidayApprovalService {

    /**
     * Check whether the next node exists by entity
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    boolean hasNextApproved(HolidayApproval holidayApproval);

    /**
     * Example Query a single piece of data
     *
     * @param holidayApprovalId A primary key
     * @return Instance objects
     */
    HolidayApproval queryOne(Long holidayApprovalId);

    /**
     * After detection insertion,Whether the holiday type has a ring
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    boolean hasRing(HolidayApproval holidayApproval);

    /**
     * Query by entity as filter criteria
     *
     * @param holidayApproval Instance objects
     * @return The object list
     */
    List<HolidayApproval> queryAll(HolidayApproval holidayApproval);


    /**
     * Querying the Role List
     *
     * @return The object list
     */
    List<SysRole> selectRoleList();

    /**
     * The new data
     *
     * @param holidayApproval Instance objects
     * @return Affect the number of rows
     */
    int insert(HolidayApproval holidayApproval);

    /**
     * Delete data by primary key
     *
     * @param holidayApprovalId A primary key
     * @return The success of
     */
    int deleteById(Long holidayApprovalId);

}
