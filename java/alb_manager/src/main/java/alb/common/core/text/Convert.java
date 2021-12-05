package alb.common.core.text;

import alb.common.utils.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Set;

/**
 * Type converter
 *
 */
public class Convert
{
    /**
     * Convert to string<br>
     * If the given value is zeronull,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static String toStr(Object value, String defaultValue)
    {
        if (null == value)
        {
            return defaultValue;
        }
        if (value instanceof String)
        {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * Convert to string<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static String toStr(Object value)
    {
        return toStr(value, null);
    }

    /**
     * Convert to character<br>
     * If the given value is zeronull,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Character toChar(Object value, Character defaultValue)
    {
        if (null == value)
        {
            return defaultValue;
        }
        if (value instanceof Character)
        {
            return (Character) value;
        }

        final String valueStr = toStr(value, null);
        return StringUtils.isEmpty(valueStr) ? defaultValue : valueStr.charAt(0);
    }

    /**
     * Convert to character<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Character toChar(Object value)
    {
        return toChar(value, null);
    }

    /**
     * convertbyte<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Byte toByte(Object value, Byte defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Byte)
        {
            return (Byte) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).byteValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Byte.parseByte(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertbyte<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Byte toByte(Object value)
    {
        return toByte(value, null);
    }

    /**
     * convertShort<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Short toShort(Object value, Short defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Short)
        {
            return (Short) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).shortValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Short.parseShort(valueStr.trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertShort<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Short toShort(Object value)
    {
        return toShort(value, null);
    }

    /**
     * convertNumber<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Number toNumber(Object value, Number defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Number)
        {
            return (Number) value;
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return NumberFormat.getInstance().parse(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertNumber<br>
     * If the given value is null,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Number toNumber(Object value)
    {
        return toNumber(value, null);
    }

    /**
     * convertint<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Integer toInt(Object value, Integer defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Integer)
        {
            return (Integer) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).intValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(valueStr.trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertint<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Integer toInt(Object value)
    {
        return toInt(value, null);
    }

    /**
     * convertIntegerAn array of<br>
     * 
     * @param str The value to be converted
     * @return The results of
     */
    public static Integer[] toIntArray(String str)
    {
        return toIntArray(",", str);
    }

    /**
     * convertLongAn array of<br>
     * 
     * @param str The value to be converted
     * @return The results of
     */
    public static Long[] toLongArray(String str)
    {
        return toLongArray(",", str);
    }

    /**
     * convertIntegerAn array of<br>
     * 
     * @param split The separator
     * @param split The value to be converted
     * @return The results of
     */
    public static Integer[] toIntArray(String split, String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return new Integer[] {};
        }
        String[] arr = str.split(split);
        final Integer[] ints = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            final Integer v = toInt(arr[i], 0);
            ints[i] = v;
        }
        return ints;
    }

    /**
     * convertLongAn array of<br>
     * 
     * @param split The separator
     * @param str The value to be converted
     * @return The results of
     */
    public static Long[] toLongArray(String split, String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return new Long[] {};
        }
        String[] arr = str.split(split);
        final Long[] longs = new Long[arr.length];
        for (int i = 0; i < arr.length; i++)
        {
            final Long v = toLong(arr[i], null);
            longs[i] = v;
        }
        return longs;
    }

    /**
     * convertStringAn array of<br>
     * 
     * @param str The value to be converted
     * @return The results of
     */
    public static String[] toStrArray(String str)
    {
        return toStrArray(",", str);
    }

    /**
     * convertStringAn array of<br>
     * 
     * @param split The separator
     * @param split The value to be converted
     * @return The results of
     */
    public static String[] toStrArray(String split, String str)
    {
        return str.split(split);
    }

    /**
     * convertlong<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Long toLong(Object value, Long defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Long)
        {
            return (Long) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            // Support scientific notation
            return new BigDecimal(valueStr.trim()).longValue();
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertlong<br>
     * If the given value is zero<code>null</code>,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Long toLong(Object value)
    {
        return toLong(value, null);
    }

    /**
     * convertdouble<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Double toDouble(Object value, Double defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Double)
        {
            return (Double) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).doubleValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            // Support scientific notation
            return new BigDecimal(valueStr.trim()).doubleValue();
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertdouble<br>
     * If the given value is null,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Double toDouble(Object value)
    {
        return toDouble(value, null);
    }

    /**
     * convertFloat<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Float toFloat(Object value, Float defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Float)
        {
            return (Float) value;
        }
        if (value instanceof Number)
        {
            return ((Number) value).floatValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Float.parseFloat(valueStr.trim());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertFloat<br>
     * If the given value is null,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Float toFloat(Object value)
    {
        return toFloat(value, null);
    }

    /**
     * convertboolean<br>
     * StringThe supported value is:true、false、yes、ok、no,1,0 If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static Boolean toBool(Object value, Boolean defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Boolean)
        {
            return (Boolean) value;
        }
        String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        valueStr = valueStr.trim().toLowerCase();
        switch (valueStr)
        {
            case "true":
                return true;
            case "false":
                return false;
            case "yes":
                return true;
            case "ok":
                return true;
            case "no":
                return false;
            case "1":
                return true;
            case "0":
                return false;
            default:
                return defaultValue;
        }
    }

    /**
     * convertboolean<br>
     * If the given value is null,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static Boolean toBool(Object value)
    {
        return toBool(value, null);
    }

    /**
     * convertEnumobject<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * 
     * @param clazz EnumtheClass
     * @param value value
     * @param defaultValue The default value
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (clazz.isAssignableFrom(value.getClass()))
        {
            @SuppressWarnings("unchecked")
            E myE = (E) value;
            return myE;
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return Enum.valueOf(clazz, valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertEnumobject<br>
     * If the given value is null,Or the conversion fails,Return the default value<code>null</code><br>
     * 
     * @param clazz EnumtheClass
     * @param value value
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value)
    {
        return toEnum(clazz, value, null);
    }

    /**
     * convertBigInteger<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static BigInteger toBigInteger(Object value, BigInteger defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof BigInteger)
        {
            return (BigInteger) value;
        }
        if (value instanceof Long)
        {
            return BigInteger.valueOf((Long) value);
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return new BigInteger(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertBigInteger<br>
     * If the given value is null,Or the conversion fails,Return the default value<code>null</code><br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static BigInteger toBigInteger(Object value)
    {
        return toBigInteger(value, null);
    }

    /**
     * convertBigDecimal<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @param defaultValue Default value for error conversion
     * @return The results of
     */
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof BigDecimal)
        {
            return (BigDecimal) value;
        }
        if (value instanceof Long)
        {
            return new BigDecimal((Long) value);
        }
        if (value instanceof Double)
        {
            return BigDecimal.valueOf((Double) value);
        }
        if (value instanceof Integer)
        {
            return new BigDecimal((Integer) value);
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        try
        {
            return new BigDecimal(valueStr);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * convertBigDecimal<br>
     * If the given value is null,Or the conversion fails,Return the default value<br>
     * Conversion failure is not reported
     * 
     * @param value The value to be converted
     * @return The results of
     */
    public static BigDecimal toBigDecimal(Object value)
    {
        return toBigDecimal(value, null);
    }

    /**
     * Convert an object to a string<br>
     * 1、ByteArray andByteBufferWill be converted to an array of corresponding strings 2、Object array is calledArrays.toStringmethods
     * 
     * @param obj object
     * @return string
     */
    public static String utf8Str(Object obj)
    {
        return str(obj, CharsetKit.CHARSET_UTF_8);
    }

    /**
     * Convert an object to a string<br>
     * 1、ByteArray andByteBufferWill be converted to an array of corresponding strings 2、Object array is calledArrays.toStringmethods
     * 
     * @param obj object
     * @param charsetName Character set
     * @return string
     */
    public static String str(Object obj, String charsetName)
    {
        return str(obj, Charset.forName(charsetName));
    }

    /**
     * Convert an object to a string<br>
     * 1、ByteArray andByteBufferWill be converted to an array of corresponding strings 2、Object array is calledArrays.toStringmethods
     * 
     * @param obj object
     * @param charset Character set
     * @return string
     */
    public static String str(Object obj, Charset charset)
    {
        if (null == obj)
        {
            return null;
        }

        if (obj instanceof String)
        {
            return (String) obj;
        }
        else if (obj instanceof byte[] || obj instanceof Byte[])
        {
            return str(obj, charset);
        }
        else if (obj instanceof ByteBuffer)
        {
            return str((ByteBuffer) obj, charset);
        }
        return obj.toString();
    }

    /**
     * willbyteArray to string
     * 
     * @param bytes byteAn array of
     * @param charset Character set
     * @return string
     */
    public static String str(byte[] bytes, String charset)
    {
        return str(bytes, StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * Decoded bytecode
     * 
     * @param data string
     * @param charset Character set,If this field is empty,The result of decoding depends on the platform
     * @return Decoded string
     */
    public static String str(byte[] data, Charset charset)
    {
        if (data == null)
        {
            return null;
        }

        if (null == charset)
        {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * The codingbyteBufferData is converted to a string
     * 
     * @param data data
     * @param charset Character set,如果为空使用当前系统Character set
     * @return string
     */
    public static String str(ByteBuffer data, String charset)
    {
        if (data == null)
        {
            return null;
        }

        return str(data, Charset.forName(charset));
    }

    /**
     * The codingbyteBufferData is converted to a string
     * 
     * @param data data
     * @param charset Character set,如果为空使用当前系统Character set
     * @return string
     */
    public static String str(ByteBuffer data, Charset charset)
    {
        if (null == charset)
        {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    // ----------------------------------------------------------------------- Full Angle and half Angle conversion
    /**
     * Half Angle to full Angle
     * 
     * @param input String.
     * @return Full-angle string.
     */
    public static String toSBC(String input)
    {
        return toSBC(input, null);
    }

    /**
     * Half Angle to full Angle
     * 
     * @param input String
     * @param notConvertSet A collection of characters that are not replaced
     * @return Full-angle string.
     */
    public static String toSBC(String input, Set<Character> notConvertSet)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (null != notConvertSet && notConvertSet.contains(c[i]))
            {
                // Skip characters that are not replaced
                continue;
            }

            if (c[i] == ' ')
            {
                c[i] = '\u3000';
            }
            else if (c[i] < '\177')
            {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * Full Angle to half Angle
     * 
     * @param input String.
     * @return Half Angle string
     */
    public static String toDBC(String input)
    {
        return toDBC(input, null);
    }

    /**
     * Replace the whole Angle with the half Angle
     * 
     * @param text The text
     * @param notConvertSet A collection of characters that are not replaced
     * @return The replaced character
     */
    public static String toDBC(String text, Set<Character> notConvertSet)
    {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (null != notConvertSet && notConvertSet.contains(c[i]))
            {
                // Skip characters that are not replaced
                continue;
            }

            if (c[i] == '\u3000')
            {
                c[i] = ' ';
            }
            else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
            {
                c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);

        return returnString;
    }

    /**
     * Digital amount uppercase conversion Let's write a complete one and then replace zero ten with zero
     * 
     * @param n digital
     * @return Chinese uppercase digit
     */
    public static String digitUppercase(double n)
    {
        String[] fraction = { "Angle", "points" };
        String[] digit = { "zero", "one", "Ii.", "3", "boss", "wu", "lu", "Retailer,", "捌", "nine" };
        String[][] unit = { { "yuan", "wan", "Hundred million" }, { "", "Pick up", "hk", "micky" } };

        String head = n < 0 ? "negative" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++)
        {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(zero.)+", "");
        }
        if (s.length() < 1)
        {
            s = "The whole";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++)
        {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++)
            {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(zero.)*zero$", "").replaceAll("^$", "zero") + unit[0][i] + s;
        }
        return head + s.replaceAll("(zero.)*zeroyuan", "yuan").replaceFirst("(zero.)+", "").replaceAll("(zero.)+", "zero").replaceAll("^The whole$", "zeroyuanThe whole");
    }
}
