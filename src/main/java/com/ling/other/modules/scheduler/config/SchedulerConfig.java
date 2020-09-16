package com.ling.other.modules.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangling
 * @since 2020/9/14 15:19
 */
@Component
public class SchedulerConfig {

    static final String PREFIX = "hzero.scheduler";
    private String executorCode;
    private boolean autoRegister = true;
    private boolean uploadLog = true;
    private Integer retry = 60;
    private Integer retryTime = 5;
    private Integer stopRetryTime = 5;
    private Integer maxPoolSize = 30;
    private Integer corePoolSize = 5;

    public SchedulerConfig() {
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public String getExecutorCode() {
        return executorCode;
    }

    public void setExecutorCode(String executorCode) {
        this.executorCode = executorCode;
    }

    public boolean isAutoRegister() {
        return autoRegister;
    }

    public void setAutoRegister(boolean autoRegister) {
        this.autoRegister = autoRegister;
    }

    public boolean isUploadLog() {
        return uploadLog;
    }

    public void setUploadLog(boolean uploadLog) {
        this.uploadLog = uploadLog;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public Integer getStopRetryTime() {
        return stopRetryTime;
    }

    public void setStopRetryTime(Integer stopRetryTime) {
        this.stopRetryTime = stopRetryTime;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}
