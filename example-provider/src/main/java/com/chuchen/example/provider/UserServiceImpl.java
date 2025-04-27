package com.chuchen.example.provider;

import com.chuchen.example.model.User;
import com.chuchen.example.service.UserService;

/**
 * @author chuchen
 * @date 2025/4/27 17:18
 * @description 用户服务器类的实现
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
