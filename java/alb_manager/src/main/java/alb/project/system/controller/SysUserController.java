package alb.project.system.controller;

import alb.common.constant.UserConstants;
import alb.common.exception.CustomException;
import alb.common.utils.DateUtils;
import alb.common.utils.SecurityUtils;
import alb.common.utils.ServletUtils;
import alb.common.utils.StringUtils;
import alb.common.utils.poi.ExcelUtil;
import alb.framework.aspectj.lang.annotation.Log;
import alb.framework.aspectj.lang.enums.BusinessType;
import alb.framework.security.LoginUser;
import alb.framework.security.service.TokenService;
import alb.framework.web.controller.BaseController;
import alb.framework.web.domain.AjaxResult;
import alb.framework.web.page.TableDataInfo;
import alb.project.system.domain.SysDept;
import alb.project.system.domain.SysRole;
import alb.project.system.domain.SysUser;
import alb.project.system.service.ISysDeptService;
import alb.project.system.service.ISysPostService;
import alb.project.system.service.ISysRoleService;
import alb.project.system.service.ISysUserService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The user information
 *
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    private final ISysUserService userService;

    private final ISysRoleService roleService;

    private final ISysPostService postService;

    private final TokenService tokenService;

    private final ISysDeptService deptService;

    public SysUserController(ISysUserService userService, ISysRoleService roleService, ISysPostService postService, TokenService tokenService, ISysDeptService deptService) {
        this.userService = userService;
        this.roleService = roleService;
        this.postService = postService;
        this.tokenService = tokenService;
        this.deptService = deptService;
    }

    /**
     * statistics
     * @return
     */
    @RequestMapping(value = "/topData",method = RequestMethod.GET)
    public AjaxResult topData(){
        List<String> monthList = DateUtils.getInitMonthMapWithZero();
        String thisMonth = DateUtil.format(new Date(), "yyyy-MM");
        String lastMonth = DateUtil.format(DateUtil.lastMonth(), "yyyy-MM");
        // Number of head office employees
        List<Integer> totalUserNumList = new ArrayList<>();
        // Number of subsidiaries
        List<Integer> totalCompanyNumList = new ArrayList<>();
//        // Number of stores
//        List<Integer> totalShopNumList = new ArrayList<>();
//        // Number of optometrists
//        List<Integer> totalOptometristNumList = new ArrayList<>();
        for (String month : monthList) {
            // Get the number of employees by month
            totalUserNumList.add(userService.getTotalNumByMonth(month));
            // Obtain subsidiary data by month
            totalCompanyNumList.add(deptService.getTotalCompanyNumByMonth(month));
//            // Obtain the number of stores by month
//            totalShopNumList.add(deptService.getTotalShopNumByMonth(month));
//            // According to the number of optometrists per month
//            totalOptometristNumList.add(userService.getTotalOptometristNumByMonth(month));
        }


        // Number of head office employees
        Integer totalUserNum = userService.getTotalNum();
        // Head office headcount this month
        Integer thisMonthTotalUserNum = userService.getTotalNumByMonth(thisMonth);
        // Head office headcount last month
        Integer lastMonthTotalUserNum = userService.getTotalNumByMonth(lastMonth);
        String totalUserNumRatio = null;
        if (lastMonthTotalUserNum > 0) {
            totalUserNumRatio = NumberUtil.div(NumberUtil.sub(thisMonthTotalUserNum, lastMonthTotalUserNum), lastMonthTotalUserNum,2) + "%";
        } else if (NumberUtil.compare(thisMonthTotalUserNum, lastMonthTotalUserNum) == 0) {
            totalUserNumRatio = "+0%";
        } else {
            totalUserNumRatio = "+100%";
        }

        // Number of subsidiaries
        Integer totalCompanyNum = deptService.getTotalCompanyNum();
        // Number of subsidiaries this month
        Integer thisMonthTotalCompanyNum = deptService.getTotalShopNumByMonth(thisMonth);
        // Number of subsidiaries last month
        Integer lastMonthTotalCompanyNum = deptService.getTotalShopNumByMonth(lastMonth);
        String totalCompanyNumRatio = null;
        if (lastMonthTotalCompanyNum > 0) {
            totalCompanyNumRatio = NumberUtil.div(NumberUtil.sub(thisMonthTotalCompanyNum, lastMonthTotalCompanyNum), lastMonthTotalCompanyNum,2) + "%";
        } else if (NumberUtil.compare(thisMonthTotalCompanyNum, lastMonthTotalCompanyNum) == 0) {
            totalCompanyNumRatio = "+0%";
        } else {
            totalCompanyNumRatio = "+100%";
        }

//        // Number of stores
//        Integer totalShopNum = deptService.getTotalShopNum();
//        // Number of stores this month
//        Integer thisMonthTotalShopNum = deptService.getTotalShopNumByMonth(thisMonth);
//        // Number of stores in last month
//        Integer lastMonthTotalShopNum = deptService.getTotalShopNumByMonth(lastMonth);
//        String totalShopNumRatio = null;
//        if (lastMonthTotalShopNum > 0) {
//            totalShopNumRatio = NumberUtil.div(NumberUtil.sub(thisMonthTotalShopNum, lastMonthTotalShopNum), lastMonthTotalShopNum,2) + "%";
//        } else if (NumberUtil.compare(thisMonthTotalShopNum, lastMonthTotalShopNum) == 0) {
//            totalShopNumRatio = "+0%";
//        } else {
//            totalShopNumRatio = "+100%";
//        }
//
//        // Number of optometrists
//        Integer totalOptometristNum = userService.getTotalOptometristNum();
//        // Number of optometrists this month
//        Integer thisMonthTotalOptometristNum = userService.getTotalOptometristNumByMonth(thisMonth);
//        // Number of optometrists last month
//        Integer lastMonthTotalOptometristNum = userService.getTotalOptometristNumByMonth(lastMonth);
//        String totalOptometristNumRatio = null;
//        if (lastMonthTotalOptometristNum > 0) {
//            totalOptometristNumRatio = NumberUtil.div(NumberUtil.sub(thisMonthTotalOptometristNum, lastMonthTotalOptometristNum), lastMonthTotalOptometristNum,2) + "%";
//        } else if (NumberUtil.compare(thisMonthTotalOptometristNum, lastMonthTotalOptometristNum) == 0) {
//            totalOptometristNumRatio = "+0%";
//        } else {
//            totalOptometristNumRatio = "+100%";
//        }

        Map<String, Object> resultMap = new HashMap<>();
        // The date of
        resultMap.put("monthList",monthList);
        // Number of head office employees
        resultMap.put("totalUserNum", totalUserNum);
        // Head office staff compared with last month
        resultMap.put("totalUserNumRatio", totalUserNumRatio);
        // Head office employee number chart
        resultMap.put("totalUserNumList", totalUserNumList);
        // Number of subsidiaries
        resultMap.put("totalCompanyNum", totalCompanyNum);
        // Number of subsidiaries compared with last month
        resultMap.put("totalCompanyNumRatio", totalCompanyNumRatio);
        // Number of subsidiaries graph
        resultMap.put("totalCompanyNumList", totalCompanyNumList);
//        // Number of stores
//        resultMap.put("totalShopNum", totalShopNum);
//        // The number of stores compared with last month
//        resultMap.put("totalShopNumRatio", totalShopNumRatio);
//        // Number of stores graph
//        resultMap.put("totalShopNumList", totalShopNumList);
//        // Number of optometrists
//        resultMap.put("totalOptometristNum", totalOptometristNum);
//        // Number of optometrists compared with last month
//        resultMap.put("totalOptometristNumRatio", totalOptometristNumRatio);
//        // Number of optometrists chart
//        resultMap.put("totalOptometristNumList",totalOptometristNumList);
        return AjaxResult.success(resultMap);
    }

    /**
     * Get user list
     */
    @GetMapping("/allList")
    public AjaxResult allList(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return AjaxResult.success(list);
    }

    /**
     * Get user list
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        for (SysUser su :
                list) {
            List<String> postNameList = postService.selectPostNameByUserId(su.getUserId());
            su.setPostName(StringUtils.join(postNameList, ","));
        }
        return getDataTable(list);
    }

    @Log(title = "User Management", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        for (SysUser su :
                list) {
            List<String> postNameList = postService.selectPostNameByUserId(su.getUserId());
            su.setPostName(StringUtils.join(postNameList, ","));
        }
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "User data");
    }

    @Log(title = "User Management", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("User data");
    }

    /**
     * Get details based on the user number
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * New users
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "User Management", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("New users'" + user.getUserName() + "'failure,The login account already exists");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("New users'" + user.getUserName() + "'failure,The mobile phone number already exists");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("New users'" + user.getUserName() + "'failure,The email account already exists");
        }
        user.setUserCode(userService.getLastUserCode());
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * Modify the user
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "User Management", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("Modify the user'" + user.getUserName() + "'failure,The mobile phone number already exists");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("Modify the user'" + user.getUserName() + "'failure,The email account already exists");
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * Delete user
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "User Management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * To reset your password
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "User Management", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * State changes
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "User Management", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUserStatus(user));
    }


    /**
     * Querying User Details
     *
     * @param userId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/userInfo")
    public AjaxResult userInfo(Long userId) {
        if (userId == null) {
            throw new CustomException("The user ID is empty!");
        }
        Map<String, Object> resultMap = new HashMap<>(12);
        // Obtaining User information
        SysUser user = userService.selectUserById(userId);
        // Get user position information
        List<String> postNameList = postService.selectPostNameByUserId(userId);
        if (user != null) {
            // Querying Department Information
            SysDept dept = deptService.selectDeptById(user.getDeptId());
            // The user information
            resultMap.put("nickName", user.getNickName() == null ? "" : user.getNickName());
            resultMap.put("userCode", user.getUserCode() == null ? "" : user.getUserCode());
            resultMap.put("deptName", dept == null ? "" : dept.getDeptName() == null ? "" : dept.getDeptName());
            resultMap.put("postName", StringUtils.join(postNameList, ","));
            resultMap.put("status", user.getStatus() == null ? "" : "0".equals(user.getStatus()) ? "on-the-job" : "departure");
            resultMap.put("sex", user.getSex() == null ? "The unknown" : "1".equals(user.getSex()) ? "male" : "2".equals(user.getSex()) ? "female" : "The unknown");
            resultMap.put("phoneNumber", user.getPhonenumber() == null ? "" : user.getPhonenumber());
            StringBuilder address = new StringBuilder();
            if (user.getProvince()!= null){
                address.append(user.getProvince()).append(" ");
            }if (user.getCity() != null){
                address.append(user.getCity()).append(" ");
            }if (user.getDistrict() != null){
                address.append(user.getDistrict()).append(" ");
            }
            resultMap.put("address", address.toString());
        }
        // User details statistics
        // In the time
        resultMap.put("hireDate", user == null ? "" : user.getCreateTime() == null ? "" : DateUtils.formatDateToAppoint(user.getCreateTime(), 3));
        // The induction time
        resultMap.put("hireTotal", DateUtils.dayComparePrecise(user == null ? new Date() : user.getCreateTime() == null ? new Date() : user.getCreateTime(), new Date()));
        return AjaxResult.success(resultMap);
    }


}