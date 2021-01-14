package com.ling.other.modules.excel.easyexcel.handle;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomCellWriteHandler implements CellWriteHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCellWriteHandler.class);

//    @Override
//    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
//                                 Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
//
//    }


//    @Override
//    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
//                                Head head, Integer relativeRowIndex, Boolean isHead) {
//
//    }


    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
// 这里可以对cell进行任何操作
        LOGGER.info("第{}行，第{}列写入完成。", cell.getRowIndex(), cell.getColumnIndex());
//        if (!isHead && cell.getStringCellValue().contains("个")) {
        if (!isHead && cell.getColumnIndex() == 1 && cell.getStringCellValue().contains("个")) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            Font cellFont = workbook.createFont();
            cellFont.setBold(true);
            cellStyle.setFont(cellFont);
            cell.setCellStyle(cellStyle);
        }
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }
}