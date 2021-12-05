package alb.project.system.domain.vo;

/**
 * Route Display Information
 *
 */
public class MetaVo
{
    /**
     * Sets the name of the route displayed in the sidebar and breadcrumbs
     */
    private String title;

    /**
     * Sets the icon of the route,Corresponding pathsrc/icons/svg
     */
    private String icon;

    public MetaVo()
    {
    }

    public MetaVo(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }
}
