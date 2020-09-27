package com.ling.other.modules.user.service;

import com.ling.other.modules.user.entity.SupplierUser;
import java.util.List;

/**
 * 供应商用户表(SupplierUser)表服务接口
 *
 * @author makejava
 * @since 2020-08-10 14:14:20
 */
public interface SupplierUserService {


    /**
     * 新建
     * @param supplierUser
     */
    void create(SupplierUser supplierUser);

    void test();

    List<SupplierUser> list();
}