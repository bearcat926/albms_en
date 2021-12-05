package alb.project.vacation.mapper;

import alb.project.vacation.domain.Holiday;
import alb.project.vacation.paramsVO.HolidayUserParamsVO;
import alb.project.vacation.resultVO.HolidayUserResultVO;

import java.util.List;

/**
 * Vacation table database access layer
 */
public interface HolidayMapper {
    /**
     * throughIDExample Query a single piece of data
     *
     * @param holidayId A primary key
     * @return Instance objects
     */
    int queryHolidayIsExists(Long holidayId);

    /**
     * throughIDExample Query a single piece of data
     *
     * @param holidayId A primary key
     * @return Instance objects
     */
    Holiday queryOne(Long holidayId);

    /**
     * Query by entity as filter criteria
     *
     * @param holiday Instance objects
     * @return The object list
     */
    List<Holiday> queryAll(Holiday holiday);

    /**
     * The new data
     *
     * @param holiday Instance objects
     * @return Affect the number of rows
     */
    int insert(Holiday holiday);

    /**
     * Modify the data
     *
     * @param holiday Instance objects
     * @return Affect the number of rows
     */
    int update(Holiday holiday);

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return Affect the number of rows
     */
    int deleteById(Long holidayId);

    /**
     * Querying a User List
     *
     * @param holidayUserParamsVO Instance objects
     * @return Instance objects
     */
    List<HolidayUserResultVO> selectUserList(HolidayUserParamsVO holidayUserParamsVO);

}