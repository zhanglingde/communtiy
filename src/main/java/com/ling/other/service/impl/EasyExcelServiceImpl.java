package com.ling.other.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ling.other.dto.ExcelExportLineDTO;
import com.ling.other.dto.User;
import com.ling.other.listener.UserDataListener;
import com.ling.other.mapper.EasyExcelMapper;
import com.ling.other.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangling
 * @since 2020/8/31 12:02
 */
@Service
public class EasyExcelServiceImpl implements EasyExcelService {

    @Autowired
    private EasyExcelMapper easyExcelMapper;

    @Override
    public void importExcel(MultipartFile file) {
        List<ExcelExportLineDTO> list = new ArrayList<>();

        try {
            EasyExcel.read(file.getInputStream(), User.class, new UserDataListener(easyExcelMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(HttpServletResponse response) throws IOException {

        List<User> list = easyExcelMapper.selectAll();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户表", "UTF-8");
        //String fileName = "用户表";

        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), User.class).sheet("用户表").doWrite(list);


    }
}
