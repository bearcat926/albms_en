package alb.project.tool.gen.service;

import java.util.List;
import alb.project.tool.gen.domain.GenTableColumn;

/**
 * The business field The service layer
 *
 */
public interface IGenTableColumnService
{
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
     * Example Delete service fields
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteGenTableColumnByIds(String ids);
}
