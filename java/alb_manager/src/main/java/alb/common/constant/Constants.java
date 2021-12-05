package alb.common.constant;

import io.jsonwebtoken.Claims;

/**
 * General constant information
 *
 */
public class Constants
{
    /**
     * UTF-8 Character set
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK Character set
     */
    public static final String GBK = "GBK";

    /**
     * httprequest
     */
    public static final String HTTP = "http://";

    /**
     * httpsrequest
     */
    public static final String HTTPS = "https://";

    /**
     * Universal success identifier
     */
    public static final String SUCCESS = "0";

    /**
     * Universal failure identifier
     */
    public static final String FAIL = "1";

    /**
     * Login successful
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * The cancellation
     */
    public static final String LOGOUT = "Logout";

    /**
     * Login failed
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * Verification code redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * SMS verification code redis key
     */
    public static final String SMS_CODE_KEY = "sms_codes:";

    /**
     * The logged in user redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * Verification code validity period(minutes)
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * Validity period of SMS verification code(minutes)
     */
    public static final Integer SMS_EXPIRATION = 10;

    /**
     * The token
     */
    public static final String TOKEN = "token";

    /**
     * The token prefix
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * The token prefix
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * The userID
     */
    public static final String JWT_USERID = "userid";

    /**
     * The user name
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * The avatars
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * Creation time
     */
    public static final String JWT_CREATED = "created";

    /**
     * User permissions
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * Parameter management cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * The dictionary management cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * Resource Mapping Path The prefix
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * Verification code Failed to change the password
     */
    public static final String SMS_RESET_PASSWORD_FAIL = "Error";

    /**
     * Verification code Failed to change the password
     */
    public static final String SMS_RESET_PASSWORD_SUCCESS = "Success";


}
