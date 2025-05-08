package com.chuchen.example.consumer;

import com.chuchen.common.model.User;
import com.chuchen.ccrpc.proxy.ServiceProxyFactory;
import com.chuchen.common.service.UserService;

/**
 * @author chuchen
 * @date 2025/4/27 17:25
 * @description 简答服务消费者示例
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 调用静态代理的方式
//        UserServiceProxy userService = new UserServiceProxy();
        // 动态代理的方式
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        User user = new User();
        user.setName("chuchen");

        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
