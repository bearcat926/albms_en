package alb.project.vacation.service;

import alb.project.vacation.domain.Holiday;
import alb.project.vacation.paramsVO.HolidayUserParamsVO;
import alb.project.vacation.resultVO.HolidayUserResultVO;

import java.util.List;

/**
 * Vacation table service interface
 */
public interface IHolidayService {

    /**
     * Example Query a single piece of data
     *
     * @param holidayId A primary key
     * @return Instance objects
     */
    Holiday queryOne(Long holidayId);

    /**
     * Querying multiple pieces of Data
     *
     * @param holiday
     * @return
     */
    List<Holiday> queryAll(Holiday holiday);

    /**
     * The new data
     *
     * @param holiday Instance objects
     * @return Instance objects
     */
    int insert(Holiday holiday);

    /**
     * Modify the data
     *
     * @param holiday Instance objects
     * @return Instance objects
     */
    int update(Holiday holiday);

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return The success of
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
