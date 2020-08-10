package com.ling.other.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 供应商用户表(AuSupplierUser)实体类
 *
 * @author makejava
 * @since 2020-08-10 14:04:26
 */

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
    private Integer enabledFlag;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(String tokenTime) {
        this.tokenTime = tokenTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

}