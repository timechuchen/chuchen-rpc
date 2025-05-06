package com.chuchen.ccrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.chuchen.ccrpc.RpcApplication;
import com.chuchen.ccrpc.config.RpcConfig;
import com.chuchen.ccrpc.constant.RpcConstant;
import com.chuchen.ccrpc.model.RpcRequest;
import com.chuchen.ccrpc.model.RpcResponse;
import com.chuchen.ccrpc.model.ServiceMetaInfo;
import com.chuchen.ccrpc.registry.Registry;
import com.chuchen.ccrpc.registry.RegistryFactory;
import com.chuchen.ccrpc.serializer.Serializer;
import com.chuchen.ccrpc.serializer.SerializerFactory;
import com.chuchen.ccrpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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
    public Object invoke(Object proxy, Method method, Object[] args) {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            // 从注册中心获取服务提供者的请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if(CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务提供者");
            }
            // TODO 这里暂时先获取第一个服务就行，之后需要使用负载均衡算法来选择服务提供者
            ServiceMetaInfo selectServiceMetaInfo = serviceMetaInfoList.get(0);

//            // Http 协议 发送请求
//            try (HttpResponse httpResponse = HttpRequest.post(selectServiceMetaInfo.getServiceAddress())
//                    .body(bytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                RpcResponse response = serializer.deserialize(result, RpcResponse.class);
//                return response.getData();
//            }
            // TCP 协议 发送请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo);
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用服务失败");
        }
    }
}
