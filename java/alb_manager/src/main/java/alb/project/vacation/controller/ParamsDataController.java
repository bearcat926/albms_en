package alb.project.vacation.controller;

import alb.common.exception.CustomException;
import alb.project.vacation.domain.ParamsData;
import alb.project.vacation.service.IParamsDataService;
import alb.project.system.domain.SysConfig;
import alb.project.system.service.ISysConfigService;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import alb.common.utils.FileToMultipartFile;
import alb.common.utils.SecurityUtils;
import alb.common.utils.file.FileUploadUtils;
import alb.common.utils.poi.ExcelUtil;
import alb.framework.aspectj.lang.annotation.Log;
import alb.framework.aspectj.lang.enums.BusinessType;
import alb.framework.config.WlwqConfig;
import alb.framework.web.controller.BaseController;
import alb.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Parameter SettingsController
 *
 * @date 2020-07-16
 */
@RestController
@RequestMapping("/vacation/paramsData")
public class ParamsDataController extends BaseController {
    private final IParamsDataService paramsDataService;

    @Autowired
    private ISysConfigService configService;
    @Value("${shareUrl}")
    private String shareUrl;

    public ParamsDataController(IParamsDataService paramsDataService) {
        this.paramsDataService = paramsDataService;
    }

    /**
     * Query the parameter setting list by type-Drop down list yo
     */
    @GetMapping("/getListByType")
    public AjaxResult getListByType(ParamsData paramsData) {
        List<ParamsData> list = paramsDataService.selectParamsDataList(paramsData);
        return AjaxResult.success(list);
    }

    /**
     * Example Query the parameter list
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:list')")
    @GetMapping("/list")
    public AjaxResult list(ParamsData paramsData) {
        List<ParamsData> list = paramsDataService.selectParamsDataList(paramsData);
        // Login page image link replacement
        list.stream().filter(e -> e.getType() == 6).forEach(e -> {
            String src = configService.selectConfigByKey(e.getValue());
            e.setValue(src);
        });
        return AjaxResult.success(list);
    }

    /**
     * Example Export the parameter setting list
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:export')")
    @Log(title = "Parameter Settings", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ParamsData paramsData) {
        List<ParamsData> list = paramsDataService.selectParamsDataList(paramsData);
        ExcelUtil<ParamsData> util = new ExcelUtil<ParamsData>(ParamsData.class);
        return util.exportExcel(list, "paramsData");
    }

    /**
     * Gets details about parameter Settings
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(paramsDataService.selectParamsDataById(id));
    }

    /**
     * The login page can only be written in fixed form for the time being
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:query')")
    @GetMapping(value = "/backGroundPic/{id}")
    public AjaxResult getBackGroundPic(@PathVariable("id") Long id) {
        ParamsData backGroundPic = paramsDataService.selectParamsDataById(id);
        if (backGroundPic != null && backGroundPic.getValue() != null) {
            backGroundPic.setValue(configService.selectConfigByKey(backGroundPic.getValue()));
        }
        return AjaxResult.success(backGroundPic);
    }

    /**
     * Generate qr code
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getQrCode")
    public AjaxResult getQrCode(Long id) throws IOException {
        if (id == null) {
            throw new CustomException("Logo is empty!");
        }
        ParamsData data = paramsDataService.selectParamsDataById(id);
        if (data == null) {
            throw new CustomException("No details were obtained!");
        }
        ConverterRegistry converter = ConverterRegistry.getInstance();
        String path = WlwqConfig.getAvatarPath() + "/qrCode.jpg";
        QrCodeUtil.generate(shareUrl + data.getValue(), 300, 300, FileUtil.file(path));
        String qrCodePic = FileUploadUtils.upload(WlwqConfig.getAvatarPath(), FileToMultipartFile.getMulFileByPath(path));
        AjaxResult result = new AjaxResult();
        result.put("qrCodePic", qrCodePic);
        return AjaxResult.success(result);
    }

    /**
     * New Parameter Settings
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:add')")
    @Log(title = "Parameter Settings", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ParamsData paramsData) {
        paramsData.setLastDate(new Date());
        return toAjax(paramsDataService.insertParamsData(paramsData));
    }

    /**
     * Modifying Parameter Settings
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:edit')")
    @Log(title = "Parameter Settings", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ParamsData paramsData) {
        paramsData.setLastDate(new Date());
        return toAjax(paramsDataService.updateParamsData(paramsData));
    }

    /**
     * Modifying the Login page
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:edit')")
    @Log(title = "Parameter Settings", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/backGroundPic")
    public AjaxResult editBackGroundPic(@RequestBody ParamsData paramsData) {
        // Get the original object
        ParamsData backGroundPic = paramsDataService.selectParamsDataById(paramsData.getId());
        // Gets the parameter configuration object
        SysConfig config = configService.selectConfigObjectByKey(backGroundPic.getValue());
        // Update picture new value
        config.setConfigValue(paramsData.getValue());
        // Update the parameter configuration object
        config.setUpdateBy(SecurityUtils.getUsername());
        configService.updateConfig(config);
        // Updates the time status of an object
        backGroundPic.setLastDate(new Date());
        return toAjax(paramsDataService.updateParamsData(backGroundPic));
    }

    /**
     * Delete parameter Settings
     */
    @PreAuthorize("@ss.hasPermi('vacation:paramsData:remove')")
    @Log(title = "Parameter Settings", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(paramsDataService.deleteParamsDataByIds(ids));
    }
}
