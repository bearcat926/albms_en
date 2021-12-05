package alb.framework.web.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import alb.project.system.domain.SysDept;
import alb.project.system.domain.SysMenu;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * TreeselectTree structure entity class
 *
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** nodeID */
    private Long id;

    /** The name of the node */
    private String label;

    /** Child nodes */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
