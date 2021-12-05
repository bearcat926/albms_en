package alb.project.vacation.service.impl;

import alb.project.vacation.domain.HolidayItem;
import alb.project.vacation.mapper.HolidayItemMapper;
import alb.project.vacation.service.IHolidayItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Holiday schedule(HolidayItem)Table service implementation class
 */
@Service("holidayItemService")
public class HolidayItemServiceImpl implements IHolidayItemService {

    @Resource
    private HolidayItemMapper holidayItemMapper;

    /**
     * Example Query a single piece of data
     *
     * @param holidayItem Instance objects
     * @return Instance objects
     */
    @Override
    public HolidayItem queryOne(HolidayItem holidayItem) {
        return this.holidayItemMapper.queryOne(holidayItem);
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holidayItem
     * @return
     */
    @Override
    public List<HolidayItem> queryAll(HolidayItem holidayItem) {
        return this.holidayItemMapper.queryAll(holidayItem);
    }

    /**
     * The new data
     *
     * @param holidayItem Instance objects
     * @return Instance objects
     */
    @Override
    public int insert(HolidayItem holidayItem) {
        // Snowflake algorithm generates unique call log numbers Individual services The data centeridAnd terminalidAll fill in1
        return this.holidayItemMapper.insert(holidayItem);
    }

    /**
     * Modify the data
     *
     * @param holidayItem Instance objects
     * @return Instance objects
     */
    @Override
    public int update(HolidayItem holidayItem) {
        return this.holidayItemMapper.update(holidayItem);
    }

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return The success of
     */
    @Override
    public int deleteById(Long holidayId) {
        return this.holidayItemMapper.deleteById(holidayId);
    }
}
