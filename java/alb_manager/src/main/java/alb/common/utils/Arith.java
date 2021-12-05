package alb.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Precise floating-point operations
 *
 */
public class Arith
{

    /** Default division accuracy */
    private static final int DEF_DIV_SCALE = 10;

    /** This class cannot be instantiated */
    private Arith()
    {
    }

    /**
     * Provides accurate addition operations。
     * @param v1 augend
     * @param v2 addend
     * @return The sum of two parameters
     */
    public static double add(double v1, double v2)
    {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * Provides accurate subtraction operations。
     * @param v1 minuend
     * @param v2 reduction
     * @return The difference between two parameters
     */
    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Provides accurate multiplication。
     * @param v1 multiplicand
     * @param v2 The multiplier
     * @return The product of two parameters
     */
    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * provide(The relative)An accurate division operation,When there is a situation that cannot be divided,Accurate to
     * After the decimal point10position,The following figures are rounded。
     * @param v1 dividend
     * @param v2 divisor
     * @return The quotient of two parameters
     */
    public static double div(double v1, double v2)
    {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * provide(The relative)An accurate division operation。When there is a situation that cannot be divided,byscaleParameter refers to
     * Accuracy of,The following figures are rounded。
     * @param v1 dividend
     * @param v2 divisor
     * @param scale Indicates to be accurate to several decimal places。
     * @return The quotient of two parameters
     */
    public static double div(double v1, double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        if (b1.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Provides accurate rounding of decimal places。
     * @param v Numbers that need to be rounded
     * @param scale Let me keep a few decimal places
     * @return The rounded result
     */
    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
