package alb.project.tool.gen.util;

import java.util.Properties;

import alb.common.constant.Constants;
import org.apache.velocity.app.Velocity;

/**
 * VelocityEngineThe factory
 *
 */
public class VelocityInitializer
{
    /**
     * Initialize thevmmethods
     */
    public static void initVelocity()
    {
        Properties p = new Properties();
        try
        {
            // loadingclasspathIn the directoryvmfile
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // Defining character set
            p.setProperty(Velocity.ENCODING_DEFAULT, Constants.UTF8);
            p.setProperty(Velocity.OUTPUT_ENCODING, Constants.UTF8);
            // Initialize theVelocityengine,Specify the configurationProperties
            Velocity.init(p);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
