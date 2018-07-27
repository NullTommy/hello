package com.MyController;

import com.MyService.ExcelExportService;
import com.MyModel.ExportDto;
import com.MyUtils.ExcelUtil;
import com.MyModel.MyBean;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
