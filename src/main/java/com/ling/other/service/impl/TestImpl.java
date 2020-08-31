package com.ling.other.service.impl;

import com.ling.other.mapper.EasyExcelMapper;
import com.ling.other.service.EasyExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
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
}
