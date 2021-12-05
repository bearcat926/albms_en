package alb.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Read project-related configurations
 *
 */
@Component
@ConfigurationProperties(prefix = "wlwq")
public class WlwqConfig
{
    /** The project name */
    private String name;

    /** version */
    private String version;

    /** The copyright year */
    private String copyrightYear;

    /** Example switch */
    private boolean demoEnabled;

    /** Upload path */
    private static String profile;

    /** Get address switch */
    private static boolean addressEnabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        WlwqConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        WlwqConfig.addressEnabled = addressEnabled;
    }

    /**
     * Obtain the profile picture upload path
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * Obtaining the download path
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * Obtaining the Upload Path
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}