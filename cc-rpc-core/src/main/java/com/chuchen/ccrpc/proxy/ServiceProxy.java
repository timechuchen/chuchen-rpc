package com.chuchen.ccrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.chuchen.ccrpc.RpcApplication;
import com.chuchen.ccrpc.config.RpcConfig;
import com.chuchen.ccrpc.constant.RpcConstant;
import com.chuchen.ccrpc.fault.retry.RetryStrategy;
import com.chuchen.ccrpc.fault.retry.RetryStrategyFactory;
import com.chuchen.ccrpc.fault.tolerant.TolerantStrategy;
import com.chuchen.ccrpc.fault.tolerant.TolerantStrategyFactory;
import com.chuchen.ccrpc.loadbalancer.LoadBalancer;
import com.chuchen.ccrpc.loadbalancer.LoadBalancerFactory;
import com.chuchen.ccrpc.model.RpcRequest;
import com.chuchen.ccrpc.model.RpcResponse;
import com.chuchen.ccrpc.model.ServiceMetaInfo;
import com.chuchen.ccrpc.registry.Registry;
import com.chuchen.ccrpc.registry.RegistryFactory;
import com.chuchen.ccrpc.serializer.Serializer;
import com.chuchen.ccrpc.serializer.SerializerFactory;
import com.chuchen.ccrpc.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // 负载均衡算法来选择服务提供者
            LoadBalancer loadBalancer = LoadBalancerFactory.getLoadBalancer(rpcConfig.getLoadBalancer());
            // 将调用参数作为选取负载均衡的参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

//            // Http 协议 发送请求
//            try (HttpResponse httpResponse = HttpRequest.post(selectServiceMetaInfo.getServiceAddress())
//                    .body(bytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                RpcResponse response = serializer.deserialize(result, RpcResponse.class);
//                return response.getData();
//            }
            // 使用重试机制
            RpcResponse rpcResponse;

            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() -> VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo));
            }catch (Exception e) {
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            // TCP 协议 发送请求
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用服务失败");
        }
    }

    /**
     * 发送 HTTP 请求
     *
     * @param selectedServiceMetaInfo
     * @param bodyBytes
     * @return
     * @throws IOException
     */
    private static RpcResponse doHttpRequest(ServiceMetaInfo selectedServiceMetaInfo, byte[] bodyBytes) throws IOException {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 发送 HTTP 请求
        try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                .body(bodyBytes)
                .execute()) {
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse;
        }
    }
}
