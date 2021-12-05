package alb.project.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import alb.project.system.domain.SysDictType;

/**
 * A dictionary table The data layer
 *
 */
@Mapper
public interface SysDictTypeMapper
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
     * Through the dictionaryIDDelete dictionary information
     * 
     * @param dictId The dictionaryID
     * @return The results of
     */
    int deleteDictTypeById(Long dictId);

    /**
     * Delete dictionary types in batches
     * 
     * @param dictIds Dictionaries that need to be deletedID
     * @return The results of
     */
    int deleteDictTypeByIds(Long[] dictIds);

    /**
     * Added dictionary type information
     * 
     * @param dictType Dictionary type information
     * @return The results of
     */
    int insertDictType(SysDictType dictType);

    /**
     * Modify the dictionary type
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
    SysDictType checkDictTypeUnique(String dictType);
}
