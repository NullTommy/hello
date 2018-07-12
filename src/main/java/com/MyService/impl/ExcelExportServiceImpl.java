package com.MyService.impl;

import com.MyService.ExcelExportService;
import com.MyUtils.ExportExcel;
import com.MyModel.Result;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by panghaichen on 2018-07-12 09:55
 **/
@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    public ExcelExportServiceImpl() {
    }

    public Result exportAdvertiseReportData(Map<String, Object> map, HttpServletResponse response) throws IOException {
        Result result = new Result();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        //获取并使用文件名
        String fileName = (String)map.get("fileName");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
        } catch (UnsupportedEncodingException var15) {
            //log.error("error:exportNewTicketExport...");
            result.setSuccess(false);
            return result;
        }

        //获取数据：表头和对应的方法，用于构建导出文件
        List<Object> contentExportList = (List)map.get("content");
        String[] headers = (String[])((String[])map.get("headers"));
        String[] props = (String[])((String[])map.get("props"));
        ServletOutputStream outputStream = null;

        label71: {
            Result var10;
            try {
                ExportExcel<Object> ex = new ExportExcel();
                outputStream = response.getOutputStream();
                ex.exportExcelCommon(fileName, headers, props, contentExportList, outputStream, "yyyy-MM-dd HH:mm:ss");
                break label71;
            } catch (Exception var16) {
                //log.error("调用导出Excel方法出错!error:exportNewTicketExport--->exportExcel()", var16);
                result.setSuccess(false);
                var10 = result;
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }

            }

            return var10;
        }

        result.setSuccess(true);
        return result;
    }
}
