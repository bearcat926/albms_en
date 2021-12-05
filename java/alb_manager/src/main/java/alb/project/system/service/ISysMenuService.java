package alb.project.system.service;

import java.util.List;
import java.util.Set;

import alb.project.system.domain.SysMenu;
import alb.project.system.domain.vo.RouterVo;
import alb.framework.web.domain.TreeSelect;

/**
 * The menu The business layer
 *
 */
public interface ISysMenuService
{
    /**
     * Query the system menu list by user
     * 
     * @param userId The userID
     * @return Menu list
     */
    List<SysMenu> selectMenuList(Long userId);

    /**
     * Query the system menu list by user
     * 
     * @param menu Menu information
     * @param userId The userID
     * @return Menu list
     */
    List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * According to the userIDQuery permissions
     * 
     * @param userId The userID
     * @return Permissions list
     */
    Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * According to the userIDExample Query menu tree information
     * 
     * @param userId The userID
     * @return Menu list
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * According to the characterIDExample Query menu tree information
     * 
     * @param roleId roleID
     * @return Select menu list
     */
    List<Integer> selectMenuListByRoleId(Long roleId);

    /**
     * The menu needed to build the front-end route
     * 
     * @param menus Menu list
     * @return The routing list
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * The tree structure is needed to build the front end
     * 
     * @param menus Menu list
     * @return Tree structure list
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * The drop-down tree structure is needed to build the front end
     * 
     * @param menus Menu list
     * @return List of drop-down tree structures
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * According to the menuIDQuery information
     * 
     * @param menuId The menuID
     * @return Menu information
     */
    SysMenu selectMenuById(Long menuId);

    /**
     * Whether a list node exists
     * 
     * @param menuId The menuID
     * @return The results of true There are false 不There are
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * Query whether a role exists in the menu
     * 
     * @param menuId The menuID
     * @return The results of true There are false 不There are
     */
    boolean checkMenuExistRole(Long menuId);

    /**
     * Added Save menu information
     * 
     * @param menu Menu information
     * @return The results of
     */
    int insertMenu(SysMenu menu);

    /**
     * Modify save menu information
     * 
     * @param menu Menu information
     * @return The results of
     */
    int updateMenu(SysMenu menu);

    /**
     * Example Delete menu management information
     * 
     * @param menuId The menuID
     * @return The results of
     */
    int deleteMenuById(Long menuId);

    /**
     * Verify that menu names are unique
     * 
     * @param menu Menu information
     * @return The results of
     */
    String checkMenuNameUnique(SysMenu menu);
}
