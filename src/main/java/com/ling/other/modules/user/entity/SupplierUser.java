package com.ling.other.modules.user.entity;

import com.ling.other.modules.lov.annotation.LovValue;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.io.Serializable;

/**
 * 供应商用户表(AuSupplierUser)实体类
 *
 * @author makejava
 * @since 2020-08-10 14:04:26
 */
@Data
@Table(name = "au_supplier_user")
public class SupplierUser implements Serializable {
    private static final long serialVersionUID = -80566553887229917L;
    /**
    * 主键id
    */
    @Id
    //@GeneratedValue
    private Integer userId;
    /**
    * 用户名
    */
    private String username;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 密码
    */
    private String password;
    /**
    * 公司id
    */
    private Integer companyId;
    /**
    * token值，用户登录凭证
    */
    private String token;
    /**
    * 创建更新token时间
    */
    private String tokenTime;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 父id
    */
    private Integer parentId;
    /**
    * 启动标识，1启用，0禁用
    */
    @LovValue("FLAG")
    private Integer enabledFlag;

    @Transient
    private String enabledFlagMeaning;



}