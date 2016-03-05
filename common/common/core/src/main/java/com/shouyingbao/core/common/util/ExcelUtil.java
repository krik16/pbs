/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年5月27日下午5:25:59
 * @Description: TODO
 *
 **/

package com.shouyingbao.core.common.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

/**	
 * @Author:  柯军
 * @Description: 报表导出工具类
 * @datetime:2015年5月27日下午5:25:59
 *
 **/

public class ExcelUtil {
    public static void exportExcel(HttpServletResponse response, Workbook wb, 
            String fileName) throws IOException { 
        // 如果文件名有中文，必须URL编码 
        fileName = URLEncoder.encode(fileName, "UTF-8"); 
        response.reset(); 
        // ContentType 可以不设置 
        response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName); 
        wb.write(response.getOutputStream()); 
        response.getOutputStream().flush(); 
        response.getOutputStream().close(); 
    }
}
