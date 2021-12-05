package alb.project.vacation.service.impl;

import alb.project.system.domain.SysRole;
import cn.hutool.core.util.IdUtil;
import alb.project.vacation.domain.HolidayApproval;
import alb.project.vacation.mapper.HolidayApprovalMapper;
import alb.project.vacation.service.IHolidayApprovalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Leave approval form(HolidayApproval)Table service implementation class
 */
@Service("holidayApprovalService")
public class HolidayApprovalServiceImpl implements IHolidayApprovalService {

    @Resource
    private HolidayApprovalMapper holidayApprovalMapper;

    /**
     * Check whether the next node exists by entity
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    @Override
    public boolean hasNextApproved(HolidayApproval holidayApproval){
        return this.holidayApprovalMapper.hasNext(holidayApproval) > 0L;
    }

    /**
     * Example Query a single piece of data
     *
     * @param holidayApprovalId Instance objects
     * @return Instance objects
     */
    @Override
    public HolidayApproval queryOne(Long holidayApprovalId) {
        return this.holidayApprovalMapper.queryOne(holidayApprovalId);
    }

    /**
     * After detection insertion,Whether the holiday type has a ring
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    @Override
    public boolean hasRing(HolidayApproval holidayApproval) {
        HolidayApproval params = HolidayApproval.builder()
                .holidayTypeId(holidayApproval.getHolidayTypeId())
                .roleId(holidayApproval.getRoleId())
                .build();
        List<HolidayApproval> list = this.holidayApprovalMapper.queryAll(params);
        Set<Long> set = new HashSet<>();
        // Put into the current node
        set.add(holidayApproval.getRoleId());
        set.add(holidayApproval.getApprovedRoleId());
        // Node before detection
        for (HolidayApproval approval : list) {
            if (set.contains(approval.getApprovedRoleId())) {
                return true;
            }
            set.add(approval.getApprovedRoleId());
        }
        return false;
    }

    /**
     * Querying multiple pieces of Data
     *
     * @param holidayApproval
     * @return
     */
    @Override
    public List<HolidayApproval> queryAll(HolidayApproval holidayApproval) {
        return this.holidayApprovalMapper.queryAll(holidayApproval);
    }

    /**
     * Querying the Role List
     *
     * @return The object list
     */
    @Override
    public List<SysRole> selectRoleList(){
        return this.holidayApprovalMapper.selectRoleList();
    }


    /**
     * The new data
     *
     * @param holidayApproval Instance objects
     * @return Instance objects
     */
    @Override
    public int insert(HolidayApproval holidayApproval) {
        // Snowflake algorithm generates unique call log numbers Individual services The data centeridAnd terminalidAll fill in1
        holidayApproval.setHolidayApprovalId(IdUtil.getSnowflake(1, 1).nextId());

        List<HolidayApproval> list = this.holidayApprovalMapper.queryAll(holidayApproval);
        holidayApproval.setCurrentApprovedIndex(list.isEmpty() ?
                1 : (list.get(list.size() - 1).getCurrentApprovedIndex() + 1));
        holidayApproval.setNextApprovalId(0L);

        // The non-head nodes also need to modify the front nodes
        if (holidayApproval.getCurrentApprovedIndex() != 1) {
            HolidayApproval pre = list.get(list.size() - 1);
            pre.setNextApprovalId(holidayApproval.getApprovedRoleId());
            this.holidayApprovalMapper.update(pre);
        }
        return this.holidayApprovalMapper.insert(holidayApproval);
    }

    /**
     * Delete data by primary key
     *
     * @param holidayApprovalId A primary key
     * @return The success of
     */
    @Override
    public int deleteById(Long holidayApprovalId) {
        HolidayApproval holidayApproval = this.holidayApprovalMapper.queryOne(holidayApprovalId);

        // There are subsequent nodes
        if (holidayApproval.getNextApprovalId() != 0) {
            HolidayApproval params = HolidayApproval.builder()
                    .holidayTypeId(holidayApproval.getHolidayTypeId())
                    .roleId(holidayApproval.getRoleId())
                    .build();
            List<HolidayApproval> list = this.holidayApprovalMapper.queryAll(params);

            // Non-head nodes also need to process front nodes
            if (holidayApproval.getCurrentApprovedIndex() != 1) {
                HolidayApproval preParams = HolidayApproval.builder()
                        .holidayTypeId(holidayApproval.getHolidayTypeId())
                        .roleId(holidayApproval.getRoleId())
                        .currentApprovedIndex(holidayApproval.getCurrentApprovedIndex() - 1)
                        .build();
                HolidayApproval pre = this.holidayApprovalMapper.queryAll(params).get(0);
                pre.setNextApprovalId(holidayApproval.getNextApprovalId());
                this.holidayApprovalMapper.update(pre);
            }

            // Processing subsequent nodes
            for (int i = holidayApproval.getCurrentApprovedIndex(); i < list.size(); i++){
                HolidayApproval next = list.get(i);
                next.setCurrentApprovedIndex(next.getCurrentApprovedIndex() - 1);
                this.holidayApprovalMapper.update(next);
            }
        }

        // No subsequent node,But you still need to deal with the front nodes
        if (holidayApproval.getCurrentApprovedIndex() != 1) {
            HolidayApproval preParams = HolidayApproval.builder()
                    .holidayTypeId(holidayApproval.getHolidayTypeId())
                    .roleId(holidayApproval.getRoleId())
                    .currentApprovedIndex(holidayApproval.getCurrentApprovedIndex() - 1)
                    .build();
            HolidayApproval pre = this.holidayApprovalMapper.queryAll(preParams).get(0);
            pre.setNextApprovalId(0L);
            this.holidayApprovalMapper.update(pre);
        }

        // Head node,No subsequent nodes are directly deleted
        return this.holidayApprovalMapper.deleteById(holidayApprovalId);
    }
}
