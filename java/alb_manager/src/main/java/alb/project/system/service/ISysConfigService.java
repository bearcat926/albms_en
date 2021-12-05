package alb.project.system.service;

import alb.project.system.domain.SysConfig;

import java.util.List;

/**
 * Parameter configuration The service layer
 *
 */
public interface ISysConfigService {
    /**
     * Example Query parameter configurations
     *
     * @param configId Parameter configurationID
     * @return Parameter Configuration
     */
    SysConfig selectConfigById(Long configId);

    /**
     * Query parameter configuration objects by key name
     *
     * @param configKey parameterkey
     * @return Parameter Configuration
     */
    SysConfig selectConfigObjectByKey(String configKey);

    /**
     * Query parameter configurations based on key names
     *
     * @param configKey Parameters of the key name
     * @return Parameters of the key value
     */
    String selectConfigByKey(String configKey);

    /**
     * Example Query the parameter list
     *
     * @param config Parameter Configuration
     * @return Parameter Configuration Set
     */
    List<SysConfig> selectConfigList(SysConfig config);

    /**
     * New Parameter Configuration
     *
     * @param config Parameter Configuration
     * @return The results of
     */
    int insertConfig(SysConfig config);

    /**
     * Modifying Parameter Settings
     *
     * @param config Parameter Configuration
     * @return The results of
     */
    int updateConfig(SysConfig config);

    /**
     * Delete parameter information in batches
     *
     * @param configIds Parameter to deleteID
     * @return The results of
     */
    int deleteConfigByIds(Long[] configIds);

    /**
     * Clearing cached data
     */
    void clearCache();

    /**
     * Verify whether the parameter key name is unique
     *
     * @param config Parameter information
     * @return The results of
     */
    String checkConfigKeyUnique(SysConfig config);
}
