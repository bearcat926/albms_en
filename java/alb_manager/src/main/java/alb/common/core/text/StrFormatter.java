package alb.common.core.text;

import alb.common.utils.StringUtils;

/**
 * String formatting
 *
 */
public class StrFormatter
{
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    /**
     * Formatted string<br>
     * This method is simply a placeholder {} The arguments are replaced in order<br>
     * If you want to output {} use \\escape { Can be,If you want to output {} Before the \ use双escape符 \\\\ Can be<br>
     * case:<br>
     * Usually use:format("this is {} for {}", "a", "b") -> this is a for b<br>
     * escape{}: format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * escape\: format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param strPattern String template
     * @param argArray The list of parameters
     * @return The results of
     */
    public static String format(final String strPattern, final Object... argArray)
    {
        if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray))
        {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // Initialize the defined length for better performance
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;
        int delimIndex;// The position of the placeholder
        for (int argIndex = 0; argIndex < argArray.length; argIndex++)
        {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1)
            {
                if (handledPosition == 0)
                {
                    return strPattern;
                }
                else
                { // The rest of the string template no longer contains placeholders,Return the result after adding the rest
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }
            }
            else
            {
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH)
                {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH)
                    {
                        // The escape character is preceded by an escape character,Placeholders are still valid
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(Convert.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    }
                    else
                    {
                        // Placeholders are escaped
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                }
                else
                {
                    // Normal placeholder
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(Convert.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // All characters after the last placeholder is added
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }
}
