package alb.common.utils.sql;

import alb.common.utils.StringUtils;

/**
 * sqlOperation tool class
 *
 */
public class SqlUtil
{
    /**
     * Letters only、digital、The underline、The blank space、The comma(Multiple field sorting is supported)
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    /**
     * Check the characters,Preventing injection bypass
     */
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * validation order by Does the syntax conform to the specification
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }
}
