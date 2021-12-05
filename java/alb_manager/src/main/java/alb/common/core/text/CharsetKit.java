package alb.common.core.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import alb.common.utils.StringUtils;

/**
 * Character set utility classes
 *
 */
public class CharsetKit
{
    /** ISO-8859-1 */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /** UTF-8 */
    public static final String UTF_8 = "UTF-8";
    /** GBK */
    public static final String GBK = "GBK";

    /** ISO-8859-1 */
    public static final Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
    /** UTF-8 */
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    /** GBK */
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

    /**
     * convertCharsetobject
     * 
     * @param charset Character set,为空则返回默认Character set
     * @return Charset
     */
    public static Charset charset(String charset)
    {
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }

    /**
     * Converts the character set encoding of a string
     * 
     * @param source string
     * @param srcCharset The source character set,The defaultISO-8859-1
     * @param destCharset Target character set,The defaultUTF-8
     * @return The converted character set
     */
    public static String convert(String source, String srcCharset, String destCharset)
    {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * Converts the character set encoding of a string
     * 
     * @param source string
     * @param srcCharset The source character set,The defaultISO-8859-1
     * @param destCharset Target character set,The defaultUTF-8
     * @return The converted character set
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset)
    {
        if (null == srcCharset)
        {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset)
        {
            srcCharset = StandardCharsets.UTF_8;
        }

        if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset))
        {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * @return System character set encoding
     */
    public static String systemCharset()
    {
        return Charset.defaultCharset().name();
    }
}
