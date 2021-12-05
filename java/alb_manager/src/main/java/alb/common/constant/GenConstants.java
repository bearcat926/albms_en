package alb.common.constant;

/**
 * Code generates generic constants
 *
 */
public class GenConstants
{
    /** Single table(Add and delete) */
    public static final String TPL_CRUD = "crud";

    /** Tree table(Add and delete) */
    public static final String TPL_TREE = "tree";

    /** Tree coded field */
    public static final String TREE_CODE = "treeCode";

    /** Tree parent code field */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /** Tree name field */
    public static final String TREE_NAME = "treeName";

    /** Database string type */
    public static final String[] COLUMNTYPE_STR = { "char", "varchar", "narchar", "varchar2", "tinytext", "text",
            "mediumtext", "longtext" };

    /** Database time type */
    public static final String[] COLUMNTYPE_TIME = { "datetime", "time", "date", "timestamp" };

    /** Database number type */
    public static final String[] COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "float", "double", "decimal" };

    /** The page does not need to edit fields */
    public static final String[] COLUMNNAME_NOT_EDIT = { "id", "create_by", "create_time", "del_flag" };

    /** A list field that the page does not need to display */
    public static final String[] COLUMNNAME_NOT_LIST = { "id", "create_by", "create_time", "del_flag", "update_by",
            "update_time" };

    /** The page does not require a query field */
    public static final String[] COLUMNNAME_NOT_QUERY = { "id", "create_by", "create_time", "del_flag", "update_by",
            "update_time", "remark" };

    /** EntityThe base class field */
    public static final String[] BASE_ENTITY = { "createBy", "createTime", "updateBy", "updateTime", "remark" };

    /** TreeThe base class field */
    public static final String[] TREE_ENTITY = { "parentName", "parentId", "orderNum", "ancestors", "children" };

    /** The text box */
    public static final String HTML_INPUT = "input";

    /** Text field */
    public static final String HTML_TEXTAREA = "textarea";

    /** A drop-down box */
    public static final String HTML_SELECT = "select";

    /** Radio buttons */
    public static final String HTML_RADIO = "radio";

    /** Check box */
    public static final String HTML_CHECKBOX = "checkbox";

    /** Date controls */
    public static final String HTML_DATETIME = "datetime";

    /** String type */
    public static final String TYPE_STRING = "String";

    /** The integer */
    public static final String TYPE_INTEGER = "Integer";

    /** Long integer */
    public static final String TYPE_LONG = "Long";

    /** floating-point */
    public static final String TYPE_DOUBLE = "Double";

    /** High precision calculation type */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /** Time to type */
    public static final String TYPE_DATE = "Date";

    /** Fuzzy query */
    public static final String QUERY_LIKE = "LIKE";

    /** Need to be */
    public static final String REQUIRE = "1";
}
