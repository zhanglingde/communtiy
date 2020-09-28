package com.ling.other.modules.coderule.dataobject;

import java.util.Date;
import java.io.Serializable;
import lombok.*;

/**
 * 编码规则分配(CodeRuleDist)实体类
 *  
 * @author zhangling
 * @since 2020-09-28 14:55:06
 */
@Data
@Builder
public class CodeRuleDistDO implements Serializable {
    private static final long serialVersionUID = 534775488252478763L;
    
    private Long ruleDistId;
    
    private Long ruleId;
    /**
    * 租户级下的应用层级
    */
    private String levelCode;
    /**
    * 应用层级值
    */
    private String levelValue;
    /**
    *  是否启用
    */
    private Integer enabledFlag;
    /**
    * 描述
    */
    private String description;
    /**
    * 创建日期
    */
    private Date createTime;
    /**
    * 最后更新日期
    */
    private Date updateTime;

}