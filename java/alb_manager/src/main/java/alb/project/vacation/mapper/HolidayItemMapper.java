package alb.project.vacation.mapper;

import alb.project.vacation.domain.HolidayItem;

import java.util.List;

/**
 * Vacation to-do list database access layer
 */
public interface HolidayItemMapper {

    /**
     * Example Query a single piece of data
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
     * @return Affect the number of rows
     */
    int deleteById(Long holidayId);

}
