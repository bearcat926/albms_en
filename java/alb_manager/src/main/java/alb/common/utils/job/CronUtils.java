package alb.common.utils.job;

import java.text.ParseException;
import java.util.Date;
import org.quartz.CronExpression;

/**
 * cronExpression utility class
 *
 *
 */
public class CronUtils
{
    /**
     * Returns a Boolean value representing a givenCronValidity of an expression
     *
     * @param cronExpression Cronexpression
     * @return boolean Whether the expression is valid
     */
    public static boolean isValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * Returns a string value,Indicates that the message is invalidCronExpression gives validity
     *
     * @param cronExpression Cronexpression
     * @return String Returns an expression error description when invalid,If it is validnull
     */
    public static String getInvalidMessage(String cronExpression)
    {
        try
        {
            new CronExpression(cronExpression);
            return null;
        }
        catch (ParseException pe)
        {
            return pe.getMessage();
        }
    }

    /**
     * Returns the next execution time based on the givenCronexpression
     *
     * @param cronExpression Cronexpression
     * @return Date The next timeCronExpression execution time
     */
    public static Date getNextExecution(String cronExpression)
    {
        try
        {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
