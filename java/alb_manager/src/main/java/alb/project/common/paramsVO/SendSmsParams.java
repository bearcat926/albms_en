package alb.project.common.paramsVO;

import alb.framework.aspectj.lang.annotation.Phone;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Send captcha request parameters
 */
@Data
public class SendSmsParams {

    @NotNull(message = "The mobile phone number is empty!")
    @Phone(message = "The phone number is incorrect!")
    private String phone;

    @NotNull(message = "The verification code request type is empty!")
    private Integer dealType;

    @NotNull(message = "The template type is empty!")
    private Integer templateType;
}
