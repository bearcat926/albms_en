package alb.project.system.mapper;

import alb.project.system.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Department Management The data layer
 *
 */
public interface SysDeptMapper {
    /**
     * Example Query department management data
     *
     * @param dept Department information
     * @return Department information set
     */
    List<SysDept> selectDeptList(SysDept dept);

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
     * According to theIDQuery all subdepartments
     *
     * @param deptId departmentID
     * @return Department list
     */
    List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * According to theIDQuery all subdepartments(The normal state)
     *
     * @param deptId departmentID
     * @return Department number
     */
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * Whether there are child nodes
     *
     * @param deptId departmentID
     * @return The results of
     */
    int hasChildByDeptId(Long deptId);

    /**
     * Query whether users exist in the department
     *
     * @param deptId departmentID
     * @return The results of
     */
    int checkDeptExistUser(Long deptId);

    /**
     * Verify that the department name is unique
     *
     * @param deptName Department name
     * @param parentId The parent departmentID
     * @return The results of
     */
    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId, @Param("deptCode") String deptCode);

    /**
     * Add Department Information
     *
     * @param dept Department information
     * @return The results of
     */
    int insertDept(SysDept dept);

    /**
     * Modifying Department Information
     *
     * @param dept Department information
     * @return The results of
     */
    int updateDept(SysDept dept);

    /**
     * Example Change the status of the parent department of the department
     *
     * @param dept department
     */
    void updateDeptStatus(SysDept dept);

    /**
     * Modify child element relationships
     *
     * @param depts Child elements
     * @return The results of
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * Example Delete department management information
     *
     * @param deptId departmentID
     * @return The results of
     */
    int deleteDeptById(Long deptId);

    /**
     * Count all parentsidTo do thisidThe number of
     *
     * @param deptId
     */
    Integer countTotalNumByParentId(Long deptId);

    /**
     * Check head officeid(Query only one)
     *
     * @return Long
     */
    Long selectHeadCompanyId();

    /**
     * Querying the List of subsidiaries
     *
     * @param dept
     * @return
     */
    List<Map<String, Object>> selectSubsidiaryCompanyList(SysDept dept);

    /**
     * Get store list
     *
     * @param dept
     * @return
     */
    List<Map<String, Object>> selectShopList(SysDept dept);

    Integer countEmployeesNumByDeptId(Long deptId);

    SysDept selectDeptByCode(@Param("deptCode") String parentDeptCode);

    SysDept checkDeptCodeUnique(@Param("deptCode") String deptCode);

    /**
     * Get a list of all stores
     *
     * @return
     */
    List<Map<String, Object>> getAllShopList();

    /**
     * Query the list of all branches Only the querydeptIdwithdeptName
     *
     * @return
     */
    List<SysDept> getAllSubsidiaryCompanyList();

    /**
     * Query all stores in branches
     *
     * @param deptId
     * @return
     */
    List<SysDept> selectShopListByParentId(@Param("deptId") Long deptId);

    /**
     * According to theshopIdsQuery stores
     *
     * @param shopIds
     * @return
     */
    List<SysDept> selectDeptByIds(@Param("shopIds") String shopIds);

    /**
     * According to the subsidiaryidQuery store list
     *
     * @param ids
     * @return
     */
    List<SysDept> getShopListByCompanyIds(@Param("ids") String ids);

    /**
     * Obtain subsidiary data by month
     *
     * @param month
     * @return
     */
    Integer getTotalCompanyNumByMonth(@Param("month") String month);

    /**
     * Obtain the number of stores by month
     *
     * @param month
     * @return
     */
    Integer getTotalShopNumByMonth(@Param("month") String month);

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
