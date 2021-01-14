package com.ling.other.modules.excel.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.ling.other.modules.user.dto.User;
import com.ling.other.mapper.EasyExcelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel导入导出监听器
 * @author zhangling
 * @since 2020/8/31 13:11
 */
public class UserDataListener extends AnalysisEventListener<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDataListener.class);

    private static final int BATCH_COUNT = 3000;
    List<User> list = new ArrayList<>();

    private EasyExcelMapper easyExcelMapper;

    public UserDataListener(EasyExcelMapper easyExcelMapper){
        this.easyExcelMapper = easyExcelMapper;
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        logger.info("解析到一条数据：{}", JSON.toJSONString(user));
        list.add(user);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(list.size()>=BATCH_COUNT){
            saveData();
            // 存储完成清理 list
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

    /**
     * 所有数据解析完成了 都会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        logger.info("所有数据解析完成！");
    }
}
