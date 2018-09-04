package com.MyUtils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Created by yanglanqing on 2017/9/5.
 */
public class CommonExcelUtil {

    public static void initHSSFCellStyle(HSSFCellStyle style) {
        style.setTopBorderColor(HSSFColor.BLUE.index);
        style.setLeftBorderColor(HSSFColor.BLUE.index);
        style.setRightBorderColor(HSSFColor.BLUE.index);
        style.setBottomBorderColor(HSSFColor.BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
    }

    public static void initCommonStyle(HSSFCellStyle style, HSSFFont font) {
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //设置垂直居中
        font.setFontName("微软雅黑");
        font.setColor(HSSFColor.BLACK.index);
        style.setFont(font);
    }

    public static void initHeadStyle(HSSFCellStyle style, HSSFFont font) {
        //HeadStyle
        style.setFillForegroundColor(HSSFColor.BLUE.index); //设置背景颜色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //设置垂直居中
        //设置font属性
        font.setFontName("微软雅黑");
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
    }

    /**
     * 创建Double类型 excel表格的列
     * @param row
     * @param cellNum
     * @param value
     */
    public static void createCostomCell(HSSFRow row, int cellNum, Object value,
                                        HSSFCellStyle cellStyle) {
        if (row != null) {
            HSSFCell cell = row.createCell(cellNum);
            cell.setCellStyle(cellStyle);
            if (value.getClass().equals(Double.class)) {
                cell.setCellValue((Double) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            } else if (value.getClass().equals(Float.class)) {
                cell.setCellValue((Float) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            } else if (value.getClass().equals(Integer.class)) {
                cell.setCellValue((Integer) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
            } else if (value.getClass().equals(Long.class) || value.getClass().equals(long.class)) {
                cell.setCellValue((Long) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
            } else {
                cell.setCellValue(new HSSFRichTextString(getNotNullStr(value)));
            }
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 创建百分比类型 excel表格的列
     * @param row
     * @param cellNum
     * @param value
     */
    public static void createPercentcell(HSSFRow row, int cellNum, Object value,
                                         HSSFCellStyle cellStyle) {
        if (row != null) {
            HSSFCell cell = row.createCell(cellNum);
            cell.setCellStyle(cellStyle);
            if (value.getClass().equals(Double.class)) {
                cell.setCellValue((Double) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
            } else if (value.getClass().equals(Float.class)) {
                cell.setCellValue((Float) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
            } else if (value.getClass().equals(Integer.class)) {
                cell.setCellValue((Integer) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("##0%"));
            } else if (value.getClass().equals(Long.class) || value.getClass().equals(long.class)) {
                cell.setCellValue((Long) value);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("##0%"));
            } else {
                cell.setCellValue(new HSSFRichTextString(getNotNullStr(value)));
            }
            cell.setCellStyle(cellStyle);
        }
    }

    public static String getNotNullStr(Object obj) {
        return obj != null?obj.toString():"";
    }
}
