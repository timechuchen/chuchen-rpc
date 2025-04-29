package com.chuchen.ccrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.chuchen.ccrpc.RpcApplication;
import com.chuchen.ccrpc.model.RpcRequest;
import com.chuchen.ccrpc.model.RpcResponse;
import com.chuchen.ccrpc.serializer.Serializer;
import com.chuchen.ccrpc.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chuchen
 * @date 2025/4/28 14:52
 * <br/>
 * 服务代理（JDK 动态代理）：需要实现 InvocationHandler 接口的 invoke 方法
 * <br/>
 * 当用户调用某个方法的接口的时候，会改为调用 invoke 方法。在 invoke 方法中，我们需要的参数是
 * 调用的方法信息、传入的参数列表等，这就是服务提供者需要的参数
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            byte[] bytes = serializer.Serializer(rpcRequest);
            byte[] result;

            //发送请求
            //TODO 注意；这里硬编码了。需要后面使用服务的注册中心和服务发现机制来解决
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
                RpcResponse response = serializer.Deserializer(result, RpcResponse.class);
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
