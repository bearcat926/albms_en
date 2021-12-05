package alb.framework.aspectj.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import alb.framework.aspectj.lang.enums.DataSourceType;

/**
 * Custom multi-data source switching annotations
 *
 * priority:Methods first,After class,If the method overrides the data source type on the class,The method shall prevail,Otherwise, the value on the class prevails
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * Switching data source name
     */
    DataSourceType value() default DataSourceType.MASTER;
}
