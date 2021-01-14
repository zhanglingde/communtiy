package com.ling.other.modules.excel.easyexcel.controller;

import com.ling.other.common.utils.CommonResult;
import com.ling.other.modules.excel.easyexcel.service.EasyExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * EasyExcel的使用
 * @author zhangling
 * @since 2020/8/31 12:01
 */
@RestController
@RequestMapping("/easyExcel")
@Api(tags = "EasyExcel")
public class EasyExcelController {

    @Resource(name = "easyExcelServiceImpl")
    private EasyExcelService easyExcelService;





    @ApiOperation(value = "导出Excel")
    @GetMapping("/user/export")
    public CommonResult easyExcelExportUser(HttpServletResponse response){
        easyExcelService.exportUser(response);
        return CommonResult.success();
    }

    @ApiOperation(value = "导出Excel")
    @GetMapping("/user/export/strategy")
    public CommonResult easyExcelExportUserStrategy(HttpServletResponse response){
        easyExcelService.exportUserByStrategy(response);
        return CommonResult.success();
    }


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









}
