package alb.common.exception.user;

/**
 * Verification code failure exception class
 *
 */
public class SmsCodeExpireException extends UserException
{
    private static final long serialVersionUID = 1L;

    public SmsCodeExpireException()
    {
        super("user.sms.expire", null);
    }
}
