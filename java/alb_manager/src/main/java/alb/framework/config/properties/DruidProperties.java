package alb.framework.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid Configuration properties
 *
 */
@Configuration
public class DruidProperties
{
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    public DruidDataSource dataSource(DruidDataSource datasource)
    {
        /** Configure the initial size、The minimum、The biggest */
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);

        /** The timeout period for obtaining connections is set */
        datasource.setMaxWait(maxWait);

        /** How often does the configuration check occur,Detect idle connections that need to be closed,In milliseconds */
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /** Configure a connection to be the smallest in the pool、Maximum survival time,In milliseconds */
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /**
         * Used to check whether the connection is validsql,The requirement is a query statement,The commonly usedselect 'x'。ifvalidationQueryfornull,testOnBorrow、testOnReturn、testWhileIdleIt doesn't work。
         */
        datasource.setValidationQuery(validationQuery);
        /** You are advised to set it totrue,Does not affect performance,And make sure it's safe。Detect when applying for a connection,If the idle time is greater thantimeBetweenEvictionRunsMillis,performvalidationQueryCheck whether the connection is valid。 */
        datasource.setTestWhileIdle(testWhileIdle);
        /** Execute when requesting a connectionvalidationQueryCheck whether the connection is valid,Doing this configuration degrades performance。 */
        datasource.setTestOnBorrow(testOnBorrow);
        /** Execute when the connection is returnedvalidationQueryCheck whether the connection is valid,Doing this configuration degrades performance。 */
        datasource.setTestOnReturn(testOnReturn);
        return datasource;
    }
}
