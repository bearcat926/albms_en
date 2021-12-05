package alb.common.utils;

import java.util.Collection;
import java.util.List;

import alb.common.constant.Constants;
import alb.common.utils.spring.SpringUtils;
import alb.framework.redis.RedisCache;
import alb.project.system.domain.SysDictData;

/**
 * Dictionary utility class
 *
 */
public class DictUtils
{
    /**
     * Setting up dictionary cache
     * 
     * @param key The parameters of the key
     * @param dictDatas Dictionary data list
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * Get the dictionary cache
     * 
     * @param key The parameters of the key
     * @return dictDatas Dictionary data list
     */
    public static List<SysDictData> getDictCache(String key)
    {
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            List<SysDictData> DictDatas = StringUtils.cast(cacheObj);
            return DictDatas;
        }
        return null;
    }

    /**
     * Clearing the dictionary cache
     */
    public static void clearDictCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * Set up thecache key
     * 
     * @param configKey The parameters of the key
     * @return Cache keykey
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
