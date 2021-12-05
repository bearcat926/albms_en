package alb.framework.web.controller;

import alb.common.constant.HttpStatus;
import alb.framework.web.domain.AjaxResult;
import alb.framework.web.page.PageDomain;
import alb.framework.web.page.TableDataInfo;
import alb.framework.web.page.TableSupport;
import cn.hutool.core.convert.ConverterRegistry;
import alb.common.utils.DateUtils;
import alb.common.utils.StringUtils;
import alb.common.utils.sql.SqlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * webLayer general data processing
 *
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);
    // Global type converter
    protected final static ConverterRegistry converterRegistry = ConverterRegistry.getInstance();

    public static <T> List<T> removeNull(List<? extends T> oldList) {
        // Temporary collection
        List<T> listTemp = new LinkedList<>();
        for (T t : oldList) {
            // Save elements that are not empty
            if (t != null) {
                listTemp.add(t);
            }
        }
        return listTemp;
    }
    /**
     * A string in the format of the date passed from the foreground,Automatically convert toDatetype
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date Type conversion
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * Set request paging data
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * Respond to request paging data
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("The query is successful");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * Response return result
     * 
     * @param rows Affect the number of rows
     * @return Operating results
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
