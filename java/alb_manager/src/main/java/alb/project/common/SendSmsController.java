package alb.project.common;

import alb.common.constant.Constants;
import alb.common.thirdParty.AliYunSms;
import alb.project.common.paramsVO.SendSmsParams;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import alb.common.utils.IdUtils;
import alb.framework.redis.RedisCache;
import alb.framework.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static alb.framework.web.domain.AjaxResult.error;
import static alb.framework.web.domain.AjaxResult.success;

/**
 * @ClassName SendSmsController
 * @Description SMS verification code processing
 * @Date 2020/7/13 14:46
 */
@RestController
@Slf4j
public class SendSmsController {


    @Autowired
    private RedisCache redisCache;

    // Register template
    @Value("${aliyunSms.templateRegist}")
    private String templateRegist;
    // Forget password template
    @Value("${aliyunSms.templateUpdatePassword}")
    private String templateUpdatePassword;
    // Change information template
    @Value("${aliyunSms.templateInformationChange}")
    private String templateInformationChange;

    /**
     *  @Date: 2020/7/14 16:26
     *  @Description: Send verification code
     */
    @RequestMapping(value = "/sendSMS",method = RequestMethod.POST)
    public AjaxResult sendSMS(@Validated SendSmsParams params,
                              BindingResult bindingResult) throws ClientException {
        // Check the field
        if (bindingResult.hasErrors()) {
            return error(bindingResult.getFieldError().getDefaultMessage());
        }
        String templateCode = "";
        if (params.getTemplateType() == 1) {
            log.info("Requesting AN SMS Template:{}", templateRegist);
            templateCode = templateRegist;
        }
        if (params.getTemplateType() == 2) {
            log.info("Requesting AN SMS Template:{}", templateUpdatePassword);
            templateCode = templateUpdatePassword;
        }
        if (params.getTemplateType() == 3) {
            log.info("Requesting AN SMS Template:{}", templateInformationChange);
            templateCode = templateInformationChange;
        }
//        // Obtain user information by mobile phone number
//        AppAccount appAccount = accountService.jwtFindAccountByPhone(params.getPhone());
//        // The operation of sending a verification code is Need users to exist
//        if (params.getDealType() == 1) {
//            // Is empty User does not exist
//            if (appAccount == null) {
//                return ResultVOUtil.error("The user does not exist,Please register!");
//            }
//        }
//        // The operation of sending a verification code is No user is required
//        if (params.getDealType() == 0) {
//            // Don't empty The user is
//            if (appAccount != null) {
//                return ResultVOUtil.error("The user already exists,Please log in!");
//            }
//        }

        String verifyCode = "";
        for (int i = 0; i < 6; i++) {
            char c = (char) (AliYunSms.randomInt(0, 9) + '0');
            verifyCode += String.valueOf(c);
        }

        // Ali SMS interface
        SendSmsResponse response = new AliYunSms(params.getPhone(), templateCode, "{\"code\":\"" + verifyCode + "\"}").sendSms();
        log.error("Ali Cloud SMS callback:{}", response.getCode());
        log.error("Send verification code:{}", verifyCode);
        if (!response.getCode().equals("OK")) {
            return error("SMS sending failure!");
        }
        // The SMS verification code is successfully sent. Procedure!
        // A unique identifier
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        // In theredisIn the Set the expiration time to10minutes Temporal granularity:minutes
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.SMS_EXPIRATION, TimeUnit.MINUTES);
        AjaxResult result = new AjaxResult();
        result.put("uuid",uuid);
        return success(result);
    }



}
