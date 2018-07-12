package com.MyUtils;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Da-Sheng Date: 2012-2-21 Time: 22:32:06 To
 * change this template use File | Settings | File Templates.
 */
public class ExcelUtil {
	//private Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	private static final ExcelUtil instance = new ExcelUtil();

	private ExcelUtil() {
	}

	public static ExcelUtil getInstance() {
		return instance;
	}

	/**
     * @param request
     * @param templateName
     *            ：位于指定的路径下的模板文件名
     * @param dataMap
     *            ：需要导出的数据
     * @throws Exception
     */
	public void exportExcel(HttpServletRequest request,
                            HttpServletResponse response, String exportName,
                            String templateName, Map<String, Object> dataMap) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ URLEncoder.encode(exportName, "utf-8") + "\"");

		//根据文件名，结合路径找到模板
		String templateFullPath = request.getSession().getServletContext()
				.getRealPath("/") + "WEB-INF/templates/"+templateName;
		
		//用于日期类型字段在excel中的格式化
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 dataMap.put("dateFormater", dateFormat);
		
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFullPath));
			//这就是实际的导出实现了
			Workbook workbook = transformer.transformXLS(is, dataMap);
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
		}catch (Exception e){
            e.printStackTrace();

        }finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					//logger.error(
					//		"ExcelUtil -> exportExcel() -> is.close() error!",
					//		e);
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					//logger.error(
					//		"ExcelUtil -> exportExcel() -> os.close() error!",
					//		e);
				}
			}
		}
	}

	/**
     * @param request
     * @param templatePathName
     *            ：位于指定的路径下的模板文件名
     * @param dataMap
     *            ：需要导出的数据
     * @throws Exception
     */
	public Workbook exportExcel(HttpServletRequest request,
                                String templatePathName, Map<String, Object> dataMap)
			throws Exception {
		String templateFullPath = request.getSession().getServletContext()
				.getRealPath("/") +templatePathName;
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFullPath));
			Workbook workbook = transformer.transformXLS(is, dataMap);
			return workbook;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					//logger.error(
					//		"ExcelUtil -> exportExcel() -> is.close() error!",
					//		e);
				}
			}
		}
	}

	/**
     * @param request
     * @param exportName
     *            ：生成下载的文件名
     * @param templateName
     *            ：位于指定的路径下的模板文件名
     * @param dataMap
     *            ：需要导出的数据
     * @throws Exception
     */
	public Workbook exportExcel(HttpServletRequest request,
                                String exportName, String templateName, Map<String, Object> dataMap)
			throws Exception {
		String templateFullPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "order/templates/" + templateName;
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFullPath));
			Workbook workbook = transformer.transformXLS(is, dataMap);
			return workbook;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					//logger.error(
					//		"ExcelUtil -> exportExcel() -> is.close() error!",
					//		e);
				}
			}
		}
	}
        

}
