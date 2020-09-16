package com.ling.other.service.impl;

import com.ling.other.modules.easyexcel.service.EasyExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangling
 * @since 2020/8/31 14:30
 */
@Service("test")
public class TestImpl implements EasyExcelService {
    @Override
    public void importExcel(MultipartFile file) {

    }

    @Override
    public void download(HttpServletResponse response) throws IOException {

    }

    @Override
    public void importExcelByExcelUtil(MultipartFile file) {

    }

    @Override
    public void test() throws ClassNotFoundException {

    }
}
