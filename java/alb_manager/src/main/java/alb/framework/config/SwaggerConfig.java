package alb.framework.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2Interface configuration of
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    /** Basic System Configuration */
    @Autowired
    private WlwqConfig wlwqConfig;

    /** Whether openswagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /** Sets a unified prefix for requests */
    @Value("${swagger.pathMapping}")
    private String pathMapping;

    /**
     * createAPI
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                // Whether to enableSwagger
                .enable(enabled)
                // Used to create theAPIBasic information about,Displayed on the page of the document(Customize the information displayed)
                .apiInfo(apiInfo())
                // Sets which interfaces are exposed toSwaggershow
                .select()
                // Scan all annotated onesapi,It's more flexible that way
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // Scans the specified packageswaggerannotations
                // .apis(RequestHandlerSelectors.basePackage("com.Renbowen.project.tool.swagger"))
                // Scan all .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                /* Setting safe Mode,swaggerYou can set accesstoken */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }

    /**
     * Safe mode,Specified heretokenthroughAuthorizationHeaders request headers pass
     */
    private List<ApiKey> securitySchemes()
    {
        List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * Security context
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * Default security reference
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * Adding summary Information
     */
    private ApiInfo apiInfo()
    {
        // withApiInfoBuildercustomized
        return new ApiInfoBuilder()
                // Set the title
                .title("The title:xxxManagement system_Interface documentation")
                // describe
                .description("describe:Used forxxx")
                // The author information
                .contact(new Contact(wlwqConfig.getName(), null, null))
                // version
                .version("The version number:" + wlwqConfig.getVersion())
                .build();
    }
}
