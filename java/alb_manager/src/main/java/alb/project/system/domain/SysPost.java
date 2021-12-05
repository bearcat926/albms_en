package alb.project.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import alb.framework.aspectj.lang.annotation.Excel;
import alb.framework.aspectj.lang.annotation.Excel.ColumnType;
import alb.framework.web.domain.BaseEntity;

/**
 * Post table sys_post
 *
 */
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Post the serial number */
    @Excel(name = "Post the serial number", cellType = ColumnType.NUMERIC)
    private Long postId;

    /** Post code */
    @Excel(name = "Post code")
    private String postCode;

    /** Post the name */
    @Excel(name = "Post the name")
    private String postName;

    /** Post sorting */
    @Excel(name = "Post sorting")
    private String postSort;

    /** state(0normal 1disable) */
    @Excel(name = "state", readConverterExp = "0=normal,1=disable")
    private String status;

    /** Whether the user has the job id Default does not exist */
    private boolean flag = false;

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    @NotBlank(message = "The post code cannot be empty")
    @Size(min = 0, max = 64, message = "The length of the post code cannot exceed64A character")
    public String getPostCode()
    {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    @NotBlank(message = "The post name cannot be empty")
    @Size(min = 0, max = 50, message = "The length of the post name cannot exceed50A character")
    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    @NotBlank(message = "The display order cannot be empty")
    public String getPostSort()
    {
        return postSort;
    }

    public void setPostSort(String postSort)
    {
        this.postSort = postSort;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("postSort", getPostSort())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
