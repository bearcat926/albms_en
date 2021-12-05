package alb.project.system.service.impl;

import java.util.List;

import alb.project.system.domain.SysDictData;
import alb.project.system.mapper.SysDictDataMapper;
import alb.project.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alb.common.utils.DictUtils;

/**
 * The dictionary Business layer processing
 *
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * Query dictionary data by conditional paging
     * 
     * @param dictData Dictionary data information
     * @return Dictionary data set information
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * Query dictionary data information by dictionary type and dictionary key value
     * 
     * @param dictType A dictionary type
     * @param dictValue The dictionary keys
     * @return The dictionary labels
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * According to dictionary dataIDQuery information
     * 
     * @param dictCode The data dictionaryID
     * @return The data dictionary
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * Delete dictionary data in batches
     * 
     * @param dictCodes Dictionary data to deleteID
     * @return The results of
     */
    @Override
    public int deleteDictDataByIds(Long[] dictCodes)
    {
        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * Added saving dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return The results of
     */
    @Override
    public int insertDictData(SysDictData dictData)
    {
        int row = dictDataMapper.insertDictData(dictData);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * Modify save dictionary data information
     * 
     * @param dictData Dictionary data information
     * @return The results of
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        int row = dictDataMapper.updateDictData(dictData);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }
}
