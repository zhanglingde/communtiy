package com.ling.other.modules.excel.hutool.service.Impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.ling.other.mapper.PackageDao;
import com.ling.other.modules.excel.hutool.dto.PackageDTO;
import com.ling.other.modules.excel.hutool.service.HuToolService;
import com.ling.other.modules.excel.hutool.vo.HuToolExportPackageVO;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HuToolServiceImpl implements HuToolService {

    @Autowired
    PackageDao packageDao;


    @Override
    public void exportMerge(PackageDTO packageDTO, HttpServletResponse response) {
        List<HuToolExportPackageVO> list = packageDao.selectExportVO(packageDTO);
        Map<String, List<HuToolExportPackageVO>> map = list.stream().collect(Collectors.groupingBy(HuToolExportPackageVO::getPackageNum));

        ExcelWriter writer = ExcelUtil.getWriter(true);

        try {
            // 设置输出流格式
            String fileName = URLEncoder.encode("订单包裹列表", "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Expose-Headers", " Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 设置样式
            writer.setColumnWidth(0,25);
            writer.setColumnWidth(1,10);
            writer.setColumnWidth(2,10);
            writer.setColumnWidth(3,10);
            writer.setColumnWidth(4,35);
            writer.setColumnWidth(5,15);
            writer.setColumnWidth(6,10);
            writer.setColumnWidth(7,10);
            writer.setColumnWidth(8,10);
            writer.setColumnWidth(9,10);
            writer.setColumnWidth(10,15);
            writer.setColumnWidth(11,35);
            writer.setColumnWidth(12,20);
            writer.setColumnWidth(13,15);

            // 设置导出的模板
            writer.addHeaderAlias("packageNum", "订单包裹号");
            writer.addHeaderAlias("logisticsNum", "物流单号");
            writer.addHeaderAlias("courierCompany", "快递公司");
            writer.addHeaderAlias("commodityNo", "货品Id");
            writer.addHeaderAlias("commodityName", "货品名称");
            writer.addHeaderAlias("barcode", "条形码");
            writer.addHeaderAlias("costPrice", "采购价");
            writer.addHeaderAlias("quantity", "货品数量");
            writer.addHeaderAlias("lineAmount", "货品金额");
            writer.addHeaderAlias("receiptName", "收件人");
            writer.addHeaderAlias("receiptTelNum", "收件电话");
            writer.addHeaderAlias("receiptAddress", "收件地址");
            writer.addHeaderAlias("transferTime", "推单时间");
            writer.addHeaderAlias("status", "状态");
            // 合并单元格
            int i = 1;
            for (Map.Entry<String, List<HuToolExportPackageVO>> val : map.entrySet()) {
                for (int j = 0; j < 3; j++) {
                    if (val.getValue().size() > 1) {
                        writer.merge(i, i + val.getValue().size() - 1, j, j, null, true);
                    }
                }
                // for (int j = 9; j < 14; j++) {
                //     if (val.getValue().size() > 1) {
                //         writer.merge(i, i + val.getValue().size() - 1, j, j, null, true);
                //     }
                // }
                i += val.getValue().size();
            }
            writer.write(list,true);




            writer.flush(response.getOutputStream(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
        }


    }
}
