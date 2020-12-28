package com.ling.other.modules.scheduler.dto;

/**
 * 任务进度信息
 *
 * @author zhangling 2020/12/28 16:01
 */
public class JobProgress {

    /**
     * 任务进度
     */
    private Integer progress;
    /**
     * 描述信息
     */
    private String message;

    public Integer getProgress() {
        return progress;
    }

    public JobProgress setProgress(Integer progress) {
        this.progress = progress;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JobProgress setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "JobProgress{" +
                "progress=" + progress +
                ", message='" + message + '\'' +
                '}';
    }
}