package alb.framework.web.page;

import alb.common.utils.ServletUtils;

/**
 * Table data processing
 *
 */
public class TableSupport
{
    /**
     * Start index of the current record
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * Number of records per page
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * Row of the sequence
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * Sorting direction "desc" or "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Encapsulating paging objects
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
