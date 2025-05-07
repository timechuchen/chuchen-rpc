package com.chuchen.examplespringbootconsumer;

import com.chuchen.ccrpc.springboot.starter.annotation.RpcReference;
import com.chuchen.ccrpc.springboot.starter.annotation.RpcService;
import com.chuchen.common.model.User;
import com.chuchen.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author chuchen
 * @date 2025/5/7 18:22
 */
@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("chuchen");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
