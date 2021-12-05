package alb.common.utils;

import alb.common.core.lang.UUID;

/**
 * IDGenerator utility class
 *
 */
public class IdUtils
{
    /**
     * Access to randomUUID
     * 
     * @return randomUUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * A simplifiedUUID,I got rid of the line
     * 
     * @return A simplifiedUUID,I got rid of the line
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * Access to randomUUID,Use something with better performanceThreadLocalRandomgenerateUUID
     * 
     * @return randomUUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * A simplifiedUUID,I got rid of the line,Use something with better performanceThreadLocalRandomgenerateUUID
     * 
     * @return A simplifiedUUID,I got rid of the line
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
