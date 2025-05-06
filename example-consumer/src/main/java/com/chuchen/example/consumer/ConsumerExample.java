package com.chuchen.example.consumer;

import com.chuchen.ccrpc.proxy.ServiceProxyFactory;
import com.chuchen.common.model.User;
import com.chuchen.common.service.UserService;

/**
 * @author chuchen
 * @date 2025/4/28 16:49
 * <br>
 * 简单消费者服务示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
//        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpcConfig);

        // 获取动态代理类
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("chuchen");
        // 调用远程服务
        User newUser = userService.getUser(user);
        System.out.println(newUser);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
//        long number = userService.getNumber();
//        System.out.println(number);
    }
}
