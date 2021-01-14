package com.ling.other.modules.excel.easyexcel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * EasyExcel
 * @author zhangling
 * @since 2020/8/31 12:02
 */
public interface EasyExcelService {





    /**
     * 导出User信息
     * 导出一个或多个 sheet 列中
     * @param response
     */
    void exportUser(HttpServletResponse response);

    /**
     * 导出Excel 使用策略
     * 合并行、使用样式等策略
     * @param response
     */
    void exportUserByStrategy(HttpServletResponse response);

    /**
     * 导入 Excle
     * 使用监听器 listener,
     * listener不归Spring容器管理，不能注入容器，过滤器，监听器是Servlet生命周期的
     *
     * @param file
     */
    void importExcel(MultipartFile file);
}
