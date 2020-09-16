package com.ling.other.modules.easyexcel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * EasyExcel
 * @author zhangling
 * @since 2020/8/31 12:02
 */
public interface EasyExcelService {


    /**
     * 导入Excle
     *
     * @param file
     */
    void importExcel(MultipartFile file);

    /**
     * 下载Excel
     * @param response
     */
    void download(HttpServletResponse response) throws IOException;


    /**
     * 使用ExcelUtil导入
     * @param file
     */
    void importExcelByExcelUtil(MultipartFile file);

    void test() throws ClassNotFoundException;
}
