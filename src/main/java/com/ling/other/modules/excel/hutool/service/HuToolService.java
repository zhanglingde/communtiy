package com.ling.other.modules.excel.hutool.service;

import com.ling.other.modules.excel.hutool.dto.PackageDTO;

import javax.servlet.http.HttpServletResponse;

public interface HuToolService {

    /**
     * 导出合并单元格Excel
     * @param packageDTO
     * @param response
     */
    void exportMerge(PackageDTO packageDTO, HttpServletResponse response);
}
