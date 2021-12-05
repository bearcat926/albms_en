package alb.project.vacation.service;

import java.util.List;

import alb.project.vacation.domain.ParamsData;

/**
 * Parameter SettingsServiceinterface
 *
 * @date 2020-07-16
 */
public interface IParamsDataService 
{
    /**
     * Querying Parameter Settings
     * 
     * @param id Parameter SettingsID
     * @return Parameter Settings
     */
    ParamsData selectParamsDataById(Long id);

    /**
     * Example Query the parameter list
     * 
     * @param paramsData Parameter Settings
     * @return Set of parameters
     */
    List<ParamsData> selectParamsDataList(ParamsData paramsData);

    /**
     * New Parameter Settings
     * 
     * @param paramsData Parameter Settings
     * @return The results of
     */
    int insertParamsData(ParamsData paramsData);

    /**
     * Modifying Parameter Settings
     * 
     * @param paramsData Parameter Settings
     * @return The results of
     */
    int updateParamsData(ParamsData paramsData);

    /**
     * Delete parameter Settings in batches
     * 
     * @param ids Parameter Settings that you want to deleteID
     * @return The results of
     */
    int deleteParamsDataByIds(Long[] ids);

    /**
     * Example Delete parameter Settings
     * 
     * @param id Parameter SettingsID
     * @return The results of
     */
    int deleteParamsDataById(Long id);
}
