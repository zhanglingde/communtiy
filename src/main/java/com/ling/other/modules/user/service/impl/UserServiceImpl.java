package com.ling.other.modules.user.service.impl;

import com.ling.other.common.exception.RrException;
import com.ling.other.mapper.SupplierUserMapper;
import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangling
 * @since 2020/12/9 14:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SupplierUserMapper supplierUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) {

        for(int i=1;i<10;i++){
            create(user);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User user) {
        for(int i=1;i<10;i++){
            user.setUsername(String.valueOf(i));
            if(i == 5){
                throw new RrException("异常");
            }
        }

        supplierUserMapper.insertUser(user);
    }
}
