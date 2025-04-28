package com.chuchen.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.chuchen.ccrpc.model.RpcRequest;
import com.chuchen.ccrpc.model.RpcResponse;
import com.chuchen.common.model.User;
import com.chuchen.ccrpc.serializer.JdkSerializer;
import com.chuchen.ccrpc.serializer.Serializer;
import com.chuchen.common.service.UserService;

import java.io.IOException;

/**
 * @author chuchen
 * @date 2025/4/28 14:26
 * <br/>
 * 静态代理：
 * 这是使用静态代理的方式实现一个UserService的代理类
 */
public class UserServiceProxy {

    public User getUser(User user) {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 发送请求
        RpcRequest request = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try{
            byte[] bytes = serializer.Serializer(request);
            byte[] result;
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse response = serializer.Deserializer(result, RpcResponse.class);
            return (User) response.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
