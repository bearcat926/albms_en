package alb.framework.config;

import alb.framework.security.filter.JwtAuthenticationTokenFilter;
import alb.framework.security.handle.AuthenticationEntryPointImpl;
import alb.framework.security.handle.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring securityconfiguration
 *
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Custom user authentication logic
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Authentication failure handling class
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * Exit handler class
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * tokenAuthentication filter
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * To solve Can't inject directly AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   Matches all request paths
     * access              |   SpringElThe expression results intrueCan be accessed
     * anonymous           |   Anonymously accessible
     * denyAll             |   User cannot access
     * fullyAuthenticated  |   Users are fully authenticated for access(nonremember-meAutomatic login)
     * hasAnyAuthority     |   If you have parameters,Parameter Indicates permission,Any of these permissions can be accessed
     * hasAnyRole          |   If you have parameters,Parameter Indicates role,Then any of the roles can access it
     * hasAuthority        |   If you have parameters,Parameter Indicates permission,The permission of the user can be accessed
     * hasIpAddress        |   If you have parameters,parameterIPaddress,If the userIPAnd parameter matching,You can access
     * hasRole             |   If you have parameters,Parameter Indicates role,Then its role can access it
     * permitAll           |   Users can access it at will
     * rememberMe          |   Allowed to pass throughremember-meLogged-in user access
     * authenticated       |   Users can access it after logging in
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CRSFdisable,Because it's not usedsession
                .csrf().disable()
                // Authentication failure handling class
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // Based on thetoken,So you don't have tosession
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Filtering request
                .authorizeRequests()
                // To log inlogin Verification codecaptchaImage Allow anonymous access
                .antMatchers("/login",
                        "/captchaImage",
                        "/getLoginBackGroundPicAndTechnicalPhone",
                        "/pushIncomingPoolMq", // Rule out into line
                        "/getDeptNameByUserName").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .antMatchers("/forgetPassword").anonymous() // Forget password release
                .antMatchers("/sendSMS").anonymous() // Change the password Send SMS verification code to put lines
                // All requests other than the above require authentication
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // addJWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * Strong hash hash encryption implementation
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Identity authentication interface
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
