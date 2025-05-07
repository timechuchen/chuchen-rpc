package com.chuchen.examplespringbootprovider;

import com.chuchen.ccrpc.springboot.starter.annotation.RpcService;
import com.chuchen.common.model.User;
import com.chuchen.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author chuchen
 * @date 2025/5/7 18:20
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名" + user.getName());
        return user;
    }
}
