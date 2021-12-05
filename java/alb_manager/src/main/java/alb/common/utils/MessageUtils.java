package alb.common.utils;

import alb.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * To obtaini18nResource file
 *
 */
public class MessageUtils
{
    /**
     * Based on message keys and parameters To get the message Entrusted tospring messageSource
     *
     * @param code Message key
     * @param args parameter
     * @return Gets the internationalized translation value
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
