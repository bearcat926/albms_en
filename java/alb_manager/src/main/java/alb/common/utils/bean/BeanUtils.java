package alb.common.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean Utility class
 *
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** BeanThe subscript at the beginning of the attribute name in the method name */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * matchinggetterMethod */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * matchingsetterMethod */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * BeanProperty copy tool method。
     * 
     * @param dest The target object
     * @param src The source object
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Of the objectsettermethods。
     * 
     * @param obj object
     * @return The object'ssetterMethods list
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setterMethods list
        List<Method> setterMethods = new ArrayList<Method>();

        // Get all methods
        Method[] methods = obj.getClass().getMethods();

        // To find thesettermethods

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // returnsetterMethods list
        return setterMethods;
    }

    /**
     * Of the objectgettermethods。
     * 
     * @param obj object
     * @return The object'sgetterMethods list
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getterMethods list
        List<Method> getterMethods = new ArrayList<Method>();
        // Get all methods
        Method[] methods = obj.getClass().getMethods();
        // To find thegettermethods
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // returngetterMethods list
        return getterMethods;
    }

    /**
     * checkBeanWhether attribute names in method names are equal。<br>
     * Such asgetName()andsetName()Same property name,getName()andsetAge()Attribute names are different。
     * 
     * @param m1 The method name1
     * @param m2 The method name2
     * @return Returns the same as the property nametrue,Otherwise returnsfalse
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}
