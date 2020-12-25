package com.ling.other.modules.scheduler.databoject;

import java.util.Date;
import java.io.Serializable;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * 执行器(Executor)实体类
 *  
 * @author zhangling
 * @since 2020-09-03 10:25:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "au_executor")
public class ExecutorDO implements Serializable {
    private static final long serialVersionUID = -70578813112373759L;

    @Id
    private Long executorId;
    /**
    * 执行器编码
    */
    private String executorCode;
    /**
    * 执行器名称
    */
    @Pattern(regexp = "^[A-Z0-9][A-Z0-9-_./]*$")
    private String executorName;
    /**
    * 排序
    */
    private Integer orderSeq;
    /**
    * 执行器地址类型：0=自动注册、1=手动录入
    */
    private Integer executorType;
    /**
    * 执行器地址列表，多地址逗号分隔
    */
    private String addressList;
    /**
    * 执行器状态
    */
    private String status;
    
    private Date createTime;
    
    private Date updateTime;

}