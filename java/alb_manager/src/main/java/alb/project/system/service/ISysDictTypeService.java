package alb.project.system.service;

import java.util.List;
import alb.project.system.domain.SysDictData;
import alb.project.system.domain.SysDictType;

/**
 * The dictionary The business layer
 *
 */
public interface ISysDictTypeService
{
    /**
     * Query dictionary types based on conditional paging
     * 
     * @param dictType Dictionary type information
     * @return Dictionary type collection information
     */
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * According to all dictionary types
     * 
     * @return Dictionary type collection information
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * Query dictionary data by dictionary type
     * 
     * @param dictType A dictionary type
     * @return Dictionary data set information
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * According to the dictionary typeIDQuery information
     * 
     * @param dictId A dictionary typeID
     * @return A dictionary type
     */
    SysDictType selectDictTypeById(Long dictId);

    /**
     * Query information by dictionary type
     * 
     * @param dictType A dictionary type
     * @return A dictionary type
     */
    SysDictType selectDictTypeByType(String dictType);

    /**
     * Delete dictionaries in batches
     * 
     * @param dictIds Dictionaries that need to be deletedID
     * @return The results of
     */
    int deleteDictTypeByIds(Long[] dictIds);

    /**
     * Clearing cached data
     */
    void clearCache();

    /**
     * Added saving dictionary type information
     * 
     * @param dictType Dictionary type information
     * @return The results of
     */
    int insertDictType(SysDictType dictType);

    /**
     * Modify save dictionary type information
     * 
     * @param dictType Dictionary type information
     * @return The results of
     */
    int updateDictType(SysDictType dictType);

    /**
     * Verify that the dictionary type name is unique
     * 
     * @param dictType A dictionary type
     * @return The results of
     */
    String checkDictTypeUnique(SysDictType dictType);
}
