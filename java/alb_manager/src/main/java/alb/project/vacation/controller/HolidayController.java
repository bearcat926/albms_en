package alb.project.vacation.controller;

import alb.common.utils.ServletUtils;
import alb.common.utils.StringUtils;
import alb.framework.aspectj.lang.annotation.Log;
import alb.framework.aspectj.lang.enums.BusinessType;
import alb.framework.security.LoginUser;
import alb.framework.security.service.PermissionService;
import alb.framework.security.service.TokenService;
import alb.framework.web.controller.BaseController;
import alb.framework.web.domain.AjaxResult;
import alb.framework.web.page.TableDataInfo;
import alb.project.system.domain.SysRole;
import alb.project.system.domain.SysUser;
import alb.project.system.service.ISysUserService;
import alb.project.vacation.domain.Holiday;
import alb.project.vacation.domain.HolidayApproval;
import alb.project.vacation.domain.HolidayItem;
import alb.project.vacation.paramsVO.HolidayUserParamsVO;
import alb.project.vacation.resultVO.HolidayUserResultVO;
import alb.project.vacation.service.IHolidayApprovalService;
import alb.project.vacation.service.IHolidayItemService;
import alb.project.vacation.service.IHolidayService;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoHao
 * Created on 2021/1/26.
 */

@RestController
@RequestMapping("/vacation/holiday")
public class HolidayController extends BaseController {

    @Resource // @Resource(This annotation belongs toJ2EEthe),The assembly is by name by default
    private IHolidayService holidayService;
    @Resource
    private IHolidayItemService holidayItemService;
    @Resource
    private IHolidayApprovalService holidayApprovalService;
    @Resource
    private ISysUserService userService;

    @Autowired // @Autowired(This annotation belongs tospringthe),The default assembly is by type
    private TokenService tokenService;

    /**
     * Example Query a single piece of data
     *
     * @param holidayId A primary key
     * @return Instance objects
     */
    @GetMapping("/{holidayId}")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:query')")
    public AjaxResult queryHoliday(@PathVariable("holidayId") Long holidayId) {
        Holiday result = this.holidayService.queryOne(holidayId);
        return result != null ? AjaxResult.success(result) : AjaxResult.error();
    }

    /**
     * Whether there is a next node
     *
     * @param holidayId A primary key
     * @return Instance objects
     */
    @GetMapping("/next/{holidayId}")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:query')")
    public AjaxResult hasNext(@PathVariable("holidayId") Long holidayId) {
        Holiday holiday = this.holidayService.queryOne(holidayId);
        // The default role is the first role
        Long roleId = this.userService.selectUserById(holiday.getProposerId()).getRoles().get(0).getRoleId();
        HolidayApproval params = HolidayApproval.builder()
                .holidayTypeId(holiday.getHolidayTypeId())
                .roleId(roleId)
                .currentApprovedIndex(holiday.getCurrentApprovedIndex())
                .build();
        return this.holidayApprovalService.hasNextApproved(params) ? AjaxResult.success("The next node exists") : AjaxResult.success(null);
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holiday
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:list')")
    public TableDataInfo queryList(Holiday holiday) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (!loginUser.getPermissions().contains(PermissionService.ALL_PERMISSION)) {
            holiday.setProposerId(loginUser.getUser().getUserId());
        }
        startPage();
        List<Holiday> list = this.holidayService.queryAll(holiday);
        return getDataTable(list);
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holiday
     * @return
     */
    @GetMapping("/approvalList")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:list')")
    public TableDataInfo queryApprovalList(Holiday holiday) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (!loginUser.getPermissions().contains(PermissionService.ALL_PERMISSION)) {
            holiday.setCurrentApproverId(loginUser.getUser().getUserId());
        }
        startPage();
        List<Holiday> list = this.holidayService.queryAll(holiday);
        return getDataTable(list);
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holiday
     * @return
     */
    @GetMapping("/user/list")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:list')")
    public AjaxResult queryUserList(Holiday holiday) {
        if (holiday.getHolidayId() != null) { // Approval Stage query
            List<HolidayUserResultVO> result = new ArrayList<>();
            // User to be approved
            SysUser user = this.userService.selectUserById(holiday.getProposerId());
            Long deptId = user.getDept().getDeptId();
            SysRole role = user.getRoles().get(0);
            // Based on type and current user roleID,Query default approval
            HolidayApproval approvalParams = HolidayApproval.builder()
                    .holidayTypeId(holiday.getHolidayTypeId())
                    .roleId(role.getRoleId())
                    .currentApprovedIndex(holiday.getCurrentApprovedIndex())
                    .build();
            List<HolidayApproval> approvals = holidayApprovalService.queryAll(approvalParams);
            // According to the departmentIDAnd the role to be queriedID,Example Query available users
            for (HolidayApproval approval : approvals) {
                HolidayUserParamsVO paramsVO = HolidayUserParamsVO.builder()
                        .deptId(deptId)
                        .roleId(approval.getNextApprovalId())
                        .build();
                List<HolidayUserResultVO> resultVOList = holidayService.selectUserList(paramsVO);
                result.addAll(resultVOList);
            }
            return AjaxResult.success(result);
        } else { // New Phase Query
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (!StringUtils.isNull(loginUser) && !CollectionUtils.isEmpty(loginUser.getUser().getRoles())) {
                List<HolidayUserResultVO> result = new ArrayList<>();

                Long deptId = loginUser.getUser().getDept().getDeptId();
                SysRole role = loginUser.getUser().getRoles().get(0);
                // Based on type and current user roleID,Query default approval
                HolidayApproval approvalParams = HolidayApproval.builder()
                        .holidayTypeId(holiday.getHolidayTypeId())
                        .roleId(role.getRoleId())
                        .currentApprovedIndex(holiday.getCurrentApprovedIndex())
                        .build();
                List<HolidayApproval> approvals = holidayApprovalService.queryAll(approvalParams);
                // According to the departmentIDAnd the role to be queriedID,Example Query available users
                for (HolidayApproval approval : approvals) {
                    HolidayUserParamsVO paramsVO = HolidayUserParamsVO.builder()
                            .deptId(deptId)
                            .roleId(approval.getApprovedRoleId())
                            .build();
                    List<HolidayUserResultVO> resultVOList = holidayService.selectUserList(paramsVO);
                    result.addAll(resultVOList);
                }
                return AjaxResult.success(result);
            }
        }
        return AjaxResult.error("The current user has no permission!");
    }

    /**
     * The new data
     *
     * @param holiday Instance objects
     * @return Instance objects
     */
    @PostMapping("")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:add')")
    @Log(title = "During the holiday", businessType = BusinessType.INSERT)
    public AjaxResult addHoliday(@RequestBody Holiday holiday) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        // Snowflake algorithm generates unique call log numbers Individual services The data centeridAnd terminalidAll fill in1
        Long holidayId = IdUtil.getSnowflake(1, 1).nextId();

        holiday.setHolidayId(holidayId);
        holiday.setProposerId(loginUser.getUser().getUserId());
        holiday.setCurrentApprovedIndex(1);

        HolidayApproval params = HolidayApproval.builder()
                .holidayTypeId(holiday.getHolidayTypeId())
                .roleId(loginUser.getUser().getRoles().get(0).getRoleId())
                .approvedRoleId(this.userService.selectUserById(holiday.getCurrentApproverId()).getRoles().get(0).getRoleId())
                .build();

        HolidayItem holidayItem = HolidayItem.builder()
                .holidayId(holiday.getHolidayId())
                .approvedUserId(holiday.getCurrentApproverId())
                .approvedIndex(this.holidayApprovalService.queryAll(params).get(0).getCurrentApprovedIndex())
                .status(0)
                .approveInstruction(null)
                .delFlag(0)
                .build();
        int countItem = this.holidayItemService.insert(holidayItem);

        int count = this.holidayService.insert(holiday);
        return count > 0 & countItem > 0 ? AjaxResult.success("New success") : AjaxResult.error("The new failure");
    }

    /**
     * Modify the data
     *
     * @param holiday Instance objects
     * @return Instance objects
     */
    @PutMapping("")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:edit')")
    @Log(title = "During the holiday", businessType = BusinessType.UPDATE)
    public AjaxResult modifyHoliday(@RequestBody Holiday holiday) {
        // Save the current approval items
        HolidayItem holidayItem = HolidayItem.builder()
                .holidayId(holiday.getHolidayId())
                .approvedIndex(holiday.getCurrentApprovedIndex())
                .status(holiday.getStatus())
                .approveInstruction(holiday.getHolidayInstruction())
                .build();
        int countItem = this.holidayItemService.update(holidayItem);

        // If it passes and there is a next node,Then add the next item
        // The default role is the first role
        Long roleId = this.userService.selectUserById(holiday.getProposerId()).getRoles().get(0).getRoleId();
        HolidayApproval params = HolidayApproval.builder()
                .holidayTypeId(holiday.getHolidayTypeId())
                .roleId(roleId)
                .currentApprovedIndex(holiday.getCurrentApprovedIndex())
                .build();
        if (this.holidayApprovalService.hasNextApproved(params) && holiday.getStatus() == 1) {
            HolidayItem newHolidayItem = HolidayItem.builder()
                    .holidayId(holiday.getHolidayId())
                    .approvedUserId(holiday.getCurrentApproverId())
                    .approvedIndex(holiday.getCurrentApprovedIndex() + 1)
                    .status(0)
                    .delFlag(0)
                    .build();
            this.holidayItemService.insert(newHolidayItem);

            holiday.setCurrentApprovedIndex(holiday.getCurrentApprovedIndex() + 1);
            holiday.setStatus(0);
        }
        // rejected / There's no next node
        int count = this.holidayService.update(holiday);
        return count > 0 & countItem > 0? AjaxResult.success("Modify the success") : AjaxResult.error("Modify the failure");
    }

    /**
     * Delete data by primary key
     *
     * @param holidayId A primary key
     * @return The success of
     */
    @DeleteMapping("/{holidayId}")
    @PreAuthorize("@ss.hasPermi('vacation:holiday:delete')")
    @Log(title = "During the holiday", businessType = BusinessType.DELETE)
    public AjaxResult deleteById(@PathVariable("holidayId") Long holidayId) {
        int count = this.holidayService.deleteById(holidayId);
        return count > 0 ? AjaxResult.success("Delete the success") : AjaxResult.error("Delete failed");
    }
}
