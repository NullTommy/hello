package com.MyController;

import com.MyService.ExcelExportService;
import com.MyModel.ExportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

}
