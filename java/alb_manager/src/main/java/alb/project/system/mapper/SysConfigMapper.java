package alb.project.system.mapper;

import java.util.List;
import alb.project.system.domain.SysConfig;

/**
 * Parameter configuration The data layer
 *
 */
public interface SysConfigMapper
{
    /**
     * Example Query parameter configurations
     * 
     * @param config Parameter Configuration
     * @return Parameter Configuration
     */
    SysConfig selectConfig(SysConfig config);

    /**
     * Example Query the parameter list
     * 
     * @param config Parameter Configuration
     * @return Parameter Configuration Set
     */
    List<SysConfig> selectConfigList(SysConfig config);

    /**
     * Query parameter configurations based on key names
     * 
     * @param configKey Parameters of the key name
     * @return Parameter Configuration
     */
    SysConfig checkConfigKeyUnique(String configKey);

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
     * Deleting Parameter Settings
     * 
     * @param configId parameterID
     * @return The results of
     */
    int deleteConfigById(Long configId);

    /**
     * Delete parameter information in batches
     * 
     * @param configIds Parameter to deleteID
     * @return The results of
     */
    int deleteConfigByIds(Long[] configIds);
}