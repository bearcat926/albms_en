package alb.framework.config;

import java.util.TimeZone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Program annotation configuration
 *
 */
@Configuration
// Said byaopThe framework exposes the proxy object,AopContextTo be able to access
@EnableAspectJAutoProxy(exposeProxy = true)
// Specifies what to scanMapperClass package path
@MapperScan("alb.project.**.mapper")
public class ApplicationConfig
{
    /**
     * Time zone configuration
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}
