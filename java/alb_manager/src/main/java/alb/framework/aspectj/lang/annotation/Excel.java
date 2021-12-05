package alb.framework.aspectj.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom exportExcelData annotations
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * Export toExcelIn the name.
     */
    String name() default "";

    /**
     * The date format, Such as: yyyy-MM-dd
     */
    String dateFormat() default "";

    /**
     * Read content to turn expression (Such as: 0=male,1=female,2=The unknown)
     */
    String readConverterExp() default "";

    /**
     * Derived type(0digital 1string)
     */
    ColumnType cellType() default ColumnType.STRING;

    /**
     * When exporting inexcelThe height of each column in Unit: character
     */
    double height() default 14;

    /**
     * When exporting inexcelThe width of each column in Unit: character
     */
    double width() default 16;

    /**
     * Word suffix,Such as% 90 become90%
     */
    String suffix() default "";

    /**
     * When the value is null,The default value for the field
     */
    String defaultValue() default "";

    /**
     * Prompt information
     */
    String prompt() default "";

    /**
     * Set only column contents that cannot be entered to be selected.
     */
    String[] combo() default {};

    /**
     * Whether to export data,To cope with demand:Sometimes we need to export a template,This is for the title but the content needs to be manually filled in by the user.
     */
    boolean isExport() default true;

    /**
     * The name of an attribute in another class,Support for multi-level fetching,Separated by decimal points
     */
    String targetAttr() default "";

    /**
     * The field type(0:Export import;1:Export only;2:Import only)
     */
    Type type() default Type.ALL;

    enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    enum ColumnType
    {
        NUMERIC(0), STRING(1);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}