package alb.common.utils;

import alb.common.constant.HttpStatus;
import alb.common.exception.CustomException;
import alb.framework.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security service utility class
 *
 */
public class SecurityUtils
{
    /**
     * Obtaining user accounts
     **/
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new CustomException("Obtaining the user account fails. Procedure", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get the user
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new CustomException("Obtaining user information fails. Procedure", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * To obtainAuthentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * generateBCryptPasswordEncoderpassword
     *
     * @param password password
     * @return Encrypted string
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * Check whether the passwords are the same
     *
     * @param rawPassword The real password
     * @param encodedPassword Postencrypted character
     * @return The results of
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Administrator or not
     * 
     * @param userId The userID
     * @return The results of
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}
