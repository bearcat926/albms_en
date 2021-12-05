package alb.project.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * Parameter configuration table sys_config
 *
 */
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Parameter the primary key */
    @Excel(name = "Parameter the primary key", cellType = ColumnType.NUMERIC)
    private Long configId;

    /** The parameter name */
    @Excel(name = "The parameter name")
    private String configName;

    /** Parameters of the key name */
    @Excel(name = "Parameters of the key name")
    private String configKey;

    /** Parameters of the key value */
    @Excel(name = "Parameters of the key value")
    private String configValue;

    /** The built-in(Yis Nno) */
    @Excel(name = "The built-in", readConverterExp = "Y=is,N=no")
    private String configType;

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    @NotBlank(message = "Parameter names cannot be empty")
    @Size(min = 0, max = 100, message = "The parameter name cannot exceed100A character")
    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    @NotBlank(message = "Parameter key name length cannot be empty")
    @Size(min = 0, max = 100, message = "The length of the parameter key cannot exceed100A character")
    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    @NotBlank(message = "Parameter keys cannot be empty")
    @Size(min = 0, max = 500, message = "Parameter key length cannot exceed500A character")
    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
