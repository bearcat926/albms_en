package alb.project.vacation.mapper;

import alb.project.vacation.domain.ParamsData;

import java.util.List;

/**
 * Parameter SettingsMapperinterface
 *
 * @date 2020-07-16
 */
public interface ParamsDataMapper 
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
     * Delete parameter Settings
     * 
     * @param id Parameter SettingsID
     * @return The results of
     */
    int deleteParamsDataById(Long id);

    /**
     * Delete parameter Settings in batches
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteParamsDataByIds(Long[] ids);
}
