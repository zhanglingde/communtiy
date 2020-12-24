package com.ling.other.modules.job.service.impl;


import com.ling.other.modules.job.service.NoticeSendJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nanziyu
 * @date 2020/11/13 14:22
 */
@Service
public class NoticeSendJobServiceImpl implements NoticeSendJobService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeSendJobServiceImpl.class);

    @Autowired
    //private NoticePushService noticePushService;

    @Override
    public void sendNotice(String noticeSendId) {
        logger.info("发送任务栏推送，发送的ID为："+noticeSendId);
        //noticePushService.sendNotice(noticeSendId);
    }
}