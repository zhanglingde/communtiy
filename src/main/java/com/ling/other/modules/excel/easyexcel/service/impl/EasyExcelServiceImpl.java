package com.ling.other.modules.excel.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.ling.other.modules.excel.easyexcel.dto.ExcelExportLineDTO;
import com.ling.other.modules.excel.easyexcel.handle.CustomCellWriteHandler;
import com.ling.other.modules.excel.easyexcel.listener.UserDataListener;
import com.ling.other.modules.excel.easyexcel.vo.UserVO;
import com.ling.other.modules.user.dto.User;
import com.ling.other.mapper.EasyExcelMapper;
import com.ling.other.modules.excel.easyexcel.service.EasyExcelService;
import com.ling.other.modules.excel.easyexcel.vo.PoLineVO;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
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
    public void exportUser(HttpServletResponse response) {
        List<UserVO> list = easyExcelMapper.list();

        ExcelWriter excelWriter = null;
        try {
            // 响应设置
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", " Content-Disposition");
            // 设置文件名
            String fileName = URLEncoder.encode("订单包裹列表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 导出Excel，设置sheet模板 和 导出的list数据

            // 写法一：
            //EasyExcel.write(response.getOutputStream(), UserVO.class).sheet("用户表").doWrite(list);

            // 写法二：
            //  同一个excelWriter对象 写入的就是一个Excel文件里
            // 同一个 WriteSheet 对象就是写入同一个sheet中
            excelWriter = EasyExcel.write(response.getOutputStream(), UserVO.class).build();
            // 这里list可以修改写多个数据 和 写到多个sheet里
            for (int i = 0; i < 5; i++) {
                // 将数据写到 5 个sheet列表里
                List<UserVO> list2 = easyExcelMapper.list();
                WriteSheet writeSheet = EasyExcel.writerSheet("用户列表" + i).build();
                excelWriter.write(list2, writeSheet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // finish方法会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public void exportUserByStrategy(HttpServletResponse response) {
        List<UserVO> list = easyExcelMapper.list();

        ExcelWriter excelWriter = null;
        try {
            // 响应设置
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", " Content-Disposition");
            // 设置文件名
            String fileName = URLEncoder.encode("订单包裹列表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 导出Excel，设置sheet模板 和 导出的list数据
            WriteSheet writeSheet = EasyExcel.writerSheet("用户列表").build();

            // 使用自动列宽处理策略
            //LongestMatchColumnWidthStyleStrategy strategy = new LongestMatchColumnWidthStyleStrategy();
            // 头的策略
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            // 背景色
            headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            //设置字体大小
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontHeightInPoints((short) 12);
            headWriteCellStyle.setWriteFont(headWriteFont);
            // 内容的策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
            //contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            //contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            // 字体策略
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 12);
            contentWriteCellStyle.setWriteFont(contentWriteFont);

            //设置 自动换行
            contentWriteCellStyle.setWrapped(true);
            //设置 垂直居中
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            //设置 水平居中
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //设置边框样式
            //contentWriteCellStyle.setBorderLeft(DASHED);
            //contentWriteCellStyle.setBorderTop(DASHED);
            //contentWriteCellStyle.setBorderRight(DASHED);
            //contentWriteCellStyle.setBorderBottom(DASHED);

            // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

            excelWriter = EasyExcel.write(response.getOutputStream(), UserVO.class)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .build();
            excelWriter.write(list, writeSheet);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // finish方法会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }


    @Override
    public void importExcel(MultipartFile file) {
        List<ExcelExportLineDTO> list = new ArrayList<>();

        try {
            EasyExcel.read(file.getInputStream(), User.class, new UserDataListener(easyExcelMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
