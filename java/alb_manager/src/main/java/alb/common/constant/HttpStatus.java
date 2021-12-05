package alb.common.constant;

/**
 * Return status code
 *
 */
public class HttpStatus
{
    /**
     * Operation is successful
     */
    public static final int SUCCESS = 200;

    /**
     * Object created successfully
     */
    public static final int CREATED = 201;

    /**
     * The request has been accepted
     */
    public static final int ACCEPTED = 202;

    /**
     * The operation has been performed successfully,But no data is returned
     */
    public static final int NO_CONTENT = 204;

    /**
     * The resource has been removed
     */
    public static final int MOVED_PERM = 301;

    /**
     * redirect
     */
    public static final int SEE_OTHER = 303;

    /**
     * The resource has not been modified
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * Parameter list error(The lack of,Format mismatch)
     */
    public static final int BAD_REQUEST = 400;

    /**
     * unauthorized
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Limited access,Authorization expires
     */
    public static final int FORBIDDEN = 403;

    /**
     * resources,Service not found
     */
    public static final int NOT_FOUND = 404;

    /**
     * Do not allow thehttpmethods
     */
    public static final int BAD_METHOD = 405;

    /**
     * Resource conflict,Or the resource is locked
     */
    public static final int CONFLICT = 409;

    /**
     * Unsupported data,The media type
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * Internal system error
     */
    public static final int ERROR = 500;

    /**
     * Interface not implemented
     */
    public static final int NOT_IMPLEMENTED = 501;
}
