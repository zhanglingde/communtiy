package com.ling.other.modules.excel.excelutil.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ling.other.mapper.EasyExcelMapper;
import com.ling.other.modules.excel.easyexcel.vo.PoLineVO;
import com.ling.other.modules.excel.excelutil.service.ExcelUtilService;
import com.ling.other.modules.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author zhangling 2021/1/14 11:16
 */
@Service
public class ExcelUtilServiceImpl implements ExcelUtilService {

    @Autowired
    EasyExcelMapper easyExcelMapper;

    @Override
    public void download(HttpServletResponse response) throws IOException {
        List<User> list = easyExcelMapper.selectAll();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户表", "UTF-8");

        // 测试编码
        System.out.println("编码一次：" + fileName);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        System.out.println("编码两次：" + fileName);
        fileName = URLDecoder.decode(fileName, "UTF-8");
        System.out.println("解码一次：" + fileName);
        System.out.println("解码两次：" + URLDecoder.decode(fileName, "UTF-8"));


        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), User.class).sheet("用户表").doWrite(list);

    }

    @Override
    public void importExcelByExcelUtil(MultipartFile file) {
        try {
            Class cls = PoLineVO.class;
            Class c = Class.forName("com.ling.other.modules.excel.easyexcel.vo.PoLineVO");
            Constructor constructor = c.getConstructor();
            Constructor con = cls.getConstructor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //List<PoLineVO> lineVOList = ExcelUtil.readExcel("", PoLineVO.class, file);
        //lineVOList.forEach(System.out::println);
    }
}
