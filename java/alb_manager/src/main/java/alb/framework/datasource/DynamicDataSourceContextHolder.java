package alb.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data source switching
 *
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * useThreadLocalMaintain the variable,ThreadLocal为每个use该变量的线程提供独立的变量副本,
     *  So each thread can change its own copy independently,It does not affect the corresponding copy of other threads。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * Set variables for the data source
     */
    public static void setDataSourceType(String dsType)
    {
        log.info("Switch to the{}The data source", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * Get the variables for the data source
     */
    public static String getDataSourceType()
    {
        return CONTEXT_HOLDER.get();
    }

    /**
     * Clearing data source variables
     */
    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }
}
