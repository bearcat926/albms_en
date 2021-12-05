package alb.project.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alb.common.constant.UserConstants;
import alb.common.exception.CustomException;
import alb.common.utils.spring.SpringUtils;
import alb.project.system.domain.SysRole;
import alb.project.system.domain.SysRoleDept;
import alb.project.system.domain.SysRoleMenu;
import alb.project.system.mapper.SysRoleDeptMapper;
import alb.project.system.mapper.SysRoleMapper;
import alb.project.system.mapper.SysRoleMenuMapper;
import alb.project.system.mapper.SysUserRoleMapper;
import alb.project.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import alb.common.utils.StringUtils;
import alb.framework.aspectj.lang.annotation.DataScope;

/**
 * role Business layer processing
 *
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * Query role data based on conditional paging
     * 
     * @param role The role of information
     * @return Role data set information
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * According to the userIDQuery permissions
     * 
     * @param userId The userID
     * @return Permissions list
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * Querying All Roles
     * 
     * @return The role list
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * According to the userIDGets the list of role selection boxes
     * 
     * @param userId The userID
     * @return Select the roleIDThe list of
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * Through the roleIDQuery role
     * 
     * @param roleId roleID
     * @return Role Object Information
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * Verify that the role name is unique
     * 
     * @param role The role of information
     * @return The results of
     */
    @Override
    public String checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Verify that the role has unique permissions
     * 
     * @param role The role of information
     * @return The results of
     */
    @Override
    public String checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Verify whether the role allows operations
     * 
     * @param role The role of information
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new CustomException("Do not operate the super administrator role");
        }
    }

    /**
     * Through the roleIDExample Query the number of roles
     * 
     * @param roleId roleID
     * @return The results of
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * Added Saving role information
     * 
     * @param role The role of information
     * @return The results of
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        // Adding Role Information
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * Modify Save role information
     * 
     * @param role The role of information
     * @return The results of
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        // Modifying Role Information
        roleMapper.updateRole(role);
        // Delete the association between a role and a menu
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * Modifying role Status
     * 
     * @param role The role of information
     * @return The results of
     */
    @Override
    public int updateRoleStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * Example Modify data permission information
     * 
     * @param role The role of information
     * @return The results of
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // Modifying Role Information
        roleMapper.updateRole(role);
        // Delete the association between a role and a department
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // Added role and department information(Data access)
        return insertRoleDept(role);
    }

    /**
     * Added role menu information
     * 
     * @param role Role object
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // Added user and role management
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * Added role department information(Data access)
     *
     * @param role Role object
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // Added roles and departments(Data access)management
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * Through the roleIDDelete the role
     * 
     * @param roleId roleID
     * @return The results of
     */
    @Override
    public int deleteRoleById(Long roleId)
    {
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * Delete role information in batches
     * 
     * @param roleIds Role that you want to deleteID
     * @return The results of
     */
    @Override
    public int deleteRoleByIds(Long[] roleIds)
    {
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new CustomException(String.format("%1$sallocated,Can't delete", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }
}
