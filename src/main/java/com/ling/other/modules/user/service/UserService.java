package com.ling.other.modules.user.service;

import com.ling.other.modules.user.dto.User;

/**
 * @author zhangling
 * @since 2020/12/9 14:00
 */
public interface UserService {

    void createUser(User user);

    void create(User user);
}
