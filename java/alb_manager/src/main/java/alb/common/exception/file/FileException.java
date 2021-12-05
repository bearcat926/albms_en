package alb.common.exception.file;

import alb.common.exception.BaseException;

/**
 * File information exception class
 *
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
