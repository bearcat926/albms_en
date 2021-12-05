package alb.common.exception.user;

import alb.common.exception.BaseException;

/**
 * User information exception class
 *
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
