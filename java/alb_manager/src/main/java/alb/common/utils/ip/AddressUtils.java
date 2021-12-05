package alb.common.utils.ip;

import alb.common.constant.Constants;
import alb.common.utils.StringUtils;
import alb.common.utils.http.HttpUtils;
import alb.framework.config.WlwqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

/**
 * Get address class
 *
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IPAddress the query
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // Unknown address
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        String address = UNKNOWN;
        // No Intranet Query
        if (IpUtils.internalIp(ip))
        {
            return "IntranetIP";
        }
        if (WlwqConfig.isAddressEnabled())
        {
            try
            {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr))
                {
                    log.error("Get a geographic location exception {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            }
            catch (Exception e)
            {
                log.error("Get a geographic location exception {}", ip);
            }
        }
        return address;
    }
}
