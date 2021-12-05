package alb.common.exception.user;

/**
 * SMS verification code error exceptions
 *
 */
public class SmsCodeException extends UserException
{
    private static final long serialVersionUID = 1L;

    public SmsCodeException()
    {
        super("user.sms.error", null);
    }
}
