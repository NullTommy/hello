package com.MyController;

import com.MyService.ExcelExportService;
import com.MyModel.ExportDto;
import com.MyUtils.ApplicationContextUtil;
import com.MyUtils.CommonExcelUtil;
import com.MyUtils.ExcelUtil;
import com.MyModel.MyBean;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by panghaichen on 2018-06-27 14:56
 **/

@Controller
public class MainController {

    @Autowired
    public ExcelExportService excelExportService;
    @Resource
    private MyBean myBean;
    @Resource
    private BasicDataSource dataSource;

    @RequestMapping(value = "/test.do",method = RequestMethod.GET)
    public String index() {
        //http://localhost:8080/test.do
        System.out.println("run：" + "index");
        return "index-return";
    }

    @RequestMapping(value = "/testBean.do",method = RequestMethod.GET)
    public String testBean() {
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        Object fuLu =  applicationContext.getBean("myBean");
        //http://localhost:8080/test.do
        return "index";
    }

    @RequestMapping(value = "/test2.do",method = RequestMethod.GET)
    public String index2() {
        //http://localhost:8080/test2.do
        return "jsp/index";
    }

    @RequestMapping(value = "/language.do",method = RequestMethod.GET)
    public String language() {
        //http://localhost:8080/language.do
        return "jsp/language";
    }

    @RequestMapping(value = "/testMyBean.do",method = RequestMethod.GET)
    public String testMyBean() {
        //http://localhost:8080/testMyBean.do
        System.out.println("Spring根据属性文件自动注入：MyBean的Name:"+myBean.getName());
        System.out.println("Spring根据属性文件自动注入：MyBean的ID:"+myBean.getId());
        return JSON.toJSONString(myBean);
    }

    @RequestMapping(value = "/testDataSource.do",method = RequestMethod.GET)
    public void testDataSource() {
        String sql = "select * from test"; //具体sql

        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("id : " + rs.getInt(1) + " name : "
                        + rs.getString(2) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/exportIndex.do",method = RequestMethod.GET)
    public String exportIndex() {
        //http://localhost:8080/exportIndex.do
        return "jsp/exportIndex";
    }


    //导出功能1：导出类中的某些字段，通过反射实现。
    @RequestMapping(value = "export.do")
    public void export(HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 列标题
        String[] headers = { "id", "string", "string"};
        // 对应的属性
        String[] props = { "getId", "getString", "getStatus"};
        //手动造数据
        List<ExportDto> exportDtoList = new ArrayList<>();
        for (int i = 0; i <5 ;i++){
            ExportDto exportDto = new ExportDto();
            exportDto.setId(i);
            exportDto.setString("test"+i);
            exportDto.setStatus(i);
            exportDtoList.add(exportDto);
        }

        resultMap.put("headers", headers);
        resultMap.put("fileName", "export");
        resultMap.put("props", props);
        resultMap.put("content", exportDtoList);

        try {
            excelExportService.exportAdvertiseReportData(resultMap, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导出功能2：
    @RequestMapping(value = "export2.do")
    public void export2(HttpServletRequest request,HttpServletResponse response){
        //手动造数据
        List<ExportDto> exportDtoList = new ArrayList<>();
        for (int i = 0; i <5 ;i++){
            ExportDto exportDto = new ExportDto();
            exportDto.setId(i);
            exportDto.setString("test"+i);
            exportDto.setStatus(i);
            exportDtoList.add(exportDto);
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("export", exportDtoList);

        try {
            ExcelUtil.getInstance().exportExcel(request, response, "exportExport.xlsx",
                    "exportTemplate.xlsx", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //导出功能3：一行一行拼接导出
    @RequestMapping(value = "export3.do")
    public void export3(HttpServletRequest request,HttpServletResponse response){
        //手动造数据
        List<MyBean> myBeanList = new ArrayList<>();
        for (int i = 0; i <5 ;i++){
            MyBean myBean = new MyBean();
            myBean.setId(String.valueOf(i));
            myBean.setName("test"+i);
            myBeanList.add(myBean);
        }

        HSSFWorkbook wb = exportExcelForMyBean(myBeanList);
        String filename = "Export" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString())+".xls";
        exportToExcel(wb,filename,response);

    }

    //private 方法
    private HSSFWorkbook exportExcelForMyBean(List<MyBean> myBeanList) {
        // 建立新HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象
        String title = "AbandonedDetail";
        HSSFSheet sheet = wb.createSheet(title);//设置sheet名称

        // 设置列宽
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);

        int rowIndex = 0;// 行数
        int colIndex = 0;// 列数

        //long数值样式
        HSSFCellStyle longCellStyle = wb.createCellStyle();
        CommonExcelUtil.initHSSFCellStyle(longCellStyle);
        //double数值样式
        HSSFCellStyle doubleCellStyle = wb.createCellStyle();
        CommonExcelUtil.initHSSFCellStyle(doubleCellStyle);
        //percentCellStyle数值样式
        HSSFCellStyle percentCellStyle = wb.createCellStyle();
        CommonExcelUtil.initHSSFCellStyle(percentCellStyle);
        //设置通用样式属性
        HSSFCellStyle cellStyle = wb.createCellStyle();//通用的样式
        HSSFFont cfont = wb.createFont();
        CommonExcelUtil.initCommonStyle(cellStyle, cfont);

        // headStyle样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        HSSFPalette customPalette = wb.getCustomPalette();
        customPalette.setColorAtIndex(HSSFColor.BLUE.index, (byte) 47, (byte) 117, (byte) 181);
        CommonExcelUtil.initHeadStyle(headStyle, font);

//        //第三行(日期)
//        HSSFRow statiHeader0 = sheet.createRow(rowIndex++);
//        CommonExcelUtil.createCostomCell(statiHeader0, colIndex++, "我是标识", headStyle);
//        colIndex = 0;

        //第三行(日期)
        HSSFRow statiHeader = sheet.createRow(rowIndex++);


        CommonExcelUtil.createCostomCell(statiHeader, colIndex++, "ID", headStyle);
        CommonExcelUtil.createCostomCell(statiHeader, colIndex++, "NAME", headStyle);

        colIndex = 0;
        if (myBeanList != null && myBeanList.size() > 0) {
            for (MyBean myBean : myBeanList) {
                HSSFRow resultData = sheet.createRow(rowIndex);
                CommonExcelUtil.createCostomCell(resultData, colIndex++, myBean.getId() == null ? 0L
                        : myBean.getId(), longCellStyle);
                CommonExcelUtil.createCostomCell(resultData, colIndex++, myBean.getName() == null ? 0L
                        : myBean.getName(), longCellStyle);

                // 列号换行
                colIndex = 0;
                // 下一行
                rowIndex++;
            }
        }
        return wb;
    }

    //导出Excel文件
    private void exportToExcel(HSSFWorkbook wb, String filename, HttpServletResponse response) {
        try {
            filename = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //log.error("::", e);
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename="
                + filename);
        OutputStream output = null;
        try {
            output = response.getOutputStream();
            wb.write(output);
            output.flush();
        } catch (Exception e) {
            //log.error("::", e);
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    //log.error("::", e);
                    e.printStackTrace();
                }
                output = null;
            }
        }
    }

}
