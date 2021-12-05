package alb.project.tool.gen.domain;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import alb.common.constant.GenConstants;
import org.apache.commons.lang3.ArrayUtils;
import alb.common.utils.StringUtils;
import alb.framework.web.domain.BaseEntity;

/**
 * A business table gen_table
 *
 */
public class GenTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Serial number */
    private Long tableId;

    /** The name of the table */
    @NotBlank(message = "Table names cannot be empty")
    private String tableName;

    /** Table describes */
    @NotBlank(message = "The table description cannot be empty")
    private String tableComment;

    /** Entity class name(Uppercase) */
    @NotBlank(message = "Entity class names cannot be empty")
    private String className;

    /** Templates to use(crudSingle table operation treeTree table operation) */
    private String tplCategory;

    /** Generate package path */
    @NotBlank(message = "The generated package path cannot be empty")
    private String packageName;

    /** Generate module name */
    @NotBlank(message = "The generated module name cannot be empty")
    private String moduleName;

    /** Generate a business name */
    @NotBlank(message = "The generated business name cannot be empty")
    private String businessName;

    /** Generate function name */
    @NotBlank(message = "The build function name cannot be empty")
    private String functionName;

    /** Generate the author */
    @NotBlank(message = "Author cannot be empty")
    private String functionAuthor;

    /** The primary key information */
    private GenTableColumn pkColumn;

    /** Tabular information */
    @Valid
    private List<GenTableColumn> columns;

    /** Other build options */
    private String options;

    /** Tree coded field */
    private String treeCode;

    /** Tree parent code field */
    private String treeParentCode;

    /** Tree name field */
    private String treeName;

    public Long getTableId()
    {
        return tableId;
    }

    public void setTableId(Long tableId)
    {
        this.tableId = tableId;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getTplCategory()
    {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory)
    {
        this.tplCategory = tplCategory;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public String getFunctionAuthor()
    {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor)
    {
        this.functionAuthor = functionAuthor;
    }

    public GenTableColumn getPkColumn()
    {
        return pkColumn;
    }

    public void setPkColumn(GenTableColumn pkColumn)
    {
        this.pkColumn = pkColumn;
    }

    public List<GenTableColumn> getColumns()
    {
        return columns;
    }

    public void setColumns(List<GenTableColumn> columns)
    {
        this.columns = columns;
    }

    public String getOptions()
    {
        return options;
    }

    public void setOptions(String options)
    {
        this.options = options;
    }

    public String getTreeCode()
    {
        return treeCode;
    }

    public void setTreeCode(String treeCode)
    {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode()
    {
        return treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode)
    {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName()
    {
        return treeName;
    }

    public void setTreeName(String treeName)
    {
        this.treeName = treeName;
    }

    public boolean isTree()
    {
        return isTree(this.tplCategory);
    }

    public static boolean isTree(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public boolean isCrud()
    {
        return isCrud(this.tplCategory);
    }

    public static boolean isCrud(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public boolean isSuperColumn(String javaField)
    {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField)
    {
        if (isTree(tplCategory))
        {
            return StringUtils.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }
}