package com.ling.other.modules.excel.hutool.controller;

import com.ling.other.modules.excel.hutool.dto.PackageDTO;
import com.ling.other.modules.excel.hutool.service.HuToolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/hutool-excel")
public class HuToolExcelController {

    @Autowired
    HuToolService huToolService;


    @ApiOperation("导出合并单元格")
    @GetMapping("/export-merge")
    public void exportMerge(PackageDTO packageDTO, HttpServletResponse response) {

        huToolService.exportMerge(packageDTO,response);

    }
}
