package com.ling.other.modules.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author zhangling
 * @since 2020/12/25 13:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobInfoDTO {

    @ApiModelProperty("JobId")
    private Long jobId;

    @ApiModelProperty("执行器ID,hsdr_executor.executor_id")
    private Long executorId;

    @Transient
    @ApiModelProperty("执行器编码")
    private String executorCode;

    @ApiModelProperty("任务编码")
    private String jobCode;

    @ApiModelProperty("任务执行cron")
    private String jobCron;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("任务执行cron日期")
    private Date jobCronDate;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("任务状态：1正常，0暂停")
    private Integer jobStatus;

    @ApiModelProperty("执行器任务参数")
    private String jobParam;

    @ApiModelProperty("执行器策略，HSDR.EXECUTOR_STRATEGY")
    private String executorStrategy;

    @ApiModelProperty("失败处理策略，HSDR.FAIL_STRATEGY")
    private String failStrategy;

    @ApiModelProperty("任务类型，HSDR.GLUE_TYPE")
    private String glueType;

    @ApiModelProperty("jobHandler")
    private String jobHandler;

    @ApiModelProperty("周期性，1周期  0非周期")
    private Integer cycleFlag;

    @ApiModelProperty("有效时间从")
    private Date startDate;

    @ApiModelProperty("有效时间至")
    private Date endDate;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("策略参数")
    private String strategyParam;
}
