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
import alb.project.system.domain.SysUser;
import alb.project.system.domain.vo.ShopExcelVO;
import alb.project.system.domain.vo.SubsidiaryCompanyExcelVO;
import alb.project.system.service.ISysDeptService;
import alb.project.system.service.ISysPostService;
import alb.project.system.service.ISysUserService;
import cn.hutool.core.convert.ConverterRegistry;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Department information
 *
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    private final ISysDeptService deptService;

    private final ISysUserService userService;

    private final ISysPostService postService;

    private final TokenService tokenService;

    public SysDeptController(ISysDeptService deptService, ISysUserService userService, ISysPostService postService, TokenService tokenService) {
        this.deptService = deptService;
        this.userService = userService;
        this.postService = postService;
        this.tokenService = tokenService;
    }

    /**
     * Get department list
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    /**
     * Querying the Department List(Eliminate the node)
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(depts);
    }

    /**
     * Get details by department number
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId) {
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * Obtain the department drop-down list
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * The department list tree of the role is displayed
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    /**
     * The new department
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "Department Management", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("The new department'" + dept.getDeptName() + "'failure,The department name already exists");
        }
        if (dept.getParentId() == 0) {
            if (deptService.selectHeadCompanyId() != null) {
                return AjaxResult.error("Add top departments'" + dept.getDeptName() + "'failure,Top-level departments already exist");
            }
        }
        dept.setCreateBy(SecurityUtils.getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * Modify the department
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "Department Management", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("Modify the department'" + dept.getDeptName() + "'failure,The department name already exists");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return AjaxResult.error("Modify the department'" + dept.getDeptName() + "'failure,The superior department cannot be itself");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return AjaxResult.error("This department contains the subdepartments that are not disabled!");
        }
        dept.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * Delete the department
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "Department Management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("There are subordinate departments,Not allowed to delete");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("A user exists in the department.,Not allowed to delete");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }


    /**
     *  Get a list of all stores
     * @return
     */
    @GetMapping(value = "/getAllShopList")
    public AjaxResult getAllShopList(){
        return AjaxResult.success(deptService.getAllShopList());
    }


    /**
     * Get list of subsidiaries
     *
     * @param dept
     * @return TableDataInfo
     */
    @PreAuthorize("@ss.hasPermi('system:dept:subsidiaryCompanyList')")
    @GetMapping(value = "/subsidiaryCompanyList")
    public TableDataInfo subsidiaryCompany(SysDept dept) {
        startPage();
        List<Map<String, Object>> list = deptService.selectSubsidiaryCompanyList(dept);
        return getDataTable(list);
    }

    /**
     * Download the subsidiary import template
     * @return
     */
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<SubsidiaryCompanyExcelVO> util = new ExcelUtil<SubsidiaryCompanyExcelVO>(SubsidiaryCompanyExcelVO.class);
        return util.importTemplateExcel("Subsidiary data");
    }

    /**
     * Subsidiary import
     *
     * @param file
     * @param updateSupport
     * @return
     */
    @Log(title = "Department Management-Subsidiary import", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:dept:subsidiaryCompanyListImport')")
    @PostMapping(value = "/subsidiaryCompanyListImport")
    public AjaxResult subsidiaryCompanyListImport(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SubsidiaryCompanyExcelVO> util = new ExcelUtil<SubsidiaryCompanyExcelVO>(SubsidiaryCompanyExcelVO.class);
        List<SubsidiaryCompanyExcelVO> list = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = deptService.importSubsidiaryCompany(list, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * export
     *
     * @param dept
     * @return
     */
    @Log(title = "Department Management-Subsidiary export", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dept:subsidiaryCompanyListExport')")
    @GetMapping(value = "/subsidiaryCompanyListExport")
    public AjaxResult subsidiaryCompanyListExport(SysDept dept) {
        List<Map<String, Object>> list = deptService.selectSubsidiaryCompanyList(dept);
        List<SubsidiaryCompanyExcelVO> resultList = new LinkedList<>();
        for (Map<String, Object> map :
                list) {
            if (map != null) {
                ConverterRegistry registry = ConverterRegistry.getInstance();
                // Reorganization of the data->export
                resultList.add(SubsidiaryCompanyExcelVO.builder()
                        .deptCode(registry.convert(String.class, map.get("deptCode")))
                        .deptName(registry.convert(String.class, map.get("deptName")))
                        .leader(registry.convert(String.class, map.get("leader")))
                        .phone(registry.convert(String.class, map.get("leaderPhone")))
                        .email(registry.convert(String.class, map.get("email")))
                        .province(registry.convert(String.class, map.get("province")))
                        .city(registry.convert(String.class, map.get("city")))
                        .district(registry.convert(String.class, map.get("district")))
                        .address(registry.convert(String.class, map.get("address")))
                        .orderNum(registry.convert(Integer.class, map.get("orderNum")))
                        .createTime(registry.convert(Date.class,map.get("createTime")))
                        .build());
            }
        }
        ExcelUtil<SubsidiaryCompanyExcelVO> util = new ExcelUtil<SubsidiaryCompanyExcelVO>(SubsidiaryCompanyExcelVO.class);
        return util.exportExcel(resultList, "Subsidiary data");
    }


    /**
     * Subsidiary Details
     *
     * @param deptId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:dept:subsidiaryCompanyInfo')")
    @GetMapping(value = "/subsidiaryCompanyInfo")
    public AjaxResult subsidiaryCompanyInfo(Long deptId) {
        if (deptId == null) {
            throw new CustomException("The department id is empty!");
        }
        // encapsulationmapreturn
        Map<String, Object> resultMap = new HashMap<>(15);
        // According to the departmentidQuerying Department Information
        SysDept dept = deptService.selectDeptById(deptId);
        if (dept == null) {
            throw new CustomException("The department data was not obtained!");
        }
        resultMap.put("deptId", dept.getDeptId() == null ? "" : dept.getDeptId());
        resultMap.put("deptCode", dept.getDeptCode() == null ? "" : dept.getDeptCode());
        resultMap.put("deptName", dept.getDeptName() == null ? "" : dept.getDeptName());
        resultMap.put("leader", dept.getLeader() == null ? "" : dept.getLeader());
        resultMap.put("email", dept.getEmail() == null ? "" : dept.getEmail());
        resultMap.put("phone", dept.getPhone() == null ? "" : dept.getPhone());
        resultMap.put("province", dept.getProvince() == null ? "" : dept.getProvince());
        resultMap.put("city", dept.getCity() == null ? "" : dept.getCity());
        resultMap.put("district", dept.getDistrict() == null ? "" : dept.getDistrict());
        resultMap.put("address", dept.getAddress() == null ? "" : dept.getAddress());
        resultMap.put("createDate", dept.getCreateTime() == null ? "" : DateUtils.formatDateToAppoint(dept.getCreateTime(), 3));

        // Calculate the number of subsidiary members Associate membership
        // Query the collection of stores under the subsidiary
        SysDept dept1 = new SysDept();
        dept1.setParentId(dept.getDeptId());
        List<Map<String, Object>> shopList = deptService.selectShopList(dept1);
        resultMap.put("shopList", shopList);
        // Query the number of stores under the subsidiary
        resultMap.put("totalNum", shopList == null ? 0 : shopList.size());
        return AjaxResult.success(resultMap);
    }

    /**
     * Get a list of all subsidiaries  Drop down list
     *
     * @return
     */
    @GetMapping(value = "/getAllSubsidiaryCompany")
    public AjaxResult getAllSubsidiaryCompany() {
        SysDept dept = new SysDept();
        dept.setType(1);
        dept.setParentId(0L);
        return AjaxResult.success(deptService.selectDeptList(dept));
    }

    /**
     * Get store list
     *
     * @param dept
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:dept:shop')")
    @GetMapping(value = "/shopList")
    public TableDataInfo shop(SysDept dept) {
        startPage();
        List<Map<String, Object>> list = deptService.selectShopList(dept);
        return getDataTable(list);
    }


    /**
     * Download the store import template
     * @return
     */
    @GetMapping("/importShopTemplate")
    public AjaxResult importShopTemplate() {
        ExcelUtil<ShopExcelVO> util = new ExcelUtil<ShopExcelVO>(ShopExcelVO.class);
        return util.importTemplateExcel("Store the data");
    }

    /**
     * Stores the import
     *
     * @param file
     * @param updateSupport
     * @return
     */
    @Log(title = "Department Management-Stores the import", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:dept:shopListImport')")
    @PostMapping(value = "/shopListImport")
    public AjaxResult shopListImport(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ShopExcelVO> util = new ExcelUtil<ShopExcelVO>(ShopExcelVO.class);
        List<ShopExcelVO> list = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = deptService.importShop(list, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * export
     *
     * @param dept
     * @return
     */
    @Log(title = "Department Management-Stores the export", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dept:shopListExport')")
    @GetMapping(value = "/shopListExport")
    public AjaxResult shopListExport(SysDept dept) {
        List<Map<String, Object>> list = deptService.selectShopList(dept);
        List<ShopExcelVO> resultList = new LinkedList<>();
        for (Map<String, Object> map :
                list) {
            if (map != null) {
                ConverterRegistry registry = ConverterRegistry.getInstance();
                // Example Query parent information
                SysDept info = deptService.selectDeptById(registry.convert(Long.class,map.get("parentId")));
                // Reorganization of the data->export
                resultList.add(ShopExcelVO.builder()
                        .parentDeptCode(info == null ? "" : info.getDeptCode() == null ? "" : info.getDeptCode())
                        .parentDeptName(info == null ? "" : info.getDeptName() == null ? "" : info.getDeptName())
                        .deptCode(registry.convert(String.class, map.get("deptCode")))
                        .deptName(registry.convert(String.class, map.get("deptName")))
                        .deptType(registry.convert(Integer.class,map.get("deptType")))
                        .leader(registry.convert(String.class, map.get("leader")))
                        .phone(registry.convert(String.class, map.get("leaderPhone")))
                        .email(registry.convert(String.class, map.get("email")))
                        .province(registry.convert(String.class, map.get("province")))
                        .city(registry.convert(String.class, map.get("city")))
                        .district(registry.convert(String.class, map.get("district")))
                        .address(registry.convert(String.class, map.get("address")))
                        .orderNum(registry.convert(Integer.class, map.get("orderNum")))
                        .createTime(registry.convert(Date.class,map.get("createTime")))
                        .build());
            }
        }
        ExcelUtil<ShopExcelVO> util = new ExcelUtil<ShopExcelVO>(ShopExcelVO.class);
        return util.exportExcel(resultList, "Store the data");
    }


    /**
     * Querying store information
     *
     * @param deptId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:dept:shopInfo')")
    @GetMapping(value = "/shopInfo")
    public AjaxResult shopInfo(Long deptId) {
        if (deptId == null) {
            throw new CustomException("The department id is empty!");
        }
        // encapsulationmapreturn
        Map<String, Object> resultMap = new HashMap<>(14);
        // According to the departmentidQuerying Department Information
        SysDept dept = deptService.selectDeptById(deptId);
        if (dept == null) {
            throw new CustomException("The department data was not obtained!");
        }
        SysDept parentDept = deptService.selectDeptById(dept.getParentId());
        resultMap.put("deptId", dept.getDeptId() == null ? "" : dept.getDeptId());
        resultMap.put("deptCode", dept.getDeptCode() == null ? "" : dept.getDeptCode());
        resultMap.put("deptName", dept.getDeptName() == null ? "" : dept.getDeptName());
        resultMap.put("deptPhone", dept.getDeptPhone() == null ? "" : dept.getDeptPhone());
        resultMap.put("parentDeptName", parentDept == null ? "" : parentDept.getDeptName() == null ? "" : parentDept.getDeptName());
        resultMap.put("leader", dept.getLeader() == null ? "" : dept.getLeader());
        resultMap.put("email", dept.getEmail() == null ? "" : dept.getEmail());
        resultMap.put("phone", dept.getPhone() == null ? "" : dept.getPhone());
        resultMap.put("province", dept.getProvince() == null ? "" : dept.getProvince());
        resultMap.put("city", dept.getCity() == null ? "" : dept.getCity());
        resultMap.put("district", dept.getDistrict() == null ? "" : dept.getDistrict());
        resultMap.put("address", dept.getAddress() == null ? "" : dept.getAddress());
        resultMap.put("deptType", dept.getDeptType() == null ? 0 : dept.getDeptType());
        resultMap.put("orderNum", dept.getOrderNum() == null ? 0 : dept.getOrderNum());
        resultMap.put("createDate", dept.getCreateTime() == null ? "" : DateUtils.formatDateToAppoint(dept.getCreateTime(), 3));
        // Query the number of employees in the store
        Integer employeesNum = deptService.countEmployeesNumByDeptId(deptId);
        resultMap.put("employeesNum", employeesNum);

        return AjaxResult.success(resultMap);
    }

    /**
     * Obtain the list of employees in the department
     *
     * @param deptId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:dept:deptEmployeesList')")
    @GetMapping(value = "/deptEmployeesList")
    public AjaxResult deptEmployeesList(Long deptId) {
        if (deptId == null) {
            throw new CustomException("The department id is empty!");
        }
        List<SysUser> list = userService.selectDeptEmployeesList(deptId);
        for (SysUser su :
                list) {
            List<String> postNameList = postService.selectPostNameByUserId(su.getUserId());
            su.setPostName(StringUtils.join(postNameList, ","));
        }
        return AjaxResult.success(list);
    }

}
