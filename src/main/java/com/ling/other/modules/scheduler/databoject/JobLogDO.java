package com.ling.other.modules.scheduler.databoject;

import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务日志(JobLog)实体类
 *  
 * @author zhangling
 * @since 2020-09-09 15:41:01
 */
@Data
@Builder
@Table(name = "au_job_log")
public class JobLogDO implements Serializable {
    private static final long serialVersionUID = -92711523000697910L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    /**
    * 任务ID,job_id
    */
    private Long jobId;
    /**
    * 任务调度结果
    */
    private String jobResult;
    /**
    * 客户端执行结果
    */
    private String clientResult;
    /**
    * 执行器ID,executor_id
    */
    private Long executorId;
    /**
    * 任务执行地址
    */
    private String address;
    /**
    * 错误信息简略
    */
    private String messageHeader;
    /**
    * 错误信息
    */
    private String message;
    /**
    * 任务开始时间
    */
    private Date startTime;
    /**
    * 任务结束时间
    */
    private Date endTime;
    /**
    * 日志文件url
    */
    private String logUrl;
    
    private Date createTime;
    
    private Date updateTime;

}