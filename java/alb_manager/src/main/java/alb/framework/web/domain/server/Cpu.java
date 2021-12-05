package alb.framework.web.domain.server;

import alb.common.utils.Arith;

/**
 * CPUThe relevant information
 *
 */
public class Cpu
{
    /**
     * The core number
     */
    private int cpuNum;

    /**
     * CPUTotal utilization rate
     */
    private double total;

    /**
     * CPUSystem usage
     */
    private double sys;

    /**
     * CPUUser usage
     */
    private double used;

    /**
     * CPUCurrent waiting rate
     */
    private double wait;

    /**
     * CPUCurrent idle rate
     */
    private double free;

    public int getCpuNum()
    {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum)
    {
        this.cpuNum = cpuNum;
    }

    public double getTotal()
    {
        return Arith.round(Arith.mul(total, 100), 2);
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getSys()
    {
        return Arith.round(Arith.mul(sys / total, 100), 2);
    }

    public void setSys(double sys)
    {
        this.sys = sys;
    }

    public double getUsed()
    {
        return Arith.round(Arith.mul(used / total, 100), 2);
    }

    public void setUsed(double used)
    {
        this.used = used;
    }

    public double getWait()
    {
        return Arith.round(Arith.mul(wait / total, 100), 2);
    }

    public void setWait(double wait)
    {
        this.wait = wait;
    }

    public double getFree()
    {
        return Arith.round(Arith.mul(free / total, 100), 2);
    }

    public void setFree(double free)
    {
        this.free = free;
    }
}
