package com.ling.other.modules.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 任务日志传输对象
 *
 * @author zhangling 2020/12/28 15:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobLogDTO {

    private Long logId;
    private Long jobId;
    private String clientResult;
    private String messageHeader;
    private String message;
    private Date endTime;
    private String logUrl;
}
