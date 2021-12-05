package alb.project.vacation.service.impl;

import alb.project.vacation.domain.ParamsData;
import alb.project.vacation.mapper.ParamsDataMapper;
import alb.project.vacation.service.IParamsDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Parameter SettingsServiceBusiness layer processing
 *
 * @date 2020-07-16
 */
@Service
public class ParamsDataServiceImpl implements IParamsDataService {

    private final ParamsDataMapper paramsDataMapper;

    public ParamsDataServiceImpl(ParamsDataMapper paramsDataMapper) {
        this.paramsDataMapper = paramsDataMapper;
    }

    /**
     * Querying Parameter Settings
     *
     * @param id Parameter SettingsID
     * @return Parameter Settings
     */
    @Override
    public ParamsData selectParamsDataById(Long id) {
        return paramsDataMapper.selectParamsDataById(id);
    }

    /**
     * Example Query the parameter list
     *
     * @param paramsData Parameter Settings
     * @return Parameter Settings
     */
    @Override
    public List<ParamsData> selectParamsDataList(ParamsData paramsData) {
        return paramsDataMapper.selectParamsDataList(paramsData);
    }

    /**
     * New Parameter Settings
     *
     * @param paramsData Parameter Settings
     * @return The results of
     */
    @Override
    public int insertParamsData(ParamsData paramsData) {
        return paramsDataMapper.insertParamsData(paramsData);
    }

    /**
     * Modifying Parameter Settings
     *
     * @param paramsData Parameter Settings
     * @return The results of
     */
    @Override
    public int updateParamsData(ParamsData paramsData) {
        return paramsDataMapper.updateParamsData(paramsData);
    }

    /**
     * Delete parameter Settings in batches
     *
     * @param ids Parameter Settings that you want to deleteID
     * @return The results of
     */
    @Override
    public int deleteParamsDataByIds(Long[] ids) {
        return paramsDataMapper.deleteParamsDataByIds(ids);
    }

    /**
     * Example Delete parameter Settings
     *
     * @param id Parameter SettingsID
     * @return The results of
     */
    @Override
    public int deleteParamsDataById(Long id) {
        return paramsDataMapper.deleteParamsDataById(id);
    }
}
