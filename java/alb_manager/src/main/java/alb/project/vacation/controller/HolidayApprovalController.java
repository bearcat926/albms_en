package alb.project.vacation.controller;

import alb.framework.aspectj.lang.annotation.Log;
import alb.framework.aspectj.lang.enums.BusinessType;
import alb.framework.web.controller.BaseController;
import alb.framework.web.domain.AjaxResult;
import alb.framework.web.page.TableDataInfo;
import alb.project.vacation.domain.HolidayApproval;
import alb.project.vacation.service.IHolidayApprovalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HaoHao
 * Created on 2021/1/26.
 */

@RestController
@RequestMapping("/vacation/holiday/approval")
public class HolidayApprovalController extends BaseController {

    @Resource // @Resource(This annotation belongs toJ2EEthe),The assembly is by name by default
    private IHolidayApprovalService holidayApprovalService;

    /**
     * Example Query a single piece of data
     *
     * @param holidayApprovalId A primary key
     * @return Instance objects
     */
    @GetMapping("/{holidayApprovalId}")
    @PreAuthorize("@ss.hasPermi('vacation:holidayApproval:query')")
    public AjaxResult queryHolidayApproval(@PathVariable("holidayApprovalId") Long holidayApprovalId) {
        HolidayApproval result = this.holidayApprovalService.queryOne(holidayApprovalId);
        return result != null ? AjaxResult.success(result) : AjaxResult.error();
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holidayApproval
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('vacation:holidayApproval:list')")
    public TableDataInfo queryList(HolidayApproval holidayApproval) {
        startPage();
        List<HolidayApproval> list = this.holidayApprovalService.queryAll(holidayApproval);
        return getDataTable(list);
    }

    /**
     * Querying the Role List
     *
     * @return The object list
     */
    @GetMapping("/roleList")
    @PreAuthorize("@ss.hasPermi('vacation:holidayApproval:list')")
    public AjaxResult queryHolidayApproval() {
        return AjaxResult.success(this.holidayApprovalService.selectRoleList());
    }

    /**
     * The new data
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    @PostMapping("")
    @PreAuthorize("@ss.hasPermi('vacation:holidayApproval:add')")
    @Log(title = "During the holiday", businessType = BusinessType.INSERT)
    public AjaxResult addHolidayApproval(@RequestBody HolidayApproval holidayApproval) {
        if (this.holidayApprovalService.hasRing(holidayApproval)) {
            return AjaxResult.error("The new failure,Unable to add item");
        }
        int count = this.holidayApprovalService.insert(holidayApproval);
        return count > 0 ? AjaxResult.success("New success") : AjaxResult.error("The new failure");
    }

    /**
     * Delete data by primary key
     *
     * @param holidayApprovalId A primary key
     * @return The success of
     */
    @DeleteMapping("/{holidayApprovalId}")
    @PreAuthorize("@ss.hasPermi('vacation:holidayApproval:delete')")
    @Log(title = "During the holiday", businessType = BusinessType.DELETE)
    public AjaxResult deleteById(@PathVariable("holidayApprovalId") Long holidayApprovalId) {
        int count = this.holidayApprovalService.deleteById(holidayApprovalId);
        return count > 0 ? AjaxResult.success("Delete the success") : AjaxResult.error("Delete failed");
    }
}
