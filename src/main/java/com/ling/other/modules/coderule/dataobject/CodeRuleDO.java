package com.ling.other.modules.coderule.dataobject;

import java.util.Date;
import java.io.Serializable;
import lombok.*;

/**
 * 编码规则头(CodeRule)实体类
 *  
 * @author zhangling
 * @since 2020-09-28 14:32:16
 */
@Data
@Builder
public class CodeRuleDO implements Serializable {
    private static final long serialVersionUID = 647650039291033009L;
    
    private Long ruleId;
    /**
    * 编码规则code
    */
    private String ruleCode;
    /**
    * 编码规则名
    */
    private String ruleName;
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