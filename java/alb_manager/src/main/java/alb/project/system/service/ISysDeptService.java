package alb.project.system.service;

import alb.framework.web.domain.TreeSelect;
import alb.project.system.domain.SysDept;
import alb.project.system.domain.vo.ShopExcelVO;
import alb.project.system.domain.vo.SubsidiaryCompanyExcelVO;

import java.util.List;
import java.util.Map;

/**
 * Department Management The service layer
 *
 */
public interface ISysDeptService
{
    /**
     * Example Query department management data
     * 
     * @param dept Department information
     * @return Department information set
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * The tree structure is needed to build the front end
     * 
     * @param depts Department list
     * @return Tree structure list
     */
    List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * The drop-down tree structure is needed to build the front end
     * 
     * @param depts Department list
     * @return List of drop-down tree structures
     */
    List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * According to the characterIDExample Query department tree information
     * 
     * @param roleId roleID
     * @return Select department list
     */
    List<Integer> selectDeptListByRoleId(Long roleId);

    /**
     * According to the departmentIDQuery information
     * 
     * @param deptId departmentID
     * @return Department information
     */
    SysDept selectDeptById(Long deptId);

    /**
     * According to theIDQuery all subdepartments(The normal state)
     * 
     * @param deptId departmentID
     * @return Department number
     */
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * Check whether there are department sub-nodes
     * 
     * @param deptId departmentID
     * @return The results of
     */
    boolean hasChildByDeptId(Long deptId);

    /**
     * Query whether users exist in the department
     * 
     * @param deptId departmentID
     * @return The results of true There are false 不There are
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * Verify that the department name is unique
     * 
     * @param dept Department information
     * @return The results of
     */
    String checkDeptNameUnique(SysDept dept);

    /**
     * Added Saving department information
     * 
     * @param dept Department information
     * @return The results of
     */
    int insertDept(SysDept dept);

    /**
     * Modify Save department information
     * 
     * @param dept Department information
     * @return The results of
     */
    int updateDept(SysDept dept);

    /**
     * Example Delete department management information
     * 
     * @param deptId departmentID
     * @return The results of
     */
    int deleteDeptById(Long deptId);

    /**
     * Query the fatheridTo do thisidThe number of
     * @param deptId
     * @return Integer
     */
    Integer countTotalNumByParentId(Long deptId);

    /**
     * Check head officeid(Query only one)
     * @return Long
     */
    Long selectHeadCompanyId();

    /**
     * Get list of subsidiaries
     * @param dept
     * @return
     */
    List<Map<String, Object>> selectSubsidiaryCompanyList(SysDept dept);

    /**
     * Get store list
     * @param dept
     * @return
     */
    List<Map<String, Object>> selectShopList(SysDept dept);

    Integer countEmployeesNumByDeptId(Long deptId);

    String importSubsidiaryCompany(List<SubsidiaryCompanyExcelVO> list, boolean updateSupport, String operName);

    String importShop(List<ShopExcelVO> list, boolean updateSupport, String operName);

    /**
     * Get a list of all stores
     * @return
     */
    List<Map<String, Object>> getAllShopList();

    /**
     * Query the list of all branches Only the querydeptIdwithdeptName
     * @return
     */
    List<SysDept> getAllSubsidiaryCompanyList();

    /**
     * Query all stores in branches
     * @param deptId
     * @return
     */
    List<SysDept> selectShopListByParentId(Long deptId);

    /**
     * According to theshopIdsQuery stores
     * @param shopIds
     * @return
     */
    List<SysDept> selectDeptByIds(String shopIds);

    /**
     * According to the subsidiaryidQuery store list
     * @param ids
     * @return
     */
    List<SysDept> getShopListByCompanyIds(String ids);

    /**
     * Obtain subsidiary data by month
     * @param month
     * @return
     */
    Integer getTotalCompanyNumByMonth(String month);

    /**
     * Obtain the number of stores by month
     * @param month
     * @return
     */
    Integer getTotalShopNumByMonth(String month);

    /**
     * Number of stores
     * @return
     */
    Integer getTotalShopNum();

    /**
     * Number of subsidiaries
     * @return
     */
    Integer getTotalCompanyNum();

    /**
     * Query the list of all subsidiaries-Dividing data Permissions
     * @return
     */
    List<SysDept> getSubsidiaryCompanyList(SysDept dept);
}
