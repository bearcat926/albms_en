package alb.project.tool.gen.mapper;

import java.util.List;
import alb.project.tool.gen.domain.GenTable;

/**
 * business The data layer
 *
 */
public interface GenTableMapper
{
    /**
     * Querying a Service List
     * 
     * @param genTable Business information
     * @return Business collection
     */
    List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * Query the data library list
     * 
     * @param genTable Business information
     * @return Collection of database tables
     */
    List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * Query the data library list
     * 
     * @param tableNames The table name group
     * @return Collection of database tables
     */
    List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * Lookup tableIDBusiness information
     * 
     * @param id businessID
     * @return Business information
     */
    GenTable selectGenTableById(Long id);

    /**
     * Querying table name Service information
     * 
     * @param tableName The name of the table
     * @return Business information
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * The new business
     * 
     * @param genTable Business information
     * @return The results of
     */
    int insertGenTable(GenTable genTable);

    /**
     * Modify the business
     * 
     * @param genTable Business information
     * @return The results of
     */
    int updateGenTable(GenTable genTable);

    /**
     * Deleting services in batches
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteGenTableByIds(Long[] ids);
}