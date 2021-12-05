package alb.framework.security.service;

import javax.annotation.Resource;

import alb.common.constant.Constants;
import alb.common.exception.ApiException;
import alb.common.exception.CustomException;
import alb.common.exception.user.*;
import alb.framework.redis.RedisCache;
import alb.project.system.domain.SysUser;
import alb.project.system.paramsVO.ForgetPasswordParamsVO;
import alb.project.system.service.ISysUserService;
import alb.common.exception.user.*;
import alb.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import alb.common.utils.MessageUtils;
import alb.framework.manager.AsyncManager;
import alb.framework.manager.factory.AsyncFactory;
import alb.framework.security.LoginUser;

/**
 * Login verification method
 *
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * Login authentication
     *
     * @param username The user name
     * @param password password
     * @param code     Verification code
     * @param uuid     A unique identifier
     * @return The results of
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // User authentication
        Authentication authentication = null;
        try {
            // The method is calledUserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        // Force other users to quit
//        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
//        for (String key : keys) {
//            LoginUser user = redisCache.getCacheObject(key);
//            if (user != null){
//                // If the login name is equal toredisThe user login name of the storage
//                if(username.equals(user.getUsername())){
//                    // Log out
//                    redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + user.getToken());
//                }
//            }
//        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // generatetoken
        return tokenService.createToken(loginUser);
    }

    @Autowired
    private ISysUserService userService;

    /**
     *
     * @Date: 2020/7/13 20:10
     * @Description:SMS verification code Changes the password
     */
    public int forgetPassword(ForgetPasswordParamsVO params) {
        // Query user information by mobile phone number
        SysUser user = userService.selectUserByPhone(params.getPhone());
        if (user == null) {
            throw new ApiException(500, MessageUtils.message("user.null"));
        }
        // Verify that the two passwords are the same
        if (!params.getPassword().equals(params.getAgainPassword())) {
            throw new ApiException(500, MessageUtils.message("user.password.reset.not.match"));
        }
        // Verification code
        // 1.To obtain parametersuuid
        String uuid = params.getUuid();
        // 2.Joining togetherredisthekey
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // 3.fromredisTo obtain the verification code
        String verifyCode = redisCache.getCacheObject(verifyKey);

        // The verification code is not obtained. Procedure,An exception is thrown,To save record
        if (verifyCode == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUserName(), Constants.SMS_RESET_PASSWORD_FAIL, MessageUtils.message("user.sms.expire")));
            throw new SmsCodeExpireException();
        }
        // The verification code is incorrect
        if (!verifyCode.equals(params.getCode())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUserName(), Constants.SMS_RESET_PASSWORD_FAIL, MessageUtils.message("user.sms.error")));
            throw new SmsCodeException();
        }
        // 4.removeredisVerification code in
        redisCache.deleteObject(verifyKey);
        // 5.The password was successfully changed by SMS
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUserName(), Constants.SMS_RESET_PASSWORD_SUCCESS, MessageUtils.message("user.password.reset.success")));
        // 6.Change the password
        SysUser resetUser = new SysUser();
        resetUser.setUserId(user.getUserId());
        resetUser.setPassword(SecurityUtils.encryptPassword(params.getPassword()));
        resetUser.setUpdateBy(user.getUserName());
        return userService.resetPwd(resetUser);
    }
}
