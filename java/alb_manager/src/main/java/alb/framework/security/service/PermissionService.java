package alb.framework.security.service;

import alb.common.utils.ServletUtils;
import alb.common.utils.StringUtils;
import alb.framework.security.LoginUser;
import alb.project.system.domain.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Custom permission implementation,ssTaken from theSpringSecurityThe first letter
 *
 */
@Service("ss")
public class PermissionService
{
    /** All permission identifiers */
    public static final String ALL_PERMISSION = "*:*:*";

    /** Id of the administrator role permission */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    @Autowired
    private TokenService tokenService;

    /**
     * Verify that a user has permissions
     * 
     * @param permission Permission string
     * @return Whether the user has certain rights
     */
    public boolean hasPermi(String permission)
    {
        if (StringUtils.isEmpty(permission))
        {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * Verify that a user does not have a permission,with hasPermiLogic instead
     *
     * @param permission Permission string
     * @return Check whether a user does not have certain rights
     */
    public boolean lacksPermi(String permission)
    {
        return hasPermi(permission) != true;
    }

    /**
     * Verify that the user has any of the following permissions
     *
     * @param permissions In order to PERMISSION_NAMES_DELIMETER Is a list of permissions delimited by
     * @return Check whether the user has any of the following rights
     */
    public boolean hasAnyPermi(String permissions)
    {
        if (StringUtils.isEmpty(permissions))
        {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(PERMISSION_DELIMETER))
        {
            if (permission != null && hasPermissions(authorities, permission))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the user has a role
     * 
     * @param role Role string
     * @return Whether the user has a role
     */
    public boolean hasRole(String role)
    {
        if (StringUtils.isEmpty(role))
        {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
        {
            return false;
        }
        for (SysRole sysRole : loginUser.getUser().getRoles())
        {
            String roleKey = sysRole.getRoleKey();
            if (SUPER_ADMIN.contains(roleKey) || roleKey.contains(StringUtils.trim(role)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify that the user does not have a role,with isRoleLogic instead。
     *
     * @param role Character name
     * @return Check whether the user does not have a role
     */
    public boolean lacksRole(String role)
    {
        return hasRole(role) != true;
    }

    /**
     * Verify that the user has any of the following roles
     *
     * @param roles In order to ROLE_NAMES_DELIMETER Is a list of roles delimited by
     * @return Whether the user has any of the following roles
     */
    public boolean hasAnyRoles(String roles)
    {
        if (StringUtils.isEmpty(roles))
        {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
        {
            return false;
        }
        for (String role : roles.split(ROLE_DELIMETER))
        {
            if (hasRole(role))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the permission is included
     * 
     * @param permissions Permissions list
     * @param permission Permission string
     * @return Whether the user has certain rights
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }
}
