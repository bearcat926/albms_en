package alb.framework.task;

import org.springframework.stereotype.Component;
import alb.common.utils.StringUtils;

/**
 * Scheduled task scheduling test
 *
 */
@Component("testTask")
public class TestTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("Execute the multi-parameter method: String type{},Boolean type{},Long integer{},floating-point{},plastic{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("Executes the parameter method:" + params);
    }

    public void ryNoParams()
    {
        System.out.println("Execute the no-argument method");
    }
}
