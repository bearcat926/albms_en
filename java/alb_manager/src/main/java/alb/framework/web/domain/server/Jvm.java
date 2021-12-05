package alb.framework.web.domain.server;

import java.lang.management.ManagementFactory;
import alb.common.utils.Arith;
import alb.common.utils.DateUtils;

/**
 * JVMThe relevant information
 *
 */
public class Jvm
{
    /**
     * The currentJVMTotal memory occupied(M)
     */
    private double total;

    /**
     * JVMMaximum amount of available memory(M)
     */
    private double max;

    /**
     * JVMFree memory(M)
     */
    private double free;

    /**
     * JDKversion
     */
    private String version;

    /**
     * JDKThe path
     */
    private String home;

    public double getTotal()
    {
        return Arith.div(total, (1024 * 1024), 2);
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getMax()
    {
        return Arith.div(max, (1024 * 1024), 2);
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getFree()
    {
        return Arith.div(free, (1024 * 1024), 2);
    }

    public void setFree(double free)
    {
        this.free = free;
    }

    public double getUsed()
    {
        return Arith.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage()
    {
        return Arith.mul(Arith.div(total - free, total, 4), 100);
    }

    /**
     * To obtainJDKThe name of the
     */
    public String getName()
    {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getHome()
    {
        return home;
    }

    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * JDKThe startup time
     */
    public String getStartTime()
    {
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
    }

    /**
     * JDKThe elapsed time
     */
    public String getRunTime()
    {
        return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
    }
}
