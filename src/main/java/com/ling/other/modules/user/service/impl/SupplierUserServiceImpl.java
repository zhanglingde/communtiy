package com.ling.other.modules.user.service.impl;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ling.other.common.exception.RrException;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.entity.SupplierUser;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.modules.user.service.SupplierUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商用户表(SupplierUser)表服务实现类
 *
 * @author makejava
 * @since 2020-08-10 14:14:22
 */
@Service("supplierUserService")
public class SupplierUserServiceImpl implements SupplierUserService {

    @Autowired
    private SupplierUserMapper supplierUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSupplierUser(SupplierUser supplierUser) {
        supplierUserMapper.insertSelective(supplierUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) {
        supplierUserMapper.insertUser(user);
        System.out.println("创建用户");
        //int i = 5/0;
    }

}