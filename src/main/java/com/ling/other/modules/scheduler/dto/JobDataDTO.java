package com.ling.other.modules.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 调度任务数据传输对象
 * @author zhangling
 * @since 2020/12/28 15:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDataDTO {

    private Long jobId;
    private Long logId;
    private String jobCode;
    private String jobType;
    private String jobHandler;
    private String param;
}
