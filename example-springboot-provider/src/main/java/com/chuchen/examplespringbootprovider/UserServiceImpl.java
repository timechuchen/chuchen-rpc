package com.chuchen.examplespringbootprovider;

import com.chuchen.common.model.User;
import com.chuchen.common.service.UserService;

/**
 * @author chuchen
 * @date 2025/5/7 18:20
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名" + user.getName());
        return user;
    }
}
