package com.ling.other.modules.scheduler.service.impl;


import com.ling.other.common.constants.BaseConstants;
import com.ling.other.modules.scheduler.databoject.ExecutorDO;
import com.ling.other.mapper.ExecutorMapper;
import com.ling.other.modules.scheduler.service.ExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 执行器业务层
 * @author zhangling
 * @since 2020/9/3 13:55
 */
@Service("executorServiceImpl")
public class ExecutorServiceImpl implements ExecutorService {

    @Autowired
    private ExecutorMapper executorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExecutorDO createExecutor(ExecutorDO executor) {
        executor.setStatus("ONLINE");
        //if (Objects.equals(executor.getExecutorType(), 0)) {
        //    Assert.isTrue(StringUtils.isNotBlank(executor.getServerName()), "error.data_invalid");
        //} else {
        //    Assert.isTrue(StringUtils.isNotBlank(executor.getAddressList()), "error.data_invalid");
        //}

        this.executorMapper.insertSelective(executor);
        return executor;
    }

    @Override
    public String refreshExecutor(String executorCode) {
        ExecutorDO executor = this.executorMapper.selectOne(new ExecutorDO().builder().executorCode(executorCode).build());
        if (executor != null) {
            //if (StringUtils.isBlank(executor.getServerName())) {
            //    判断远程服务是否为空
            //    executor.setExecutorType(Flag.NO).setStatus("ONLINE").setServerName(serverName);
            //    this.executorRepository.updateByPrimaryKeySelective(executor);
            //    return "SUCCESS";
            //} else {
            // 服务名相同，返回SUCCESS
            //    return Objects.equals(serverName, executor.getServerName()) ? "SUCCESS" : "FAILURE";
            return "SUCCESS";
            //}
        } else {
            // 执行器为空新建执行器
            ExecutorDO executorDO = ExecutorDO.builder()
                    .executorCode(executorCode)
                    .executorName(executorCode)
                    .orderSeq(0)
                    .executorType(0)
                    .status("ONLINE")
                    .build();
            this.executorMapper.insertSelective(executorDO);
            return "SUCCESS";
        }
    }
}
