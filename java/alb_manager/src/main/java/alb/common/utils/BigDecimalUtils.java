package alb.common.utils;

import com.google.common.base.Optional;

import java.math.BigDecimal;

/**
 * @ClassName : BigDecimalUtils  //The name of the class
 * @Description : decimalUtility class  //describe
 * @Date: 2019-12-10 14:05  //time
 */
public class BigDecimalUtils {


    /**
     * Is greater than
     * @param b1 The operand1
     * @param b2 The operand2
     * @return The results of
     */
    public static boolean greaterThan(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = new BigDecimal(0);
        }
        if (b2 == null) {
            b2 = new BigDecimal(0);
        }
        return b1.compareTo(b2)>0;
    }

    /**
     * Greater than or equal to
     * @param b1 The operand1
     * @param b2 The operand2
     * @return The results of
     */
    public static boolean greaterOrEquals(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = new BigDecimal(0);
        }
        if (b2 == null) {
            b2 = new BigDecimal(0);
        }
        return b1.compareTo(b2)>=0;
    }

    /**
     * Less than
     * @param b1 The operand1
     * @param b2 The operand2
     * @return The results of
     */
    public static boolean lessThan(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = new BigDecimal(0);
        }
        if (b2 == null) {
            b2 = new BigDecimal(0);
        }
        return b1.compareTo(b2) < 0;
    }

    /**
     * Less than or equal to
     * @param b1 The operand1
     * @param b2 The operand2
     * @return The results of
     */
    public static boolean lessOrEquals(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = new BigDecimal(0);
        }
        if (b2 == null) {
            b2 = new BigDecimal(0);
        }
        return b1.compareTo(b2) <= 0;
    }

    /**
     * Is equal to the
     * @param b1 The operand1
     * @param b2 The operand2
     * @return The results of
     */
    public static boolean equals(BigDecimal b1, BigDecimal b2) {
        if (b1 == null) {
            b1 = new BigDecimal(0);
        }
        if (b2 == null) {
            b2 = new BigDecimal(0);
        }
        return b1.compareTo(b2) == 0;
    }

//    public static Integer compare(BigDecimal a,BigDecimal b){
//        Integer i = null;
//        //usecompareToMethods to compare
//        //Pay attention to:a、bAre not asnull,Otherwise a null pointer is reported
//        if(a.compareTo(b) == -1){
//            i = -1;
////            System.out.println("aLess thanb");
//        }
//
//        if(a.compareTo(b) == 0){
//            i = 0;
////            System.out.println("aIs equal to theb");
//        }
//
//        if(a.compareTo(b) == 1){
//            i = 1;
////            System.out.println("aIs greater thanb");
//        }
//
//        if(a.compareTo(b) > -1){
//            i = 2;
////            System.out.println("aGreater than or equal tob");
//        }
//
//        if(a.compareTo(b) < 1){
//            i = 3;
////            System.out.println("aLess than or equal tob");
//        }
//        return i;
//    }

    /**
     * BigDecimalThe addition operation encapsulation of
     * @param b1
     * @param bn
     * @return
     */
    public static BigDecimal safeAdd(BigDecimal b1, BigDecimal... bn) {
        if (null == b1) {
            b1 = BigDecimal.ZERO;
        }
        if (null != bn) {
            for (BigDecimal b : bn) {
                b1 = b1.add(null == b ? BigDecimal.ZERO : b);
            }
        }
        return b1;
    }

    /**
     * IntegerEncapsulation of addition operations
     * @param b1   The first number
     * @param bn   The additive array to add
     * @note : Optional  It is to belong tocom.google.common.base.Optional<T> The followingclass
     * @return
     */
    public static Integer safeAdd(Integer b1, Integer... bn) {
        if (null == b1) {
            b1 = 0;
        }
        Integer r = b1;
        if (null != bn) {
            for (Integer b : bn) {
                r += Optional.fromNullable(b).or(0);
            }
        }
        return r > 0 ? r : 0;
    }

    /**
     * Calculation method
     * @param b1
     * @param bn
     * @return
     */
    public static BigDecimal safeSubtract(BigDecimal b1, BigDecimal... bn) {
        return safeSubtract(true, b1, bn);
    }

    /**
     * BigDecimalSafety subtraction operation
     * @param isZero  Whether to return if the subtraction result is negative0,trueIs to return0(For the calculation of the amount),falseIs to return负数结果
     * @param b1		   minuend
     * @param bn        The subtraction array to subtract
     * @return
     */
    public static BigDecimal safeSubtract(Boolean isZero, BigDecimal b1, BigDecimal... bn) {
        if (null == b1) {
            b1 = BigDecimal.ZERO;
        }
        BigDecimal r = b1;
        if (null != bn) {
            for (BigDecimal b : bn) {
                r = r.subtract((null == b ? BigDecimal.ZERO : b));
            }
        }
        return isZero ? (r.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : r) : r;
    }

    /**
     * The subtraction operation of an integer,Less than0When to return to0
     * @param b1
     * @param bn
     * @return
     */
    public static Integer safeSubtract(Integer b1, Integer... bn) {
        if (null == b1) {
            b1 = 0;
        }
        Integer r = b1;
        if (null != bn) {
            for (Integer b : bn) {
                r -= Optional.fromNullable(b).or(0);
            }
        }
        return null != r && r > 0 ? r : 0;
    }

    /**
     * Amount division calculation,return2Decimal places(具体的return多少位大家自己看着改吧)
     * @param b1
     * @param b2
     * @return
     */
    public static <T extends Number> BigDecimal safeDivide(T b1, T b2){
        return safeDivide(b1, b2, BigDecimal.ZERO);
    }

    /**
     * BigDecimalDivision operation encapsulation,If the divisor or dividend is zero0,Return the default value
     * Default return after decimal place2position,For amount calculation
     * @param b1
     * @param b2
     * @param defaultValue
     * @return
     */
    public static <T extends Number> BigDecimal safeDivide(T b1, T b2, BigDecimal defaultValue) {
        if (null == b1 || null == b2) {
            return defaultValue;
        }
        try {
            return BigDecimal.valueOf(b1.doubleValue()).divide(BigDecimal.valueOf(b2.doubleValue()), 2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * BigDecimalThe multiplication operation encapsulation of
     * @param b1
     * @param b2
     * @return
     */
    public static <T extends Number> BigDecimal safeMultiply(T b1, T b2) {
        if (null == b1 || null == b2) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(b1.doubleValue()).multiply(BigDecimal.valueOf(b2.doubleValue())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
