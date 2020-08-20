package com.ling.other.service.impl;

import com.ling.other.common.exception.RrException;
import com.ling.other.entity.SupplierUser;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.service.SupplierUserService;
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
    public void create(SupplierUser supplierUser) {
        supplierUserMapper.insertSelective(supplierUser);
    }

    @Override
    public void test() {
        System.out.println("hello");
        int i = 1;
        if(i == 1){
            throw new RrException("异常测试");
        }
    }

    @Override
    public List<SupplierUser> list() {

        List<SupplierUser> supplierUsers = supplierUserMapper.selectAll();
        return supplierUsers;
    }
}