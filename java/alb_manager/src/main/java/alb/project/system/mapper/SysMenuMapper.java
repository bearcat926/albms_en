package alb.project.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import alb.project.system.domain.SysMenu;

/**
 * The menu list The data layer
 *
 */
public interface SysMenuMapper
{
    /**
     * Example Query the system menu list
     * 
     * @param menu Menu information
     * @return Menu list
     */
    List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * According to all user permissions
     * 
     * @return Permissions list
     */
    List<String> selectMenuPerms();

    /**
     * Query the system menu list by user
     * 
     * @param menu Menu information
     * @return Menu list
     */
    List<SysMenu> selectMenuListByUserId(SysMenu menu);

    /**
     * According to the userIDQuery permissions
     * 
     * @param userId The userID
     * @return Permissions list
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * According to the userIDQuery menu
     * 
     * @return Menu list
     */
    List<SysMenu> selectMenuTreeAll();

    /**
     * According to the userIDQuery menu
     * 
     * @param username The userID
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
     * @return The results of
     */
    int hasChildByMenuId(Long menuId);

    /**
     * Add Menu information
     * 
     * @param menu Menu information
     * @return The results of
     */
    int insertMenu(SysMenu menu);

    /**
     * Modifying menu Information
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
     * @param menuName The name of the menu
     * @param parentId The parent menuID
     * @return The results of
     */
    SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);
}
