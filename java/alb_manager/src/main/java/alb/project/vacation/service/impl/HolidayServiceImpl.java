package alb.project.vacation.service.impl;

import alb.project.vacation.domain.Holiday;
import alb.project.vacation.domain.HolidayItem;
import alb.project.vacation.mapper.HolidayItemMapper;
import alb.project.vacation.mapper.HolidayMapper;
import alb.project.vacation.paramsVO.HolidayUserParamsVO;
import alb.project.vacation.resultVO.HolidayUserResultVO;
import alb.project.vacation.service.IHolidayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Holiday table(Holiday)Table service implementation class
 */
@Service("holidayService")
public class HolidayServiceImpl implements IHolidayService {

    @Resource
    private HolidayMapper holidayMapper;
    @Resource
    private HolidayItemMapper holidayItemMapper;

    /**
     * Example Query a single piece of data
     *
     * @param holidayId Instance objects
     * @return Instance objects
     */
    @Override
    public Holiday queryOne(Long holidayId) {
        Holiday resultOne = this.holidayMapper.queryOne(holidayId);
        HolidayItem params = HolidayItem.builder()
                .holidayId(holidayId)
                .build();
        resultOne.setItems(this.holidayItemMapper.queryAll(params));
        return resultOne;
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holiday
     * @return
     */
    @Override
    public List<Holiday> queryAll(Holiday holiday) {
        List<Holiday> resultAll = this.holidayMapper.queryAll(holiday);
        for (Holiday one : resultAll) {
            HolidayItem params = HolidayItem.builder()
                    .holidayId(one.getHolidayId())
                    .build();
            one.setItems(this.holidayItemMapper.queryAll(params));
        }
        return resultAll;
    }

    /**
     * The new data
     *
     * @param holiday Instance objects
     * @return Instance objects
     */
    @Override
    public int insert(Holiday holiday) {
        return this.holidayMapper.insert(holiday);
    }

    /**
     * Modify the data
     *
     * @param holiday Instance objects
     * @return Instance objects
     */
    @Override
    public int update(Holiday holiday) {
        return this.holidayMapper.update(holiday);
    }

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return The success of
     */
    @Override
    public int deleteById(Long holidayId) {
        int count = this.holidayItemMapper.deleteById(holidayId);
        int countItem = this.holidayMapper.deleteById(holidayId);
        return count > 0 && countItem > 0 ? 1 : 0;
    }

    /**
     * Querying a User List
     *
     * @param holidayUserParamsVO Instance objects
     * @return Instance objects
     */
    @Override
    public List<HolidayUserResultVO> selectUserList(HolidayUserParamsVO holidayUserParamsVO){
        return this.holidayMapper.selectUserList(holidayUserParamsVO);
    }
}
