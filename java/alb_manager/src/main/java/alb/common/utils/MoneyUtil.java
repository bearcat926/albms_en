package alb.common.utils;

import java.text.DecimalFormat;

/**
 * Amount instrument class
 *
 * @author HaoHao
 * Created on 2021/1/11.
 */
public class MoneyUtil {
    /** Upper case number */
    private final static String[] STR_NUMBER = { "zero", "one", "Ii.", "3", "boss", "wu",
            "lu", "Retailer,", "捌", "nine" };
    /** The integer unit */
    private final static String[] STR_UNIT = { "", "Pick up", "hk", "micky", "wan", "Pick up",
            "hk", "micky", "Hundred million", "Pick up", "hk", "micky" };
    /** The decimal unit */
    private final static String[] STR_UNIT2 = { "Angle", "points", "li" };

    /**
     * Get integer part
     */
    public static String getInteger(String num) {
        // Determine whether a decimal point is included
        if (num.contains(".")) {
            num = num.substring(0, num.indexOf("."));
        }
        // Inverted string
        num = new StringBuffer(num).reverse().toString();
        // To create aStringBufferobject
        StringBuffer temp = new StringBuffer();
        // In the unit
        for (int i = 0; i < num.length(); i++) {
            temp.append(STR_UNIT[i]);
            temp.append(STR_NUMBER[num.charAt(i) - 48]);
        }
        // Inverted string
        num = temp.reverse().toString();
        // The character that replaces the string
        num = numReplace(num, "Zero to pick up", "zero");
        num = numReplace(num, "Zero mean", "zero");
        num = numReplace(num, "Zero micky", "zero");
        num = numReplace(num, "One hundred thousand", "wan");
        num = numReplace(num, "One billion", "Hundred million");
        num = numReplace(num, "Zero zero", "zero");
        num = numReplace(num, "One hundred million", "Hundred million");
        // Remove the string if it ends in zero
        if (num.lastIndexOf("zero") == num.length() - 1) {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    /**
     * Get decimal part
     *
     * @param num
     * @return
     */
    public static String getDecimal(String num) {
        // Determine whether a decimal point is included
        if (!num.contains(".")) {
            return "";
        }
        num = num.substring(num.indexOf(".") + 1);
        // Inverted string
        num = new StringBuffer(num).reverse().toString();
        // To create aStringBufferobject
        StringBuffer temp = new StringBuffer();
        // In the unit
        for (int i = 0; i < num.length(); i++) {
//            temp.append(STR_UNIT2[i]);
            temp.append(STR_UNIT2[(num.length()-1)-i]);
            temp.append(STR_NUMBER[num.charAt(i) - 48]);
        }
        // The character that replaces the string
        num = temp.reverse().toString();
        num = numReplace(num, "Zero Angle", "zero");
        num = numReplace(num, "A zero", "zero");
        num = numReplace(num, "Zero duty", "zero");
        num = numReplace(num, "Zero zero", "zero");
        // Remove the string if it ends in zero
        if (num.lastIndexOf("zero") == num.length() - 1) {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    /**
     * Replace the contents of a string
     * @param num
     * @param oldStr
     * @param newStr
     * @return
     */
    public static String numReplace(String num, String oldStr, String newStr) {
        while (true) {
            // Checks whether the string contains the specified character
            if (!num.contains(oldStr)) {
                break;
            }
            // Substitution string
            num = num.replaceAll(oldStr, newStr);
        }
        // Returns the replaced string
        return num;
    }

    /**
     * Convert the amount from lowercase to uppercase
     * @param d
     * @return
     */
    public static String convert(double d) {
        // instantiationDecimalFormatobject
        DecimalFormat df = new DecimalFormat("#0.###");
        // formattingdoubledigital
        String strNum = df.format(d);
        // Determine whether a decimal point is included
        if (strNum.contains(".")) {
            String num = strNum.substring(0, strNum.indexOf("."));
            // Integer part greater than12You cannot convert
            if (num.length() > 12) {
                System.out.println("Number is too big,Unable to complete conversion!");
                return "";
            }
        }
        // The decimal point
        String point = "";
        if (strNum.contains(".")) {
            point = "yuan";
        } else {
            point = "yuan";
        }
        // Conversion results
        String result = getInteger(strNum) + point + getDecimal(strNum);
        // Check if the string is already"yuan"At the end
        if (result.startsWith("yuan")) {
            // Intercept string
            result = result.substring(1);
        }
        // Return a new string
        return result;
    }
}
