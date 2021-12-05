package alb.common.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Twitter_Snowflake<br>
 * SnowFlakeThe structure of(Each part with-separate):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1A logo,Due to thelongBasic type inJavaIs signed,The highest bit is the sign bit,A positive number is0,A negative number is1,soidIt's usually positive,Highest level is0<br>
 * 41A time to cut(millisecond),Pay attention to,41A time to cut不是存储当前时间的时间截,It's the difference of the storage time intercept(Current time cut - Start time cut)
 * Get the value of the),This is the start time here,Usually oursidThe time when the generator was in use,It's specified by our program(The following procedureIdWorkerOf the classstartTimeattribute)。41Bit time cutoff,You can use69years,yearsT = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10Bit data machine bit,Can be deployed in1024A node,including5positiondatacenterIdand5positionworkerId<br>
 * 12A sequence of,Count in milliseconds,12Bit count sequence numbers are supported per node per millisecond(The same machine,Same time cut)produce4096aIDThe serial number<br>
 * It just adds up64position,As aLongtype。<br>
 * SnowFlakeThe advantage of,The whole thing is sort by time increment,And it does not occur throughout the distributed systemIDThe collision(By the data centerIDAnd the machineIDDifferentiate between),And it's more efficient,After the test,SnowFlakeCan be generated per second26wanIDOr so。
 */
public class IDKeyUtil {

    // ==============================Fields===========================================
    /** Start time cut (2015-01-01) */
    private final long twepoch = 1489111610226L;

    /** The machineidThe number of digits */
    private final long workerIdBits = 5L;

    /** Data identificationidThe number of digits */
    private final long dataCenterIdBits = 5L;

    /** Maximum machine supportedid,As a result,31 (This shift algorithm can quickly calculate the largest decimal number represented by several binary digits) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** Maximum data id supportedid,As a result,31 */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /** Sequence inidThe median number of digits */
    private final long sequenceBits = 12L;

    /** The machineIDMoving to the left12position */
    private final long workerIdShift = sequenceBits;

    /** Data identificationidMoving to the left17position(12+5) */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /** Time cut shifted to the left22position(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /** Generates the mask of the sequence,Here for4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** Work the machineID(0~31) */
    private final long workerId;

    /** The data centerID(0~31) */
    private final long dataCenterId;

    /** Millisecond sequence(0~4095) */
    private long sequence = 0L;

    /** The last generationIDTime to cut */
    private long lastTimestamp = -1L;

    private static final IDKeyUtil idKeyUtil;

    static {
        idKeyUtil = new IDKeyUtil(getWorkId(),getDataCenterId());
    }

    //==============================Constructors=====================================
    /**
     * The constructor
     * @param workerId workID (0~31)
     * @param dataCenterId The data centerID (0~31)
     */
    public IDKeyUtil(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    // ==============================Methods==========================================
    /**
     * Get the next oneID (This method is thread-safe)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //If the current time is less than the last timeIDGenerated timestamp,When the system clock is rolled back, an exception should be thrown
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //If they were all generated at the same time,Performs a sequence within milliseconds
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //Sequence overflow in milliseconds
            if (sequence == 0) {
                //Block until the next millisecond,Get a new timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //Timestamp change,Sequence reset in milliseconds
        else {
            sequence = 0L;
        }

        //The last generationIDTime to cut
        lastTimestamp = timestamp;

        //Shifts and are pieced together by or operations64bitID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * Block until the next millisecond,Until you get a new timestamp
     * @param lastTimestamp The last generationIDTime to cut
     * @return Current timestamp
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Returns the current time in milliseconds
     * @return The current time(ms)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private static Long getWorkId(){
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for(int b : ints){
                sums += b;
            }
            return (long)(sums % 32);
        } catch (UnknownHostException e) {
            // If fetching fails,A random number is used for backup
            return RandomUtils.nextLong(0,31);
        }
    }

    private static Long getDataCenterId(){
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        int sums = 0;
        for (int i: ints) {
            sums += i;
        }
        return (long)(sums % 32);
    }


    /**
     * Static utility class
     *
     * @return
     */
    public static Long generateId(){
        return idKeyUtil.nextId();
    }

}
