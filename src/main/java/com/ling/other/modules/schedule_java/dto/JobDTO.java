package com.ling.other.modules.schedule_java.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhangling
 * @since 2020/12/25 10:45
 */
@Data
public class JobDTO {

    @ApiModelProperty("任务ID")
    private Integer jobId;

    @ApiModelProperty("bean名称")
    private String beanName;

    @ApiModelProperty("方法名称")
    private String methodName;

    @ApiModelProperty("方法参数")
    private String methodParams;

    @ApiModelProperty("cron表达式")
    private String cronExpression;

    @ApiModelProperty("执行日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date executeDate;

    @ApiModelProperty("状态（1正常 0暂停）")
    private Integer jobStatus;

    @ApiModelProperty("备注")
    private String remark;

    private Date createTime;

}
