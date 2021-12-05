package alb.project.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import alb.common.constant.UserConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * Dictionary table sys_dict_data
 *
 */
public class SysDictData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The dictionary coding */
    @Excel(name = "The dictionary coding", cellType = ColumnType.NUMERIC)
    private Long dictCode;

    /** Dictionary order */
    @Excel(name = "Dictionary order", cellType = ColumnType.NUMERIC)
    private Long dictSort;

    /** The dictionary labels */
    @Excel(name = "The dictionary labels")
    private String dictLabel;

    /** The dictionary keys */
    @Excel(name = "The dictionary keys")
    private String dictValue;

    /** A dictionary type */
    @Excel(name = "A dictionary type")
    private String dictType;

    /** Style properties(Other style extensions) */
    private String cssClass;

    /** Table dictionary style */
    private String listClass;

    /** Whether the default(Yis Nno) */
    @Excel(name = "Whether the default", readConverterExp = "Y=is,N=no")
    private String isDefault;

    /** state(0normal 1disable) */
    @Excel(name = "state", readConverterExp = "0=normal,1=disable")
    private String status;

    public Long getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    @NotBlank(message = "Dictionary labels cannot be empty")
    @Size(min = 0, max = 100, message = "The dictionary label length cannot exceed100A character")
    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    @NotBlank(message = "Dictionary keys cannot be empty")
    @Size(min = 0, max = 100, message = "The dictionary key value length cannot exceed100A character")
    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    @NotBlank(message = "The dictionary type cannot be empty")
    @Size(min = 0, max = 100, message = "The dictionary type length cannot exceed100A character")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    @Size(min = 0, max = 100, message = "The length of the style attribute cannot exceed100A character")
    public String getCssClass()
    {
        return cssClass;
    }

    public void setCssClass(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public String getListClass()
    {
        return listClass;
    }

    public void setListClass(String listClass)
    {
        this.listClass = listClass;
    }

    public boolean getDefault()
    {
        return UserConstants.YES.equals(this.isDefault);
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictCode", getDictCode())
            .append("dictSort", getDictSort())
            .append("dictLabel", getDictLabel())
            .append("dictValue", getDictValue())
            .append("dictType", getDictType())
            .append("cssClass", getCssClass())
            .append("listClass", getListClass())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
