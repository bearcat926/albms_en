package alb.framework.web.page;

import java.io.Serializable;
import java.util.List;

/**
 * Table paging data object
 *
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** The total number of records */
    private long total;

    /** The list of data */
    private List<?> rows;

    /** Message status code */
    private int code;

    /** The message content */
    private String msg;

    /**
     * Tabular data object
     */
    public TableDataInfo()
    {
    }

    /**
     * paging
     * 
     * @param list The list of data
     * @param total The total number of records
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}