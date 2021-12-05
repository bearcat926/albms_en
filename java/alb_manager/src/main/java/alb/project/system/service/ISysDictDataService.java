package alb.project.system.service;

import java.util.List;
import alb.project.system.domain.SysDictData;

/**
 * The dictionary The business layer
 *
 */
public interface ISysDictDataService
{
    /**
     * Query dictionary data by conditional paging
     * 
     * @param dictData Dictionary data information
     * @return Dictionary data set information
     */
    List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * Query dictionary data information by dictionary type and dictionary key value
     * 
     * @param dictType A dictionary type
     * @param dictValue The dictionary keys
     * @return The dictionary labels
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * According to dictionary dataIDQuery information
     * 
     * @param dictCode The data dictionaryID
     * @return The data dictionary
     */
    SysDictData selectDictDataById(Long dictCode);

    /**
     * Delete dictionary data in batches
     * 
     * @param dictCodes Dictionary data to deleteID
     * @return The results of
     */
    int deleteDictDataByIds(Long[] dictCodes);

    /**
     * Added saving dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return The results of
     */
    int insertDictData(SysDictData dictData);

    /**
     * Modify save dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return The results of
     */
    int updateDictData(SysDictData dictData);
}
