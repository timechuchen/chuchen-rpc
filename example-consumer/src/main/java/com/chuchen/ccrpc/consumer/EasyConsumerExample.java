package com.chuchen.ccrpc.consumer;

import com.chuchen.ccrpc.model.User;
import com.chuchen.ccrpc.service.UserService;

/**
 * @author chuchen
 * @date 2025/4/27 17:25
 * @description 简答服务消费者示例
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService = null;
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
