package alb.framework.aspectj;

import java.lang.reflect.Method;

import alb.common.utils.spring.SpringUtils;
import alb.framework.aspectj.lang.annotation.DataScope;
import alb.framework.security.LoginUser;
import alb.framework.security.service.TokenService;
import alb.framework.web.domain.BaseEntity;
import alb.project.system.domain.SysRole;
import alb.project.system.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import alb.common.utils.ServletUtils;
import alb.common.utils.StringUtils;

/**
 * Data filtering
 *
 */
@Aspect
@Component
public class DataScopeAspect
{
    /**
     * All data permissions
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * Custom data permissions
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * Department Data permissions
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * Department and the following data permissions
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * Only personal data permission
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * Data permission filtering keyword
     */
    public static final String DATA_SCOPE = "dataScope";

    // Configure the weave point
    @Pointcut("@annotation(alb.framework.aspectj.lang.annotation.DataScope)")
    public void dataScopePointCut()
    {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable
    {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint)
    {
        // Get annotations
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null)
        {
            return;
        }
        // Gets the current user
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        SysUser currentUser = loginUser.getUser();
        if (currentUser != null)
        {
            // If you are the super administrator,Data is not filtered
            if (!currentUser.isAdmin())
            {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias());
            }
        }
    }

    /**
     * Data range filtering
     * 
     * @param joinPoint Point of tangency
     * @param user The user
     * @param alias The alias
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias)
    {
        StringBuilder sqlString = new StringBuilder();

        for (SysRole role : user.getRoles())
        {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                break;
            }
            else if (DATA_SCOPE_CUSTOM.equals(dataScope))
            {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
                        role.getRoleId()));
            }
            else if (DATA_SCOPE_DEPT.equals(dataScope))
            {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            }
            else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))
            {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptAlias, user.getDeptId(), user.getDeptId()));
            }
            else if (DATA_SCOPE_SELF.equals(dataScope))
            {
                if (StringUtils.isNotBlank(userAlias))
                {
                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                }
                else
                {
                    // Data permission is for myself only and nouserAliasAliases do not query any data
                    sqlString.append(" OR 1=0 ");
                }
            }
        }

        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
        }
    }

    /**
     * Are there annotations?,Get if it exists
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
}
