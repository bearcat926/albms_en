package alb.project.tool.gen.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alb.common.core.text.Convert;
import alb.project.tool.gen.domain.GenTableColumn;
import alb.project.tool.gen.mapper.GenTableColumnMapper;

/**
 * The business field Service layer implementation
 *
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService 
{
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	/**
     * Example Query the service field list
     * 
     * @param tableId Business Field Number
     * @return Collection of business fields
     */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId)
	{
	    return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
	}
	
    /**
     * New Service Field
     * 
     * @param genTableColumn Business field information
     * @return The results of
     */
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.insertGenTableColumn(genTableColumn);
	}
	
	/**
     * Modifying service Fields
     * 
     * @param genTableColumn Business field information
     * @return The results of
     */
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.updateGenTableColumn(genTableColumn);
	}

	/**
     * Deletes a business field object
     * 
     * @param ids Data to be deletedID
     * @return The results of
     */
	@Override
	public int deleteGenTableColumnByIds(String ids)
	{
		return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}
}