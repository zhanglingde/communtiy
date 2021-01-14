package com.ling.other.modules.excel.excelutil.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangling 2021/1/14 11:13
 */
public interface ExcelUtilService {

    /**
     * ExcelUtil 导出Excel
     * @param response
     */
    void download(HttpServletResponse response) throws IOException;

    /**
     * 使用ExcelUtil导入
     * @param file
     */
    void importExcelByExcelUtil(MultipartFile file);
}
