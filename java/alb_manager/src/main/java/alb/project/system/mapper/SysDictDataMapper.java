package alb.project.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import alb.project.system.domain.SysDictData;

/**
 * A dictionary table The data layer
 *
 */
public interface SysDictDataMapper
{
    /**
     * Query dictionary data by conditional paging
     * 
     * @param dictData Dictionary data information
     * @return Dictionary data set information
     */
    List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * Query dictionary data by dictionary type
     * 
     * @param dictType A dictionary type
     * @return Dictionary data set information
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * Query dictionary data information by dictionary type and dictionary key value
     * 
     * @param dictType A dictionary type
     * @param dictValue The dictionary keys
     * @return The dictionary labels
     */
    String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * According to dictionary dataIDQuery information
     * 
     * @param dictCode The data dictionaryID
     * @return The data dictionary
     */
    SysDictData selectDictDataById(Long dictCode);

    /**
     * Querying dictionary data
     * 
     * @param dictType A dictionary type
     * @return The data dictionary
     */
    int countDictDataByType(String dictType);

    /**
     * Through the dictionaryIDDelete dictionary data information
     * 
     * @param dictCode The data dictionaryID
     * @return The results of
     */
    int deleteDictDataById(Long dictCode);

    /**
     * Delete dictionary data in batches
     * 
     * @param dictCodes Dictionary data to deleteID
     * @return The results of
     */
    int deleteDictDataByIds(Long[] dictCodes);

    /**
     * Added dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return The results of
     */
    int insertDictData(SysDictData dictData);

    /**
     * Modify dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return The results of
     */
    int updateDictData(SysDictData dictData);

    /**
     * Change the dictionary type synchronously
     * 
     * @param oldDictType Old dictionary type
     * @param newDictType New and old dictionary types
     * @return The results of
     */
    int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
