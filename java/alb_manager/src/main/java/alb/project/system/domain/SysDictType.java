package alb.project.system.domain;

import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dictionary type table sys_dict_type
 *
 */
public class SysDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** The dictionary keys */
    @Excel(name = "The dictionary keys", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /** Name of the dictionary */
    @Excel(name = "Name of the dictionary")
    private String dictName;

    /** A dictionary type */
    @Excel(name = "A dictionary type")
    private String dictType;

    /** state(0normal 1disable) */
    @Excel(name = "state", readConverterExp = "0=normal,1=disable")
    private String status;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    @NotBlank(message = "Dictionary names cannot be empty")
    @Size(min = 0, max = 100, message = "The dictionary type name cannot exceed the length100A character")
    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    @NotBlank(message = "The dictionary type cannot be empty")
    @Size(min = 0, max = 100, message = "Dictionary type Type length cannot exceed100A character")
    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
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
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
