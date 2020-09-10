package com.ling.other.modules.scheduler.databoject;

import java.util.Date;
import java.io.Serializable;
import lombok.*;

import javax.persistence.*;

/**
 * 调度任务(JobInfo)实体类
 *  
 * @author zhangling
 * @since 2020-09-03 09:50:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "au_job_info")
public class JobInfoDO implements Serializable {
    private static final long serialVersionUID = -29699160721442410L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    /**
    * 执行器ID,hsdr_executor.executor_id
    */
    private Long executorId;

    @Transient
    private String executorCode;
    /**
    * 任务编码
    */
    private String jobCode;
    /**
    * 任务执行corn
    */
    private String jobCron;
    /**
    * 任务描述
    */
    private String description;
    /**
    * 执行器任务参数
    */
    private String jobParam;
    /**
    * 执行器策略，HSDR.EXECUTOR_STRATEGY
    */
    private String executorStrategy;
    /**
    * 失败处理策略，HSDR.FAIL_STRATEGY
    */
    private String failStrategy;
    /**
    * 任务类型，HSDR.GLUE_TYPE
    */
    private String glueType;
    /**
    * jobHandler
    */
    private String jobHandler;
    /**
    * 周期性，1周期  0非周期
    */
    private Integer cycleFlag;
    /**
    * 有效时间从
    */
    private Date startDate;
    /**
    * 有效时间至
    */
    private Date endDate;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 策略参数
    */
    private String strategyParam;

}