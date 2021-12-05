package alb.framework.aspectj.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import alb.framework.aspectj.lang.enums.BusinessType;
import alb.framework.aspectj.lang.enums.OperatorType;

/**
 * Custom operation log annotations
 *
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * The module 
     */
    String title() default "";

    /**
     * function
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operator type
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Whether to save the parameters of the request
     */
    boolean isSaveRequestData() default true;
}
