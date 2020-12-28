package com.ling.other.modules.scheduler.service.impl;

/**
 * @author zhangling
 * @since 2020/12/28 13:49
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.other.common.constants.BaseConstants;
import com.ling.other.modules.scheduler.dto.JobDataDTO;
import com.ling.other.modules.scheduler.dto.JobInfoDTO;
import com.ling.other.modules.scheduler.dto.JobLogDTO;
import com.ling.other.modules.scheduler.dto.JobProgress;
import com.ling.other.modules.scheduler.myenum.ReturnT;
import com.ling.other.modules.scheduler.registry.JobRegistry;
import com.ling.other.modules.scheduler.registry.ThreadRegistry;
import com.ling.other.modules.scheduler.service.IJobHandler;
import com.ling.other.modules.scheduler.service.IJobHandlerAllowStop;
import com.ling.other.modules.scheduler.service.JobExecuteService;
import com.ling.other.modules.scheduler.utils.SchedulerTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JobExecuteServiceImpl implements JobExecuteService {

    private static final Logger logger = LoggerFactory.getLogger(JobExecuteServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public String jobExecute(JobDataDTO jobDataDTO) {
        String jobHandler = jobDataDTO.getJobHandler();
        Object handler = JobRegistry.getJobHandler(jobHandler);
        Long jobId = jobDataDTO.getJobId();
        Long logId = jobDataDTO.getLogId();
        JobLogDTO logDTO = JobLogDTO.builder().logId(logId).jobId(jobId).build();

        if(handler == null){
            // 查找任务失败
            // TODO 设置失败日志
            return "SUCCESS";
        }
        IJobHandler iJobHandler = null;
        Thread thread = Thread.currentThread();
        SchedulerTool tool = new SchedulerTool();

        try {
            if(handler instanceof IJobHandlerAllowStop){
                iJobHandler = (IJobHandlerAllowStop) handler;
                //     缓存中写入线程和iJobHandler
                ThreadRegistry.addJobHandler(thread.getId(), iJobHandler);
                ThreadRegistry.addThread(thread, jobId);
                jobRun(iJobHandler, jobDataDTO, logDTO, tool);
            }else{
                iJobHandler = (IJobHandler) handler;
                jobRun(iJobHandler,jobDataDTO,logDTO,tool);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            // 任务执行出现异常时执行
            iJobHandler.onException(tool);
            //JobProgress progress = tool.getJobProgress();
            //jobLogBackService.updateLog(logDTO.setClientResult(BootSchedulerConstant.ClientResult.FAILURE)
            //        .setMessageHeader(e.getMessage())
            //        .setMessage("Job progress:" + progress.getProgress() + "%, message: " + progress.getMessage() + "\n" + ExceptionUtils.getMessage(e)));
            return "SUCCESS";
        }finally {
            // 清除线程以及iJobHandler的缓存
            ThreadRegistry.deleteThread(thread);
            ThreadRegistry.deleteJobHandler(thread.getId());
        }

        return "SUCCESS";
    }

    private void jobRun(IJobHandler iJobHandler, JobDataDTO jobDataDTO, JobLogDTO logDTO, SchedulerTool tool) throws IOException {
        String url = StringUtils.EMPTY;
        // 任务开始前执行
        iJobHandler.onCreate(tool);
        // 初始化任务进度
        //tool.updateProgress(BaseConstants.Digital.ZERO, "Job init.");
        Map<String, String> map = StringUtils.isNotBlank(jobDataDTO.getParam()) ?
                objectMapper.readValue(jobDataDTO.getParam(), new TypeReference<Map<String, String>>() {
                }) : new HashMap<>(16);
        // 执行定时任务 的 业务方法
        ReturnT result = iJobHandler.execute(map, tool);
        // 清除进度缓存
        //tool.clearProgress(jobDataDTO.getLogId());
        //if (tool.getByteArrayOutputStream().size() > BaseConstants.Digital.ZERO) {
        //    String suffix = StringUtils.isNotBlank(tool.getFileSuffix()) ? tool.getFileSuffix() : ".txt";
        //    // 上传文件
        //    url = fileClient.uploadFile(jobDataDTO.getTenantId(), BootSchedulerConstant.BUCKET_NAME,
        //            BootSchedulerConstant.DIRECTORY, jobDataDTO.getJobCode() + suffix, tool.getByteArrayOutputStream().toByteArray());
        //}
        //tool.closeLogger();
        //// 任务正常执行结束后执行
        //iJobHandler.onFinish(tool, result);
        //switch (result) {
        //    case SUCCESS:
        //        jobLogBackService.updateLog(logDTO.setClientResult(BootSchedulerConstant.ClientResult.SUCCESS).setLogUrl(url));
        //        break;
        //    case FAILURE:
        //        jobLogBackService.updateLog(logDTO.setClientResult(BootSchedulerConstant.ClientResult.WARNING).setLogUrl(url));
        //        break;
        //    default:
        //        jobLogBackService.updateLog(logDTO.setClientResult(BootSchedulerConstant.ClientResult.FAILURE)
        //                .setMessageHeader("empty.").setMessage("empty Result.").setLogUrl(url));
        //        break;
        //}
    }
}
