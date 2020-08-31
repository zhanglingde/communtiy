package com.ling.other.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ling.other.dto.ExcelExportLineDTO;
import com.ling.other.dto.User;
import com.ling.other.mapper.EasyExcelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangling
 * @since 2020/8/31 13:11
 */
public class UserDataListener extends AnalysisEventListener<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDataListener.class);

    private static final int BATCH_COUNT = 5;
    List<User> list = new ArrayList<>();

    private EasyExcelMapper easyExcelMapper;

    public UserDataListener(EasyExcelMapper easyExcelMapper){
        this.easyExcelMapper = easyExcelMapper;
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        logger.info("解析到一条数据：{}", JSON.toJSONString(user));
        list.add(user);
        if(list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    private void saveData() {
        logger.info("{}条数据，开始存储数据库！",list.size());
        for (User user : list) {
            System.out.println(user);
        }
        easyExcelMapper.batchInsert(list);
        logger.info("存储数据库成功！");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        logger.info("所有数据解析完成！");
    }
}
