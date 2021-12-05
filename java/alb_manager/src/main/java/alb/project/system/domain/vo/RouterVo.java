package alb.project.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Route Configuration information
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo
{
    /**
     * Routing name
     */
    private String name;

    /**
     * The routing address
     */
    private String path;

    /**
     * Whether to hide routes,When setting true The route will no longer appear in the sidebar
     */
    private boolean hidden;

    /**
     * Redirect address,When setting noRedirect The route is not clickable in breadcrumb navigation
     */
    private String redirect;

    /**
     * Component address
     */
    private String component;

    /**
     * When you have a route below children The declared route is greater than1When a,Automatically becomes a nested mode--Such as component pages
     */
    private Boolean alwaysShow;

    /**
     * Other elements
     */
    private MetaVo meta;

    /**
     * Zi lu by
     */
    private List<RouterVo> children;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public boolean getHidden()
    {
        return hidden;
    }

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    public String getRedirect()
    {
        return redirect;
    }

    public void setRedirect(String redirect)
    {
        this.redirect = redirect;
    }

    public String getComponent()
    {
        return component;
    }

    public void setComponent(String component)
    {
        this.component = component;
    }

    public Boolean getAlwaysShow()
    {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow)
    {
        this.alwaysShow = alwaysShow;
    }

    public MetaVo getMeta()
    {
        return meta;
    }

    public void setMeta(MetaVo meta)
    {
        this.meta = meta;
    }

    public List<RouterVo> getChildren()
    {
        return children;
    }

    public void setChildren(List<RouterVo> children)
    {
        this.children = children;
    }
}
