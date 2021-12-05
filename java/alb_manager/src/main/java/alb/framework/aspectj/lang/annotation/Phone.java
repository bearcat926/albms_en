package alb.framework.aspectj.lang.annotation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Verify mobile phone number,Both empty and the correct phone number can be verified<br/>
 * Correct mobile phone number by11Bit digit composition,The first is1
 * The second is 3、4、5、7、8.9
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "1[3|4|5|7|8|9][0-9]\\d{8}")
@Length(min = 11, max = 11)
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Phone {
    String message() default "Mobile phone number verification error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
