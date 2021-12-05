package alb.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import alb.common.core.text.StrFormatter;

/**
 * String utility class
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils
{
    /** An empty string */
    private static final String NULLSTR = "";

    /** The underline */
    private static final char SEPARATOR = '_';

    /**
     * Gets parameters that are not null
     * 
     * @param value defaultValue To determine thevalue
     * @return value The return value
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * * Judge aCollectionWhether is empty, containsList,Set,Queue
     * 
     * @param coll To determine theCollection
     * @return true:Is empty false:Is not empty
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * Judge aCollectionIs not empty,containsList,Set,Queue
     * 
     * @param coll To determine theCollection
     * @return true:Is not empty false:empty
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * * Determines whether an array of objects is empty
     * 
     * @param objects Array of objects to judge
     ** @return true:Is empty false:Is not empty
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * Checks whether an array of objects is non-empty
     * 
     * @param objects Array of objects to judge
     * @return true:Is not empty false:empty
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * * Judge aMapWhether is empty
     * 
     * @param map To determine theMap
     * @return true:Is empty false:Is not empty
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * Judge aMapWhether is empty
     * 
     * @param map To determine theMap
     * @return true:Is not empty false:empty
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * * Checks whether a string is empty
     * 
     * @param str String
     * @return true:Is empty false:Is not empty
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * Determines whether a string is a non-empty string
     * 
     * @param str String
     * @return true:Not empty string false:An empty string
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * * Determines whether an object is empty
     * 
     * @param object Object
     * @return true:Is empty false:Is not empty
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * Determines whether an object is non-null
     * 
     * @param object Object
     * @return true:Is not empty false:empty
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * * Determines whether an object is of array type(JavaAn array of primitive types)
     * 
     * @param object object
     * @return true:Is an array false:不Is an array
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * Go to the space
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * Intercept string
     * 
     * @param str string
     * @param start start
     * @return The results of
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * Intercept string
     * 
     * @param str string
     * @param start start
     * @param end The end of the
     * @return The results of
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * Formatted text, {} Represents a placeholder<br>
     * This method is simply a placeholder {} The arguments are replaced in order<br>
     * If you want to output {} use \\escape { Can be,If you want to output {} Before the \ use双escape符 \\\\ Can be<br>
     * case:<br>
     * Usually use:format("this is {} for {}", "a", "b") -> this is a for b<br>
     * escape{}: format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * escape\: format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param template The text template,The part that is replaced is used {} said
     * @param params The parameter value
     * @return Formatted text
     */
    public static String format(String template, Object... params)
    {
        if (isEmpty(params) || isEmpty(template))
        {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * The string to turnset
     * 
     * @param str string
     * @param sep The separator
     * @return setA collection of
     */
    public static final Set<String> str2Set(String str, String sep)
    {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * The string to turnlist
     * 
     * @param str string
     * @param sep The separator
     * @param filterBlank Filter pure blank
     * @param trim Remove the beginning and end whitespace
     * @return listA collection of
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim)
    {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str))
        {
            return list;
        }

        // Filtering whitespace strings
        if (filterBlank && StringUtils.isBlank(str))
        {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split)
        {
            if (filterBlank && StringUtils.isBlank(string))
            {
                continue;
            }
            if (trim)
            {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * Underline the hump name
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // Whether the leading character is capitalized
        boolean preCharIsUpperCase = true;
        // Whether the current character is capitalized
        boolean curreCharIsUpperCase = true;
        // Whether the next character is capitalized
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * Contains strings or not
     * 
     * @param str Validation string
     * @param strs String group
     * @return Contains the returntrue
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converts underlined capitalised strings to hump。If the underline before the conversion is uppercase, the string is empty,Returns an empty string。 For example,:HELLO_WORLD->HelloWorld
     * 
     * @param name The underscore before the conversion is a string named in uppercase
     * @return The converted hump named string
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // A quick check
        if (name == null || name.isEmpty())
        {
            // No need to switch
            return "";
        }
        else if (!name.contains("_"))
        {
            // No underline,Capitalize only the first letter
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // Split the original string with an underscore
        String[] camels = name.split("_");
        for (String camel : camels)
        {
            // Skip the beginning of the original string、A trailing underline or double underscore
            if (camel.isEmpty())
            {
                continue;
            }
            // Uppercase
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * Hump nomenclature For example,:user_name->userName
     */
    public static String toCamelCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == SEPARATOR)
            {
                upperCase = true;
            }
            else if (upperCase)
            {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }
}