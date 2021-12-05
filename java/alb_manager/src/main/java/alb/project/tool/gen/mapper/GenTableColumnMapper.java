package alb.project.tool.gen.mapper;

import java.util.List;
import alb.project.tool.gen.domain.GenTableColumn;

/**
 * The business field The data layer
 *
 */
public interface GenTableColumnMapper
{
    /**
     * Query column information by table name
     * 
     * @param tableName The name of the table
     * @return Column information
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);
    
    /**
     * Example Query the service field list
     * 
     * @param tableId Business Field Number
     * @return Collection of business fields
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * New Service Field
     * 
     * @param genTableColumn Business field information
     * @return The results of
     */
    int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * Modifying service Fields
     * 
     * @param genTableColumn Business field information
     * @return The results of
     */
    int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * Delete service fields in batches
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteGenTableColumnByIds(Long[] ids);
}