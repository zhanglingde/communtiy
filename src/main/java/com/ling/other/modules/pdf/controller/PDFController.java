package com.ling.other.modules.pdf.controller;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.ling.community.mapper.UserMapper;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.modules.user.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置pdf模板，修改表格模板的表单字段，读取模板导出
 *
 * @author zhangling 2021/2/23 11:15
 */
@RestController
@RequestMapping("/pdf")
public class PDFController {

    private static final Logger logger = LoggerFactory.getLogger(PDFController.class);

    @Autowired
    SupplierUserMapper supplierUserMapper;

    //@RegisterToSMP(serviceDisplay = "预览页面PDF下载")
    //@RequestMapping(value = "/DM/gwclwxsq/qygl/exportPDF$m=query.service",method =RequestMethod.POST)
    @GetMapping("/exportPDF")
    public String exportPdf(Integer id, HttpServletResponse response) throws UnsupportedEncodingException {
        // 1.指定解析器
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory","com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        String filename = URLEncoder.encode("用户模板", "UTF-8");
        String path = PDFController.class.getResource("/").getPath() + "templates/用户模板.pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename + ".pdf");
        OutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        try {
            os = response.getOutputStream();
            // 2 读入pdf表单
            reader = new PdfReader(path);
            // 3 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);
            // 4 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 5给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示
            BaseFont bf = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            form.addSubstitutionFont(bf);
            // 6查询数据================================================

            User user = supplierUserMapper.selectUser(id);

            Map<String, String> data = new HashMap<String, String>();
            data.put("id", user.getId().toString());
            data.put("username", user.getUsername());
            data.put("gender", user.getGender());
            data.put("age", user.getAge().toString());
            data.put("birth", user.getBirth().toString());

            // 7遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                form.setField(key, data.get(key).toString());
            }
            ps.setFormFlattening(true);
            logger.info("*******************PDF导出成功***********************");
        } catch (Exception e) {
            logger.error("*******************PDF导出失败***********************");
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                reader.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
