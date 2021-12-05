package alb.project.system.service.impl;

import alb.common.constant.Constants;
import alb.common.constant.UserConstants;
import alb.project.system.domain.SysConfig;
import alb.project.system.mapper.SysConfigMapper;
import alb.project.system.service.ISysConfigService;
import alb.common.core.text.Convert;
import alb.common.utils.StringUtils;
import alb.framework.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * Parameter configuration Service layer implementation
 *
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService
{
    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * At project launch,Initialize parameters to the cache
     */
    @PostConstruct
    public void init()
    {
        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * Example Query parameter configurations
     * 
     * @param configId Parameter configurationID
     * @return Parameter Configuration
     */
    @Override
    public SysConfig selectConfigById(Long configId)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * Query parameter configuration objects by key name
     *
     * @param configKey parameterkey
     * @return Parameter Configuration
     */
    @Override
    public SysConfig selectConfigObjectByKey(String configKey)
    {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        return configMapper.selectConfig(config);
    }

    /**
     * Query parameter configurations based on key names
     * 
     * @param configKey parameterkey
     * @return Parameters of the key value
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * Example Query the parameter list
     * 
     * @param config Parameter Configuration
     * @return Parameter Configuration Set
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * New Parameter Configuration
     * 
     * @param config Parameter Configuration
     * @return The results of
     */
    @Override
    public int insertConfig(SysConfig config)
    {
        int row = configMapper.insertConfig(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * Modifying Parameter Settings
     * 
     * @param config Parameter Configuration
     * @return The results of
     */
    @Override
    public int updateConfig(SysConfig config)
    {
        int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * Delete parameter information in batches
     * 
     * @param configIds Parameter to deleteID
     * @return The results of
     */
    @Override
    public int deleteConfigByIds(Long[] configIds)
    {
        int count = configMapper.deleteConfigByIds(configIds);
        if (count > 0)
        {
            Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
            redisCache.deleteObject(keys);
        }
        return count;
    }

    /**
     * Clearing cached data
     */
    @Override
    public void clearCache()
    {
        Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * Verify whether the parameter key name is unique
     * 
     * @param config Parameter Configuration
     * @return The results of
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Set up thecache key
     * 
     * @param configKey The parameters of the key
     * @return Cache keykey
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
