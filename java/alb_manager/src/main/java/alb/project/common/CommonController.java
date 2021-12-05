package alb.project.common;

import alb.common.constant.Constants;
import alb.common.utils.StringUtils;
import alb.common.utils.file.FileUploadUtils;
import alb.common.utils.file.FileUtils;
import alb.framework.config.ServerConfig;
import alb.framework.config.WlwqConfig;
import alb.framework.redis.RedisCache;
import alb.framework.web.domain.AjaxResult;
import alb.project.system.domain.SysDept;
import alb.project.system.domain.SysUser;
import alb.project.system.service.ISysConfigService;
import alb.project.system.service.ISysDeptService;
import alb.project.system.service.ISysUserService;
import alb.project.vacation.domain.ParamsData;
import alb.project.vacation.service.IParamsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Generic request processing
 *
 */
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    // Match a phone number
    private static final Pattern phonePattern = Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IParamsDataService paramsDataService; // Parameter Settings
    // The cache
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDeptService deptService;

    /**
     *
     * @Date: 2020/7/21 17:25
     * @Description: Get login background and technical phone
     */
    @RequestMapping(value = "/getLoginBackGroundPicAndTechnicalPhone", method = RequestMethod.GET)
    public AjaxResult getLoginBackGroundPicAndTechnicalPhone() {
        ParamsData backGroundPic = paramsDataService.selectParamsDataById(7L);
        ParamsData technicalPhone = paramsDataService.selectParamsDataById(8L);
        Map<String, String> resultMap = new HashMap<>();
        String value = "";
        if (backGroundPic != null && backGroundPic.getValue() != null) {
            value = configService.selectConfigByKey(backGroundPic.getValue());
        }
        resultMap.put("backGroundPic", value);
        resultMap.put("technicalPhone", technicalPhone == null ? "" : technicalPhone.getValue() == null ? "" : technicalPhone.getValue());
        return AjaxResult.success(resultMap);
    }

    /**
     * Create By Renbowen
     *
     * @Date: 2020/7/22 17:07
     * @Description: Get the name based on the login account
     */
    @RequestMapping(value = "/getDeptNameByUserName", method = RequestMethod.GET)
    public AjaxResult getDeptNameByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        SysUser sysUser = sysUserService.selectUserByUserName(userName);
        if (sysUser == null) {
            return null;
        }
        SysDept dept = deptService.selectDeptById(sysUser.getDeptId());
        return AjaxResult.success(AjaxResult.successMsg, dept == null ? "" : dept.getDeptName() == null ? "" : dept.getDeptName());
    }

    /**
     * Common download request
     *
     * @param fileName The file name
     * @param delete   Whether or not to delete
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("The file name({})illegal,Download not allowed。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = WlwqConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("Failed to download file", e);
        }
    }

    /**
     * Common upload request
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // Path to upload files
            String filePath = WlwqConfig.getUploadPath();
            // Upload and return the new file name
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * Local resources universal download
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Local Resource Path
        String localPath = WlwqConfig.getProfile();
        // Database resource address
        String downloadPath = localPath + StringUtils.substringAfter(name, Constants.RESOURCE_PREFIX);
        // Download the name
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

//    /**
//     * Push to incoming pool buffer queue
//     *
//     * @return
//     */
//    @CrossOrigin
//    @RequestMapping(value = "/pushIncomingPoolMq",
//            method = RequestMethod.PUT)
//    public void pushIncomingPool(@RequestBody IncomingPool incomingPool) {
//        String phone = incomingPool.getPhone();
//        int sourceType = incomingPool.getSourceType() != null ? (int) incomingPool.getSourceType().longValue() : 1;
//        if (!phonePattern.matcher(phone).matches()) {
//            return;
//        }
//        switch (sourceType) {
//            case 2: // China southern airlines
//                break;
//            case 3: // Air China
//                break;
//            case 4: // China Eastern airlines
//                break;
//            case 5: // hna
//                break;
//            default:
//                sourceType = 1; // The phone
//        }
//        // Put into message queue
//        List<String> list = new ArrayList<>();
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectMapper mapper = new ObjectMapper();
//            JsonGenerator jg = mapper.getFactory().createGenerator(byteArrayOutputStream, JsonEncoding.UTF8);
//            jg.writeStartObject(); // {
//            // The phone
//            jg.writeStringField("phone", phone);
//            // gender (1=male,2=female,3=The unknown) => (0=male,1=female,2=The unknown)
//            jg.writeNumberField("sex", 2);
//            // source
//            jg.writeNumberField("sourceType", sourceType);
//            jg.writeEndObject(); // }
//            jg.close();
//            list.add(byteArrayOutputStream.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        redisCache.setCacheList("IncomingPoolMq", list);
//    }
}
