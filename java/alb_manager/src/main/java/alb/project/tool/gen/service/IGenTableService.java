package alb.project.tool.gen.service;

import java.util.List;
import java.util.Map;
import alb.project.tool.gen.domain.GenTable;

/**
 * business The service layer
 *
 */
public interface IGenTableService
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
     * Querying Service Information
     * 
     * @param id businessID
     * @return Business information
     */
    GenTable selectGenTableById(Long id);

    /**
     * Modify the business
     * 
     * @param genTable Business information
     * @return The results of
     */
    void updateGenTable(GenTable genTable);

    /**
     * Deleting Service Information
     * 
     * @param tableIds Table data to be deletedID
     * @return The results of
     */
    void deleteGenTableByIds(Long[] tableIds);

    /**
     * Import table structure
     * 
     * @param tableList Import table list
     */
    void importGenTable(List<GenTable> tableList);

    /**
     * Preview code
     * 
     * @param tableId Table number
     * @return Preview data list
     */
    Map<String, String> previewCode(Long tableId);

    /**
     * The generated code
     * 
     * @param tableName The name of the table
     * @return data
     */
    byte[] generatorCode(String tableName);

    /**
     * Bulk generation code
     * 
     * @param tableNames Table array
     * @return data
     */
    byte[] generatorCode(String[] tableNames);

    /**
     * Modify save parameter verification
     * 
     * @param genTable Business information
     */
    void validateEdit(GenTable genTable);
}
