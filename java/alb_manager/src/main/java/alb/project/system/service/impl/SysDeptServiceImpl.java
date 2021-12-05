package alb.project.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import alb.common.constant.UserConstants;
import alb.common.exception.CustomException;
import alb.framework.aspectj.lang.annotation.DataScope;
import alb.framework.web.domain.TreeSelect;
import alb.project.system.domain.SysDept;
import alb.project.system.domain.vo.ShopExcelVO;
import alb.project.system.domain.vo.SubsidiaryCompanyExcelVO;
import alb.project.system.mapper.SysDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alb.common.utils.StringUtils;
import alb.project.system.service.ISysDeptService;

/**
 * Department Management The service implementation
 *
 */
@Service
@Slf4j
public class SysDeptServiceImpl implements ISysDeptService {
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * Example Query department management data
     *
     * @param dept Department information
     * @return Department information set
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * The tree structure is needed to build the front end
     *
     * @param depts Department list
     * @return Tree structure list
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDept dept = iterator.next();
            // If it's a top-level node, All children of the parent node are traversed
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * The drop-down tree structure is needed to build the front end
     *
     * @param depts Department list
     * @return List of drop-down tree structures
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * According to the characterIDExample Query department tree information
     *
     * @param roleId roleID
     * @return Select department list
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return deptMapper.selectDeptListByRoleId(roleId);
    }

    /**
     * According to the departmentIDQuery information
     *
     * @param deptId departmentID
     * @return Department information
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * According to theIDQuery all subdepartments(The normal state)
     *
     * @param deptId departmentID
     * @return Department number
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * Whether there are child nodes
     *
     * @param deptId departmentID
     * @return The results of
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * Query whether users exist in the department
     *
     * @param deptId departmentID
     * @return The results of true There are false 不There are
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * Verify that the department name is unique
     *
     * @param dept Department information
     * @return The results of
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId(),dept.getDeptCode());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Added Saving department information
     *
     * @param dept Department information
     * @return The results of
     */
    @Override
    public int insertDept(SysDept dept) {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // If the parent node is not in the normal state,You cannot add child nodes
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new CustomException("Department of stopping,Not allowed to add");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * Modify Save department information
     *
     * @param dept Department information
     * @return The results of
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
            // If the department is enabled,Enable all superior departments of the department
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * Example Change the status of the parent department
     *
     * @param dept The current department
     */
    private void updateParentDeptStatus(SysDept dept) {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * Modify child element relationships
     *
     * @param deptId       The modified departmentID
     * @param newAncestors The new parentIDA collection of
     * @param oldAncestors The old fatherIDA collection of
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * Example Delete department management information
     *
     * @param deptId departmentID
     * @return The results of
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * Recursive list
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // Get a list of child nodes
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                // Check whether there are child nodes
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext()) {
                    SysDept n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * Get a list of child nodes
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * Check whether there are child nodes
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0;
    }


    @Override
    public Integer countTotalNumByParentId(Long deptId) {
        return deptMapper.countTotalNumByParentId(deptId);
    }

    @Override
    public Long selectHeadCompanyId() {
        return deptMapper.selectHeadCompanyId();
    }

    /**
     *  Query the list of all subsidiaries   Dividing data Permissions
     * @param dept
     * @return
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Map<String, Object>> selectSubsidiaryCompanyList(SysDept dept) {
        return deptMapper.selectSubsidiaryCompanyList(dept);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<Map<String, Object>> selectShopList(SysDept dept) {
        return deptMapper.selectShopList(dept);
    }

    @Override
    public Integer countEmployeesNumByDeptId(Long deptId) {
        return deptMapper.countEmployeesNumByDeptId(deptId);
    }

    /**
     * Import subsidiary
     * @param list
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    @Override
    public String importSubsidiaryCompany(List<SubsidiaryCompanyExcelVO> list, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(list) || list.size() == 0)
        {
            throw new CustomException("The imported subsidiary data cannot be empty!");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SubsidiaryCompanyExcelVO vo : list)
        {
            try
            {
                SysDept info = deptMapper.selectDeptById(100L);
                // If the parent node is not in the normal state,You cannot add child nodes
                if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
                    throw new CustomException(info.getDeptName() + " disable,Not allowed to add");
                }
                // Verify that the department exists
                SysDept dept = deptMapper.checkDeptCodeUnique(vo.getDeptCode());
                if (StringUtils.isNull(dept))
                {
                    SysDept insertDept = new SysDept();
                    insertDept.setDeptCode(vo.getDeptCode());
                    insertDept.setParentId(info.getDeptId());
                    insertDept.setAncestors(info.getAncestors() + "," + info.getDeptId());
                    insertDept.setDeptName(vo.getDeptName());
                    insertDept.setOrderNum(String.valueOf(vo.getOrderNum()));
                    insertDept.setLeader(vo.getLeader());
                    insertDept.setPhone(vo.getPhone());
                    insertDept.setEmail(vo.getEmail());
                    insertDept.setProvince(vo.getProvince());
                    insertDept.setCity(vo.getCity());
                    insertDept.setDistrict(vo.getDistrict());
                    insertDept.setAddress(vo.getAddress());
                    insertDept.setDeptType(0);
                    insertDept.setType(1);
                    insertDept.setStatus("0");
                    insertDept.setDelFlag("0");
                    insertDept.setCreateBy(operName);
                    deptMapper.insertDept(insertDept);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、subsidiary ").append(vo.getDeptName()).append(" Import success");
                }
                else if (isUpdateSupport)
                {
                    dept.setParentId(info.getDeptId());
                    dept.setAncestors(info.getAncestors() + "," + info.getDeptId());
                    dept.setDeptCode(vo.getDeptCode());
                    dept.setDeptName(vo.getDeptName());
                    dept.setOrderNum(String.valueOf(vo.getOrderNum()));
                    dept.setLeader(vo.getLeader());
                    dept.setPhone(vo.getPhone());
                    dept.setEmail(vo.getEmail());
                    dept.setProvince(vo.getProvince());
                    dept.setCity(vo.getCity());
                    dept.setDistrict(vo.getDistrict());
                    dept.setAddress(vo.getAddress());
                    dept.setUpdateBy(operName);
                    deptMapper.updateDept(dept);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、subsidiary ").append(vo.getDeptName()).append(" The update is successful");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、subsidiary ").append(vo.getDeptCode()).append(" existing");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、subsidiary " + vo.getDeptName() + " Import failure:";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "I'm sorry,Import failure!A total of " + failureNum + " The data format is incorrect,Error is as follows:");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "Congratulations to you,All data has been imported successfully!A total of " + successNum + " article,The following data:");
        }
        return successMsg.toString();
    }


    /**
     * The import of stores
     * @param list
     * @param isUpdateSupport
     * @param operName
     * @return
     */
    @Override
    public String importShop(List<ShopExcelVO> list, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(list) || list.size() == 0)
        {
            throw new CustomException("Import store data cannot be empty!");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (ShopExcelVO vo : list)
        {
            try
            {
                // Query subsidiary information according to the code of the subsidiary of the store
                SysDept info = deptMapper.selectDeptByCode(vo.getParentDeptCode());

                // If the parent node is not in the normal state,You cannot add child nodes
                if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
                    throw new CustomException(info.getDeptName() + "Subsidiary discontinuation,Not allowed to add");
                }
                // Verify the existence of the store
//                SysDept dept = deptMapper.checkDeptNameUnique(vo.getDeptName(),info.getDeptId(),vo.getDeptCode());
                SysDept dept = deptMapper.checkDeptCodeUnique(vo.getDeptCode());
                if (StringUtils.isNull(dept))
                {
                    SysDept insertDept = new SysDept();
                    insertDept.setDeptCode(vo.getDeptCode());
                    insertDept.setParentId(info.getDeptId());
                    insertDept.setAncestors(info.getAncestors() + "," + info.getDeptId());
                    insertDept.setDeptName(vo.getDeptName());
                    insertDept.setOrderNum(String.valueOf(vo.getOrderNum()));
                    insertDept.setDeptType(vo.getDeptType());
                    insertDept.setLeader(vo.getLeader());
                    insertDept.setPhone(vo.getPhone());
                    insertDept.setEmail(vo.getEmail());
                    insertDept.setProvince(vo.getProvince());
                    insertDept.setCity(vo.getCity());
                    insertDept.setDistrict(vo.getDistrict());
                    insertDept.setAddress(vo.getAddress());
                    insertDept.setType(2);
                    insertDept.setStatus("0");
                    insertDept.setDelFlag("0");
                    insertDept.setCreateBy(operName);
                    deptMapper.insertDept(insertDept);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、stores ").append(vo.getDeptName()).append(" Import success");
                }
                else if (isUpdateSupport)
                {
                    dept.setDeptCode(vo.getDeptCode());
                    dept.setParentId(info.getDeptId());
                    dept.setAncestors(info.getAncestors() + "," + info.getDeptId());
                    dept.setDeptName(vo.getDeptName());
                    dept.setOrderNum(String.valueOf(vo.getOrderNum()));
                    dept.setDeptType(vo.getDeptType());
                    dept.setLeader(vo.getLeader());
                    dept.setPhone(vo.getPhone());
                    dept.setEmail(vo.getEmail());
                    dept.setProvince(vo.getProvince());
                    dept.setCity(vo.getCity());
                    dept.setDistrict(vo.getDistrict());
                    dept.setAddress(vo.getAddress());
                    dept.setUpdateBy(operName);
                    deptMapper.updateDept(dept);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、stores ").append(vo.getDeptName()).append(" The update is successful");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、stores ").append(vo.getDeptCode()).append(" existing");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、stores " + vo.getDeptName() + " Import failure:";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "I'm sorry,Import failure!A total of " + failureNum + " The data format is incorrect,Error is as follows:");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "Congratulations to you,All data has been imported successfully!A total of " + successNum + " article,The following data:");
        }
        return successMsg.toString();
    }

    /**
     * Get a list of all stores
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllShopList() {
        return deptMapper.getAllShopList();
    }

    /**
     * Query the list of all branches Only the querydeptIdwithdeptName
     * @return
     */
    @Override
    public List<SysDept> getAllSubsidiaryCompanyList() {
        return deptMapper.getAllSubsidiaryCompanyList();
    }

    /**
     * Query all stores in branches
     * @param deptId
     * @return
     */
    @Override
    public List<SysDept> selectShopListByParentId(Long deptId) {
        return deptMapper.selectShopListByParentId(deptId);
    }

    /**
     * According to theshopIdsQuery stores
     * @param shopIds
     * @return
     */
    @Override
    public List<SysDept> selectDeptByIds(String shopIds) {
        return deptMapper.selectDeptByIds(shopIds);
    }

    /**
     * According to the subsidiaryidQuery store list
     * @param ids
     * @return
     */
    @Override
    public List<SysDept> getShopListByCompanyIds(String ids) {
        return deptMapper.getShopListByCompanyIds(ids);
    }

    /**
     * Obtain subsidiary data by month
     * @param month
     * @return
     */
    @Override
    public Integer getTotalCompanyNumByMonth(String month) {
        return deptMapper.getTotalCompanyNumByMonth(month);
    }

    /**
     * Obtain the number of stores by month
     * @param month
     * @return
     */
    @Override
    public Integer getTotalShopNumByMonth(String month) {
        return deptMapper.getTotalShopNumByMonth(month);
    }

    /**
     * Number of stores
     * @return
     */
    @Override
    public Integer getTotalShopNum() {
        return deptMapper.getTotalShopNum();
    }

    /**
     * Number of subsidiaries
     * @return
     */
    @Override
    public Integer getTotalCompanyNum() {
        return deptMapper.getTotalCompanyNum();
    }

    /**
     * Query the list of all subsidiaries-Dividing data Permissions
     * @return
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> getSubsidiaryCompanyList(SysDept dept) {
        return deptMapper.getSubsidiaryCompanyList(dept);
    }
}
