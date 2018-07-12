package com.MyService;

import com.MyModel.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by panghaichen on 2018-07-12 09:50
 **/
@Service
public interface ExcelExportService {
    Result exportAdvertiseReportData(Map<String, Object> var1, HttpServletResponse var2) throws IOException;
}
