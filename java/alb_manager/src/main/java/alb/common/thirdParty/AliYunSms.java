package alb.common.thirdParty;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Ali Cloud SMS interface
 */
public class AliYunSms {

    /** The product name:Cloud communication SMSAPIproduct,Developers don't need to replace */
    private static final String product = "Dysmsapi";
    /** The product domain,,Developers don't need to replace */
    private static final String domain = "dysmsapi.aliyuncs.com";

    /** This needs to be replaced with the developer's ownAK(Look for it in ali Cloud access console) */
    private static final String accessKeyId = "LTAIlkcGk1b1Y3Cm";
    private static final String accessKeySecret = "djGug9Mx9mcwGb5RfXlVnDw3BLrKsD";

    /** mandatory:Message signatures-It can be found in the SMS console */
    private static final String signName = "Network to network";
    /** mandatory:Phone number to be sent */
    private String phoneNumbers;
    /** mandatory:Message template-It can be found in the SMS console */
    private String templateCode;
    /** optional:Substitution of variables in templatesJSONstring,For example, the template content is"dear${name},Your verification code is${code}"when,The value here is */
    private String templateParam;
    /** optional:outIdExtend the field to provide to the business side,This value is eventually returned to the caller in the SMS receipt message */
    private String outId;

    public AliYunSms() {
    }

    /** Mobile phone no.、Message template、Substitution of variables in templatesJSONString constructor */
    public AliYunSms(String phoneNumbers, String templateCode, String templateParam) {
        super();
        this.phoneNumbers = phoneNumbers;
        this.templateCode = templateCode;
        this.templateParam = templateParam;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public SendSmsResponse sendSms() throws ClientException {

        // You can adjust the timeout period by yourself
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // Initialize theacsClient,Temporary does not supportregionthe
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // Assemble request object-See the console for detailed description-Document Contents
        SendSmsRequest request = new SendSmsRequest();
        // mandatory:Phone number to be sent
        request.setPhoneNumbers(phoneNumbers);
        // mandatory:Message signatures-It can be found in the SMS console
        request.setSignName(signName);
        // mandatory:Message template-It can be found in the SMS console
        request.setTemplateCode(templateCode);
        // optional:Substitution of variables in templatesJSONstring,For example, the template content is"dear${name},Your verification code is${code}"when,The value here is
        request.setTemplateParam(templateParam);

        // optional-Upstream SMS extension code(For users without special requirements, ignore this field)
        // request.setSmsUpExtendCode("90997");

        // optional:outIdExtend the field to provide to the business side,This value is eventually returned to the caller in the SMS receipt message
        request.setOutId(outId);

        // hint An exception may be thrown here,Pay attention tocatch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        // You can adjust the timeout period by yourself
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // Initialize theacsClient,Temporary does not supportregionthe
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // Assemble request object
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // mandatory-number
        request.setPhoneNumber(phoneNumbers);
        // optional-Serial number
        request.setBizId(bizId);
        // mandatory-Sending date support30Record query within days,formatyyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        // mandatory-Page size
        request.setPageSize(10L);
        // mandatory-Current page number from1Start counting
        request.setCurrentPage(1L);

        // hint An exception may be thrown here,Pay attention tocatch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    /**
     * Generate a random six digit number
     *
     * @param from
     * @param to
     * @return
     */
    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
