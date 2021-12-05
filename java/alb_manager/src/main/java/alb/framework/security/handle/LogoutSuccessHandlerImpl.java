package alb.framework.security.handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.common.constant.Constants;
import alb.common.constant.HttpStatus;
import alb.framework.security.service.TokenService;
import alb.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson.JSON;
import alb.common.utils.ServletUtils;
import alb.common.utils.StringUtils;
import alb.framework.manager.AsyncManager;
import alb.framework.manager.factory.AsyncFactory;
import alb.framework.security.LoginUser;

/**
 * Custom exit handling classes Return to success
 *
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    /**
     * Exit the processing
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            // Delete user cache records
            tokenService.delLoginUser(loginUser.getToken());
            // Logs about user logout are recorded
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "Exit the success"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "Exit the success")));
    }
}
