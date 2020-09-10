package com.ling.other.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.service.EasyExcelService;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * EasyExcel的使用
 * @author zhangling
 * @since 2020/8/31 12:01
 */
@RestController
public class EasyExcelController {

    @Resource(name = "easyExcelServiceImpl")
    private EasyExcelService easyExcelService;

    @Qualifier("test")
    @Autowired
    private EasyExcelService testImpl;


    @ApiOperation("导入Excel")
    @PostMapping("/import")
    public String importExcel(HttpServletRequest request){
        MultipartFile file = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("file");
        }
        easyExcelService.importExcel(file);
        return "success";
    }

    @ApiOperation(value = "导出Excel")
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        easyExcelService.download(response);
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

        easyExcelService.importExcelByExcelUtil(file);

        return CommonResult.success(null, "操作成功");
    }

    @GetMapping("/test")
    public CommonResult test() throws ClassNotFoundException {
        easyExcelService.test();
        return CommonResult.success();
    }


}
