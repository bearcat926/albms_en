package alb.project.system.paramsVO;

import alb.framework.aspectj.lang.annotation.Phone;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ForgetPasswordParamsVO
 * @Description Forget password parameter encapsulation
 * @Date 2020/7/13 19:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class ForgetPasswordParamsVO {

    @Phone(message = "The mobile phone number format is incorrect!")
    @NotNull(message = "The mobile phone number is empty!")
    private String phone;

    @NotNull(message = "The verification code is empty!")
    @Length(min = 6, max = 6, message = "The length of the verification code is6position!")
    private String code;

    @NotNull(message = "Password is empty!")
    @Length(min = 6,max = 20,message = "Password length limit6-20character!")
    private String password;

    @NotNull(message = "Please enter your password again!")
    @Length(min = 6,max = 20,message = "Password length limit6-20character!")
    private String againPassword;

    @NotNull(message = "The unique identifier is null!")
    private String uuid;

}
