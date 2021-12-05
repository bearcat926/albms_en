package alb.project.system.mapper;

import java.util.List;
import alb.project.system.domain.SysRoleMenu;

/**
 * Role and menu association table The data layer
 *
 */
public interface SysRoleMenuMapper
{
    /**
     * Example Query the number of menus used
     * 
     * @param menuId The menuID
     * @return The results of
     */
    int checkMenuExistRole(Long menuId);

    /**
     * Through the roleIDDelete the association between the role and the menu
     * 
     * @param roleId roleID
     * @return The results of
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * Added role menu information in batches
     * 
     * @param roleMenuList Character menu list
     * @return The results of
     */
    int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
