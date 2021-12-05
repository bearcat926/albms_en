package alb.common.core.lang;

import alb.common.exception.UtilException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides a universal unique identifier(universally unique identifier)(UUID)implementation
 *
 */
public final class UUID implements java.io.Serializable, Comparable<UUID>
{
    private static final long serialVersionUID = -1185015143654744140L;

    /**
     * SecureRandom The singleton
     *
     */
    private static class Holder
    {
        static final SecureRandom numberGenerator = getSecureRandom();
    }

    /** thisUUIDThe highest64Significant bit */
    private final long mostSigBits;

    /** thisUUIDThe minimum64Significant bit */
    private final long leastSigBits;

    /**
     * Private structure
     * 
     * @param data data
     */
    private UUID(byte[] data)
    {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++)
        {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 8; i < 16; i++)
        {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * Constructs a new one using the specified data UUID。
     *
     * @param mostSigBits Used for {@code UUID} Of maximum effectiveness 64 position
     * @param leastSigBits Used for {@code UUID} Minimum effective 64 position
     */
    public UUID(long mostSigBits, long leastSigBits)
    {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * Access to type 4(Pseudo random generated)UUID Static factory of。 This is generated using an encrypted local thread pseudorandom number generator UUID。
     * 
     * @return Randomly generated {@code UUID}
     */
    public static UUID fastUUID()
    {
        return randomUUID(false);
    }

    /**
     * Access to type 4(Pseudo random generated)UUID Static factory of。 This is generated using an encrypted strong pseudorandom number generator UUID。
     * 
     * @return Randomly generated {@code UUID}
     */
    public static UUID randomUUID()
    {
        return randomUUID(true);
    }

    /**
     * Access to type 4(Pseudo random generated)UUID Static factory of。 This is generated using an encrypted strong pseudorandom number generator UUID。
     * 
     * @param isSecure Whether to use{@link SecureRandom}If so, you can get a more secure random code,Otherwise you get better performance
     * @return Randomly generated {@code UUID}
     */
    public static UUID randomUUID(boolean isSecure)
    {
        final Random ng = isSecure ? Holder.numberGenerator : getRandom();

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(randomBytes);
    }

    /**
     * Gets the type based on the specified byte array 3(name-based)UUID Static factory of。
     *
     * @param name Is used to construct UUID Byte array of。
     *
     * @return Generated from the specified array {@code UUID}
     */
    public static UUID nameUUIDFromBytes(byte[] name)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae)
        {
            throw new InternalError("MD5 not supported");
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] &= 0x0f; /* clear version */
        md5Bytes[6] |= 0x30; /* set to version 3 */
        md5Bytes[8] &= 0x3f; /* clear variant */
        md5Bytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(md5Bytes);
    }

    /**
     * According to the {@link #toString()} Method to create a standard representation of the string described in{@code UUID}。
     *
     * @param name The specified {@code UUID} string
     * @return Having the specified value {@code UUID}
     * @throws IllegalArgumentException if name with {@link #toString} Does not match the string representation described in
     *
     */
    public static UUID fromString(String name)
    {
        String[] components = name.split("-");
        if (components.length != 5)
        {
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        }
        for (int i = 0; i < 5; i++)
        {
            components[i] = "0x" + components[i];
        }

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * Return to the UUID the 128 position值中the最低有效 64 position。
     *
     * @return this UUID the 128 position值中the最低有效 64 position。
     */
    public long getLeastSignificantBits()
    {
        return leastSigBits;
    }

    /**
     * Return to the UUID the 128 position值中the最高有效 64 position。
     *
     * @return this UUID the 128 The bit value is the highest valid value 64 position。
     */
    public long getMostSignificantBits()
    {
        return mostSigBits;
    }

    /**
     * With this {@code UUID} Associated version number. The version number describes this {@code UUID} How is it generated。
     * <p>
     * The version number has the following implications:
     * <ul>
     * <li>1 time-based UUID
     * <li>2 DCE security UUID
     * <li>3 name-based UUID
     * <li>4 Randomly generated UUID
     * </ul>
     *
     * @return this {@code UUID} The version number of the
     */
    public int version()
    {
        // Version is bits masked by 0x000000000000F000 in MS long
        return (int) ((mostSigBits >> 12) & 0x0f);
    }

    /**
     * With this {@code UUID} Associated variant number。Variant Number description {@code UUID} The layout of the。
     * <p>
     * The variant number has the following implications:
     * <ul>
     * <li>0 for NCS Backward compatible reservation
     * <li>2 <a href="http://www.ietf.org/rfc/rfc4122.txt">IETF&nbsp;RFC&nbsp;4122</a>(Leach-Salz), For such
     * <li>6 keep,Microsoft backward compatibility
     * <li>7 Reserved for later definition
     * </ul>
     *
     * @return this {@code UUID} Associated variant number
     */
    public int variant()
    {
        // This field is composed of a varying number of bits.
        // 0 - - Reserved for NCS backward compatibility
        // 1 0 - The IETF aka Leach-Salz variant (used by this class)
        // 1 1 0 Reserved, Microsoft backward compatibility
        // 1 1 1 Reserved for future definition.
        return (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62))) & (leastSigBits >> 63));
    }

    /**
     * With this UUID Associated timestamp value。
     *
     * <p>
     * 60 Bit timestamp value based on this {@code UUID} the time_low、time_mid and time_hi Field construction。<br>
     * The obtained time stamp to 100 Nanoseconds,from UTC(Universal coordinated time) 1582 years 10 month 15 The day begins at midnight。
     *
     * <p>
     * Timestamp values are only available in time-based UUID(its version A type of 1)Is meaningful。<br>
     * If this {@code UUID} It's not time-based UUID,This method throws UnsupportedOperationException。
     *
     * @throws UnsupportedOperationException If this {@code UUID} not version for 1 the UUID。
     */
    public long timestamp() throws UnsupportedOperationException
    {
        checkTimeBase();
        return (mostSigBits & 0x0FFFL) << 48//
                | ((mostSigBits >> 16) & 0x0FFFFL) << 32//
                | mostSigBits >>> 32;
    }

    /**
     * With this UUID The associated clock sequence value。
     *
     * <p>
     * 14 Bit clock sequence values according to this UUID the clock_seq Field construction。clock_seq 字段用于保证在基于时间the UUID 中the时间唯一性。
     * <p>
     * {@code clockSequence} Value only in time-based UUID(its version A type of 1)Is meaningful。 If this UUID It's not time-based UUID,This method throws
     * UnsupportedOperationException。
     *
     * @return this {@code UUID} Clock sequence of
     *
     * @throws UnsupportedOperationException If this UUID the version Don't for 1
     */
    public int clockSequence() throws UnsupportedOperationException
    {
        checkTimeBase();
        return (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
    }

    /**
     * With this UUID Associated node values。
     *
     * <p>
     * 48 Bit node values according to this UUID the node Field construction。此字段旨在用于保存机器the IEEE 802 address,该address用于生成此 UUID To keep the space unique。
     * <p>
     * The node value is only in time-based UUID(its version A type of 1)Is meaningful。<br>
     * If this UUID It's not time-based UUID,This method throws UnsupportedOperationException。
     *
     * @return this {@code UUID} The node values
     *
     * @throws UnsupportedOperationException If this UUID the version Don't for 1
     */
    public long node() throws UnsupportedOperationException
    {
        checkTimeBase();
        return leastSigBits & 0x0000FFFFFFFFFFFFL;
    }

    /**
     * Return to the{@code UUID} Is a string representation of。
     *
     * <p>
     * UUID The string representation is thus BNF describe:
     * 
     * <pre>
     * {@code
     * UUID                   = <time_low>-<time_mid>-<time_high_and_version>-<variant_and_sequence>-<node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               = [0-9a-fA-F]
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @return this{@code UUID} Is a string representation of
     * @see #toString(boolean)
     */
    @Override
    public String toString()
    {
        return toString(false);
    }

    /**
     * Return to the{@code UUID} Is a string representation of。
     *
     * <p>
     * UUID The string representation is thus BNF describe:
     * 
     * <pre>
     * {@code
     * UUID                   = <time_low>-<time_mid>-<time_high_and_version>-<variant_and_sequence>-<node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               = [0-9a-fA-F]
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param isSimple Simple mode,In simple mode, no'-'theUUIDstring
     * @return this{@code UUID} Is a string representation of
     */
    public String toString(boolean isSimple)
    {
        final StringBuilder builder = new StringBuilder(isSimple ? 32 : 36);
        // time_low
        builder.append(digits(mostSigBits >> 32, 8));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // time_mid
        builder.append(digits(mostSigBits >> 16, 4));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // time_high_and_version
        builder.append(digits(mostSigBits, 4));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // variant_and_sequence
        builder.append(digits(leastSigBits >> 48, 4));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // node
        builder.append(digits(leastSigBits, 12));

        return builder.toString();
    }

    /**
     * Return to the UUID The hash code。
     *
     * @return UUID Hash code value of。
     */
    @Override
    public int hashCode()
    {
        long hilo = mostSigBits ^ leastSigBits;
        return ((int) (hilo >> 32)) ^ (int) hilo;
    }

    /**
     * Compares this object with the specified object。
     * <p>
     * If and only if the parameter is not {@code null}、It is a UUID object、With this UUID The same varriant、包含The same值(Each one is the same)when,The results for {@code true}。
     *
     * @param obj Something to be compared with
     *
     * @return If the objects are the same,It returns {@code true};否It returns {@code false}
     */
    @Override
    public boolean equals(Object obj)
    {
        if ((null == obj) || (obj.getClass() != UUID.class))
        {
            return false;
        }
        UUID id = (UUID) obj;
        return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
    }

    // Comparison Operations

    /**
     * Will this UUID With the specified UUID To compare。
     *
     * <p>
     * If the two UUID different,And the first UUID Is greater than the second UUID Corresponding field of,Is the first UUID Greater than the second UUID。
     *
     * @param val With this UUID To compare the UUID
     *
     * @return In this UUID Less than、Equal to or greater than val when,Return, respectively, -1、0 or 1。
     *
     */
    @Override
    public int compareTo(UUID val)
    {
        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 : //
                (this.mostSigBits > val.mostSigBits ? 1 : //
                        (this.leastSigBits < val.leastSigBits ? -1 : //
                                (this.leastSigBits > val.leastSigBits ? 1 : //
                                        0))));
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Private method start
    /**
     * Returns the corresponding to the specified numberhexvalue
     * 
     * @param val value
     * @param digits position
     * @return value
     */
    private static String digits(long val, int digits)
    {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * Check whethertime-basedversionUUID
     */
    private void checkTimeBase()
    {
        if (version() != 1)
        {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
    }

    /**
     * To obtain{@link SecureRandom},Class provides an encrypted strong random number generator (RNG)
     * 
     * @return {@link SecureRandom}
     */
    public static SecureRandom getSecureRandom()
    {
        try
        {
            return SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new UtilException(e);
        }
    }

    /**
     * Gets the random number generator object<br>
     * ThreadLocalRandomisJDK 7Then provide concurrent generation of random numbers,Can solve multiple threads contention。
     * 
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom()
    {
        return ThreadLocalRandom.current();
    }
}
