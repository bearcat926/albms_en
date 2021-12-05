package alb.common.constant;

/**
 * Task scheduling generic constants
 *
 */
public class ScheduleConstants
{
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /** Performance targetskey */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /** The default */
    public static final String MISFIRE_DEFAULT = "0";

    /** Trigger execution immediately */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** Trigger an execution */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** Immediate execution is not triggered */
    public static final String MISFIRE_DO_NOTHING = "3";

    public enum Status
    {
        /**
         * normal
         */
        NORMAL("0"),
        /**
         * suspended
         */
        PAUSE("1");

        private final String value;

        Status(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }
}
