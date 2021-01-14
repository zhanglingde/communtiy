package com.ling.other.modules.excel.excelutil.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.excel.excelutil.service.ExcelUtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangling 2021/1/14 11:10
 */
@RestController
@Api(tags = "Util工具操作Excel")
public class ExcelUtilController {

    @Autowired
    ExcelUtilService excelUtilService;

    /**
     * 下载
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出Excel")
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        excelUtilService.download(response);
    }

    /**
     * 使用ExcelUtil导入
     * @param request
     * @return
     */
    @ApiOperation(value = "采购单确认", notes = "采购单确认")
    @PostMapping("/confirm/order")
    public CommonResult confirmOrder(HttpServletRequest request) {

        MultipartFile file = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("file");
        }

        excelUtilService.importExcelByExcelUtil(file);

        return CommonResult.success(null, "操作成功");
    }
}
