package alb.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.common.utils.spring.SpringUtils;
import alb.framework.aspectj.lang.annotation.Log;
import alb.framework.aspectj.lang.enums.BusinessStatus;
import alb.framework.manager.AsyncManager;
import alb.framework.manager.factory.AsyncFactory;
import alb.framework.security.LoginUser;
import alb.framework.security.service.TokenService;
import alb.project.monitor.domain.SysOperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import com.alibaba.fastjson.JSON;
import alb.common.enums.HttpMethod;
import alb.common.utils.ServletUtils;
import alb.common.utils.StringUtils;
import alb.common.utils.ip.IpUtils;

/**
 * Record operation logs
 *
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // Configure the weave point
    @Pointcut("@annotation(alb.framework.aspectj.lang.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * Execute after processing the request
     *
     * @param joinPoint Point of tangency
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * Intercept exception operation
     * 
     * @param joinPoint Point of tangency
     * @param e abnormal
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
    {
        try
        {
            // Get annotations
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            // Gets the current user
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            // *========Database logs=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // Requested address
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            // Returns the parameter
            operLog.setJsonResult(JSON.toJSONString(jsonResult));

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null)
            {
                operLog.setOperName(loginUser.getUsername());
            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // Setting Method Name
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // Setting the request Mode
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // Handle parameters on set annotations
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // Console log print returns parameters
            log.info("\nDetails about operation log parameters:{}","requestIP:"+ip+"\nrequest方式:"+operLog.getRequestMethod()+"\nrequestURL:"+operLog.getOperUrl()+"\nThe method name:"+className + "." + methodName + "()\nrequest参数:"+operLog.getOperParam()+ "\nReturns the parameter:"+operLog.getJsonResult());
            // Save the database
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));

        }
        catch (Exception exp)
        {
            // Record local exception logs
            log.error("==Pre-notification exception==");
            log.error("Exception information:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * Gets the description of the method in the annotation Used forControllerLayer annotation
     * 
     * @param log The log
     * @param operLog The operation log
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) throws Exception
    {
        // Set up theactionaction
        operLog.setBusinessType(log.businessType().ordinal());
        // Set the title
        operLog.setTitle(log.title());
        // Set the operator category
        operLog.setOperatorType(log.operatorType().ordinal());
        // Whether to saverequest,Parameters and values
        if (log.isSaveRequestData())
        {
            // Gets information about parameters,Passed into the database。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * Gets the parameters of the request,In thelogIn the
     * 
     * @param operLog The operation log
     * @throws Exception abnormal
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception
    {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
        {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * Are there annotations?,Get if it exists
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * Parameters of the assembled
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (int i = 0; i < paramsArray.length; i++)
            {
                if (!isFilterObject(paramsArray[i]))
                {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * Determines whether an object needs to be filtered。
     * 
     * @param o Object information。
     * @return If it is an object to be filtered,It returnstrue;否It returnsfalse。
     */
    public boolean isFilterObject(final Object o)
    {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
