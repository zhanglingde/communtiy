package com.ling.other.modules.scheduler.service.impl;


import com.ling.other.entity.ExecutorDO;
import com.ling.other.mapper.ExecutorMapper;
import com.ling.other.modules.scheduler.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 执行器业务层
 * @author zhangling
 * @since 2020/9/3 13:55
 */
@Service
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
}
