package alb.project.vacation.service;

import alb.project.vacation.domain.Holiday;
import alb.project.vacation.domain.HolidayItem;

import java.util.List;

/**
 * Vacation table service interface
 */
public interface IHolidayItemService {

    /**
     * throughIDExample Query a single piece of data
     *
     * @param holidayItem Instance objects
     * @return Instance objects
     */
    HolidayItem queryOne(HolidayItem holidayItem);

    /**
     * Query by entity as filter criteria
     *
     * @param holidayItem Instance objects
     * @return The object list
     */
    List<HolidayItem> queryAll(HolidayItem holidayItem);

    /**
     * The new data
     *
     * @param holidayItem Instance objects
     * @return Affect the number of rows
     */
    int insert(HolidayItem holidayItem);

    /**
     * Modify the data
     *
     * @param holidayItem Instance objects
     * @return Affect the number of rows
     */
    int update(HolidayItem holidayItem);

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return The success of
     */
    int deleteById(Long holidayId);

}
