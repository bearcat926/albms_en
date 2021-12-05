package alb.framework.security;

/**
 * User Login Object
 *
 */
public class LoginBody
{
    /**
     * The user name
     */
    private String username;

    /**
     * The user password
     */
    private String password;

    /**
     * Verification code
     */
    private String code;

    /**
     * A unique identifier
     */
    private String uuid = "";

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
