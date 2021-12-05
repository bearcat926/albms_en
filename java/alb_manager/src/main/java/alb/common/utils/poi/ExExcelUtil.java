package alb.common.utils.poi;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.write.ExcelBuilderImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Used to fillExcelThe template
 *
 * @author HaoHao
 * Created on 2021/1/9.
 */
public class ExExcelUtil {

    /** Easy Excel */
    /**
     * To obtainworkbook
     * becauseEasyExcelThe reason for the library design
     * Can only be retrieved using reflectionworkbook
     *
     * @param writer
     * @return
     */
    public static Workbook getWorkbook(ExcelWriter writer) {
        Workbook workbook = null;
        try {
            Class<?> clazz1 = Class.forName("com.alibaba.excel.ExcelWriter");
            Field[] fs = clazz1.getDeclaredFields();
            for (Field field : fs) {
                // To set property reachable,Otherwise it will throwIllegalAccessExceptionabnormal
                field.setAccessible(true);
                if ("excelBuilder".equals(field.getName())) {
                    ExcelBuilderImpl excelBuilder = (ExcelBuilderImpl) field.get(writer);
                    Class<?> clazz2 = Class.forName("com.alibaba.excel.write.ExcelBuilderImpl");
                    Field[] fs2 = clazz2.getDeclaredFields();
                    for (Field field2 : fs2) {
                        field2.setAccessible(true);
                        if ("context".equals(field2.getName())) {
                            WriteContext context = (WriteContext) field2.get(excelBuilder);
                            workbook = context.getWorkbook();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /* =====  mergesheet  ===== */

    /**
     * Merge the twoExcel
     *
     * @param targetWorkBook
     * @param sourceWorkBook
     * @return
     */
    public static Workbook mergeExcel(Workbook targetWorkBook, Workbook sourceWorkBook) {
        // Create the targetsheet
        org.apache.poi.ss.usermodel.Sheet targetSheet = targetWorkBook.getSheetAt(0);
        org.apache.poi.ss.usermodel.Sheet sourceSheet = sourceWorkBook.getSheetAt(0);

        moveSourceSheetIntoTargetSheet(targetWorkBook, sourceSheet, targetSheet, 0);

        return targetWorkBook;
    }

    /**
     * sheetpages
     *
     * @param targetWorkBook    The targetworkbook,This object is primarily used to create cell formats
     * @param sourceSheet       The sourcesheet
     * @param targetSheet       The targetsheet
     * @param removeTitleLength The sourcesheetPage merge to targetsheetThe length of the table header to be removed from,If you don't get rid of the pass0
     */
    public static void moveSourceSheetIntoTargetSheet(Workbook targetWorkBook,
                                                      org.apache.poi.ss.usermodel.Sheet sourceSheet,
                                                      org.apache.poi.ss.usermodel.Sheet targetSheet,
                                                      int removeTitleLength) {

        if (sourceSheet == null) {
            return;
        }

        // Access to targetsheetThe next row from the last row
        int targetRowNums = targetSheet.getLastRowNum();
        int physicalNumberOfRows = targetSheet.getPhysicalNumberOfRows();
        targetRowNums = physicalNumberOfRows == 0 ? 0 : targetRowNums + 1;

        // mobile The sourcesheetIn the page Merge cell areas To the targetsheetIn the page
        moveSourceSheetAllMergedRegionToTargetSheet(sourceSheet, targetSheet, targetRowNums, removeTitleLength);

        int sourceRowNums = sourceSheet.getLastRowNum();
        for (int i = removeTitleLength; i <= sourceRowNums; i++) {

            Row targetRow = targetSheet.createRow(targetRowNums++);
            Row sourceRow = sourceSheet.getRow(i);

            // Copy the line
            copySourceRowToTargetRow(targetWorkBook, sourceRow, targetRow);
        }
    }

    /**
     * mergesheetIn the page,To deal with the sourcesheetIn the page可能存在的 merge单元格部分;
     * When the sourcesheetPage may remove table headers when merging cells,So you also need to remove the merged cell part of the table header
     *
     * @param sourceSheet       The sourcesheet
     * @param targetSheet       The targetsheet
     * @param targetRowNums     The targetsheetThe last line of(The location of the source merge cell,需要变化到The target单元格区域,A position horn is required)
     * @param removeTitleLength The length of the header to be removed
     */
    private static void moveSourceSheetAllMergedRegionToTargetSheet(org.apache.poi.ss.usermodel.Sheet sourceSheet,
                                                                    org.apache.poi.ss.usermodel.Sheet targetSheet,
                                                                    int targetRowNums,
                                                                    int removeTitleLength) {

        int numMergedRegions = sourceSheet.getNumMergedRegions();
        for (int i = 0; i < numMergedRegions; i++) {

            CellRangeAddress mergedRegion = sourceSheet.getMergedRegion(i);

            int firstRow = mergedRegion.getFirstRow();

            // Remove the header Cell merge
            if (firstRow < removeTitleLength) {
                continue;
            }

            int lastRow = mergedRegion.getLastRow();
            int firstColumn = mergedRegion.getFirstColumn();
            int lastColumn = mergedRegion.getLastColumn();

            // The rows of the merged cells need to be moved down to follow the number of rows of the current cell
            firstRow = firstRow + targetRowNums;
            lastRow = lastRow + targetRowNums;

            CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
            targetSheet.addMergedRegion(cellRangeAddress);
        }

    }

    /**
     * Move cell
     *
     * @param targetWorkBook The targetworkbook,Used to create a cell style in this method
     * @param targetCell     Target cell
     * @param sourceCell     The source cell
     */
    private static void copySourceCellToTargetCell(Workbook targetWorkBook, Cell targetCell, Cell sourceCell) {

        if (sourceCell == null) {
            return;
        }

        // Format the source cell Assign values to the In the target cell
        CellStyle sourceCellStyle = sourceCell.getCellStyle();
        /*
            This is new because it's newworkbookobject,Only new CellStyleobject,thenclone,To assign a value;
            Direct assignment The sourceCellStyleobject 会报不是同The source异常
        */
        CellStyle targetCellStyle = targetWorkBook.createCellStyle();
        targetCellStyle.cloneStyleFrom(sourceCellStyle);
        targetCell.setCellStyle(targetCellStyle);

        CellType cellTypeEnum = sourceCell.getCellTypeEnum();
        switch (cellTypeEnum) {
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(sourceCell)) {
                    // Value in date format
                    targetCell.setCellValue(sourceCell.getDateCellValue());
                } else {
                    targetCell.setCellValue(sourceCell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                // ***In the case of formula, the value of the cell is obtained
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case BLANK:
                break;
            case ERROR:
                targetCell.setCellValue(sourceCell.getErrorCellValue());
                break;
            case _NONE:
                break;
            default:

        }
    }

    /**
     * Copy the source row to the target row
     *
     * @param targetWorkBook The targetworkbook,Mainly used to create cell styles
     * @param sourceRow      The source line
     * @param targetRow      The target row
     */
    private static void copySourceRowToTargetRow(Workbook targetWorkBook, Row sourceRow, Row targetRow) {

        if (sourceRow == null) {
            return;
        }

        // Line height
        targetRow.setHeight(sourceRow.getHeight());

        int sourceCellNums = sourceRow.getLastCellNum();
        for (int i = 0; i < sourceCellNums; i++) {

            Cell targetCell = targetRow.createCell(i);
            Cell sourceCell = sourceRow.getCell(i);

            // Copy cell
            copySourceCellToTargetCell(targetWorkBook, targetCell, sourceCell);
        }
    }

    public static XSSFWorkbook getNewWorkbook(Workbook workbook) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return new XSSFWorkbook(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
