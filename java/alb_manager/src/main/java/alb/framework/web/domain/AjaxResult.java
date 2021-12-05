package alb.framework.web.domain;

import java.util.HashMap;

import alb.common.constant.HttpStatus;
import alb.common.utils.StringUtils;

/**
 * Operation message alert
 *
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** Status code */
    public static final String CODE_TAG = "code";

    /** Returns the content */
    public static final String MSG_TAG = "msg";

    /** The data object */
    public static final String DATA_TAG = "data";

    public static final String successMsg = "Operation is successful";

    public static final String errorMsg = "The operation failure";

    /**
     * Initializes a newly created AjaxResult object,Make it represent an empty message。
     */
    public AjaxResult()
    {
    }

    /**
     * Initializes a newly created AjaxResult object
     * 
     * @param code Status code
     * @param msg Returns the content
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * Initializes a newly created AjaxResult object
     * 
     * @param code Status code
     * @param msg Returns the content
     * @param data The data object
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * Return success message
     * 
     * @return Success message
     */
    public static AjaxResult success()
    {
        return AjaxResult.success(successMsg);
    }

    /**
     * Return success data
     * 
     * @return Success message
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success(successMsg, data);
    }

    /**
     * Return success message
     * 
     * @param msg Returns the content
     * @return Success message
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * Return success message
     * 
     * @param msg Returns the content
     * @param data The data object
     * @return Success message
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * Return error message
     * 
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error(errorMsg);
    }

    /**
     * Return error message
     * 
     * @param msg Returns the content
     * @return A warning message
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * Return error message
     * 
     * @param msg Returns the content
     * @param data The data object
     * @return A warning message
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * Return error message
     * 
     * @param code Status code
     * @param msg Returns the content
     * @return A warning message
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }
}
