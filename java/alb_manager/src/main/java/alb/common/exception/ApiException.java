package alb.common.exception;

import lombok.Data;

/**
 * @ClassName : ApiException  //The name of the class
 * @Description : My exception handling  //describe
 * @Date: 2019-11-27 15:42  //time
 */
@Data
public class ApiException extends RuntimeException {
    private Integer code;


    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
