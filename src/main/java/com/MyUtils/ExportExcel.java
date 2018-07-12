package com.MyUtils;

import org.apache.poi.hssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by panghaichen on 2018-07-12 09:56
 **/
public class ExportExcel<T> {
    public static final DecimalFormat df = new DecimalFormat("###,###.##");

    public ExportExcel() {
    }

    //导出方法1：导出类的全部字段数据（默认excel格式）
    //title：sheet名；headers：表头一列；dataset：数据；pattern：用于定义日期格式；
    public void exportExcel(String title, List<T> dataset, OutputStream out, String pattern) {
        if (dataset != null) {
            Class clazz = dataset.get(0).getClass();
            Field[] fields1 = clazz.getDeclaredFields();
            String[] headers = new String[fields1.length];
            int count = 0;
            Field[] arr$ = fields1;
            int len$ = fields1.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Field field = arr$[i$];
                headers[count] = field.getName().toString();
                ++count;
            }

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(15);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor((short)40);
            style.setFillPattern((short)1);
            style.setBorderBottom((short)1);
            style.setBorderLeft((short)1);
            style.setBorderRight((short)1);
            style.setBorderTop((short)1);
            style.setAlignment((short)2);
            HSSFFont font = workbook.createFont();
            font.setColor((short)20);
            font.setFontHeightInPoints((short)12);
            font.setBoldweight((short)700);
            style.setFont(font);
            HSSFCellStyle style2 = workbook.createCellStyle();
            style2.setFillForegroundColor((short)43);
            style2.setFillPattern((short)1);
            style2.setBorderBottom((short)1);
            style2.setBorderLeft((short)1);
            style2.setBorderRight((short)1);
            style2.setBorderTop((short)1);
            style2.setAlignment((short)2);
            style2.setVerticalAlignment((short)1);
            HSSFFont font2 = workbook.createFont();
            font2.setBoldweight((short)400);
            style2.setFont(font2);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            HSSFRow row = sheet.createRow(0);

            for(int i = 0; i < headers.length; ++i) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            Iterator<T> it = dataset.iterator();
            int index = 0;

            while(it.hasNext()) {
                ++index;
                row = sheet.createRow(index);
                T t = it.next();
                Field[] fields = t.getClass().getDeclaredFields();

                for(int i = 0; i < fields.length; ++i) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                    try {
                        Class tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName);
                        Object value = getMethod.invoke(t);
                        String textValue = null;
                        if (value instanceof Date) {
                            Date date = (Date)value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        } else if (value instanceof byte[]) {
                            row.setHeightInPoints(60.0F);
                            sheet.setColumnWidth(i, 2856);
                            byte[] bsValue = (byte[])((byte[])value);
                            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short)6, index, (short)6, index);
                            anchor.setAnchorType(2);
                            patriarch.createPicture(anchor, workbook.addPicture(bsValue, 5));
                        } else if (value != null) {
                            textValue = value.toString();
                        }

                        if (textValue != null) {
                            if ("getMerRegSourceId".equals(getMethodName)) {
                                HSSFRichTextString richString = new HSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            } else {
                                Pattern p = Pattern.compile("([-\\+]?[1-9]([0-9]*)(\\.[0-9]+)?)|(^0$)");
                                Matcher matcher = p.matcher(textValue);
                                if (matcher.matches()) {
                                    cell.setCellValue(df.format(Double.parseDouble(textValue)));
                                } else {
                                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
                                    HSSFFont font3 = workbook.createFont();
                                    font3.setColor((short)12);
                                    richString.applyFont(font3);
                                    cell.setCellValue(richString);
                                }
                            }
                        }
                    } catch (SecurityException var43) {
                        var43.printStackTrace();
                    } catch (NoSuchMethodException var44) {
                        var44.printStackTrace();
                    } catch (IllegalArgumentException var45) {
                        var45.printStackTrace();
                    } catch (IllegalAccessException var46) {
                        var46.printStackTrace();
                    } catch (InvocationTargetException var47) {
                        var47.printStackTrace();
                    } finally {
                        ;
                    }
                }
            }

            try {
                workbook.write(out);
            } catch (IOException var42) {
                var42.printStackTrace();
            }
        }

    }

    //导出方法2：导出类的全部字段数据（压缩文件格式 or excel格式），type做区分
    //title：sheet名；headers：表头一列；dataset：数据；pattern：用于定义日期格式；type：区分是ZIP压缩格式还是excel格式；
    public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern, int type) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth((short)15);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor((short)40);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setAlignment((short)2);
        HSSFFont font = workbook.createFont();
        font.setColor((short)20);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight((short)700);
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor((short)43);
        style2.setFillPattern((short)1);
        style2.setBorderBottom((short)1);
        style2.setBorderLeft((short)1);
        style2.setBorderRight((short)1);
        style2.setBorderTop((short)1);
        style2.setAlignment((short)2);
        style2.setVerticalAlignment((short)1);
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight((short)400);
        style2.setFont(font2);
        HSSFRow row = sheet.createRow(0);

        for(short i = 0; i < headers.length; ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        Iterator<T> it = dataset.iterator();
        int index = 0;

        while(it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            T t = it.next();
            Field[] fields = t.getClass().getDeclaredFields();

            for(short i = 0; i < fields.length; ++i) {
                HSSFCell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName);
                    Object value = getMethod.invoke(t);
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date)value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value != null) {
                        textValue = value.toString();
                    }

                    if (textValue != null) {
                        if ("getMerRegSourceId".equals(getMethodName)) {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        } else {
                            Pattern p = Pattern.compile("([-\\+]?[1-9]([0-9]*)(\\.[0-9]+)?)|(^0$)");
                            Matcher matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                cell.setCellValue(df.format(Double.parseDouble(textValue)));
                            } else {
                                HSSFRichTextString richString = new HSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            }
                        }
                    }
                } catch (SecurityException var41) {
                    var41.printStackTrace();
                } catch (NoSuchMethodException var42) {
                    var42.printStackTrace();
                } catch (IllegalArgumentException var43) {
                    var43.printStackTrace();
                } catch (IllegalAccessException var44) {
                    var44.printStackTrace();
                } catch (InvocationTargetException var45) {
                    var45.printStackTrace();
                } finally {
                    ;
                }
            }
        }

        switch(type) {
            case 0:
                try {
                    workbook.write(out);
                } catch (IOException var40) {
                    var40.printStackTrace();
                }
                break;
            case 1:
                ZipOutputStream zip = new ZipOutputStream(out);
                ZipEntry entry = new ZipEntry(title + ".xls");
                zip.putNextEntry(entry);

                try {
                    workbook.write(zip);
                    zip.close();
                } catch (IOException var39) {
                    var39.printStackTrace();
                }
        }

    }

    //导出方法3：自定义类的导出字段数据（由props确定）
    //title：sheet名；headers：表头一列；props：表头一列对应的get方法；dataset：数据；pattern：用于定义日期格式；
    public void exportExcelCommon(String title, String[] headers, String[] props, Collection<T> dataset, OutputStream out, String pattern) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth((short)15);
        //样式1和字体1：style 和 font（这个方法实际只使用了样式1）
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor((short)40);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setAlignment((short)2);
        HSSFFont font = workbook.createFont();
        font.setColor((short)20);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight((short)700);
        style.setFont(font);
        //样式2和字体2：style2 和 font2
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor((short)43);
        style2.setFillPattern((short)1);
        style2.setBorderBottom((short)1);
        style2.setBorderLeft((short)1);
        style2.setBorderRight((short)1);
        style2.setBorderTop((short)1);
        style2.setAlignment((short)2);
        style2.setVerticalAlignment((short)1);
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight((short)400);
        style2.setFont(font2);
        HSSFRow row = sheet.createRow(0);

        //创建表头一行
        for(short i = 0; i < headers.length; ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //遍历数据，依次塞入每行
        Iterator<T> it = dataset.iterator();
        int index = 0;

        while(it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            T t = it.next();

            for(short i = 0; i < props.length; ++i) {
                HSSFCell cell = row.createCell(i);
                String fieldName = props[i];

                try {
                    //1、获取传入数据的类；2、根据传入的方法名，获取类的方法3、调用该对象的方法获取值；4、对值进行处理，并放入Excel中
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(fieldName);
                    Object value = getMethod.invoke(t);
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date)value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof Long) {
                        Long v = (Long)value;
                        if ("getMerRegSourceId".equals(fieldName)) {
                            HSSFRichTextString richString = new HSSFRichTextString(v.toString());
                            cell.setCellValue(richString);
                            continue;
                        }

                        textValue = df.format(v);
                    } else if (value instanceof Double) {
                        Double v = (Double)value;
                        textValue = df.format(v);
                    } else if (value instanceof Integer) {
                        Integer v = (Integer)value;
                        textValue = df.format(v);
                    } else if (value != null) {
                        textValue = value.toString();
                    }

                    if (textValue != null) {
                        Pattern p = Pattern.compile("([-\\+]?[1-9]([0-9]*)(\\.[0-9]+)?)|(^0$)");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            cell.setCellValue(df.format(Double.parseDouble(textValue)));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException var36) {
                    var36.printStackTrace();
                } catch (NoSuchMethodException var37) {
                    var37.printStackTrace();
                } catch (IllegalArgumentException var38) {
                    var38.printStackTrace();
                } catch (IllegalAccessException var39) {
                    var39.printStackTrace();
                } catch (InvocationTargetException var40) {
                    var40.printStackTrace();
                } finally {
                    ;
                }
            }
        }

        try {
            workbook.write(out);
        } catch (IOException var35) {
            var35.printStackTrace();
        }

    }
}
