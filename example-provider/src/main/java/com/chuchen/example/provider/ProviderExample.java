package com.chuchen.example.provider;

import com.chuchen.ccrpc.RpcApplication;
import com.chuchen.ccrpc.config.RegistryConfig;
import com.chuchen.ccrpc.config.RpcConfig;
import com.chuchen.ccrpc.model.ServiceMetaInfo;
import com.chuchen.ccrpc.registry.LocalRegistry;
import com.chuchen.ccrpc.registry.Registry;
import com.chuchen.ccrpc.registry.RegistryFactory;
import com.chuchen.ccrpc.server.HttpServer;
import com.chuchen.ccrpc.server.VertxHttpServer;
import com.chuchen.ccrpc.server.tcp.VertxTcpServer;
import com.chuchen.common.service.UserService;

/**
 * @author chuchen
 * @date 2025/4/27 17:22
 * @description 简单服务提供者示例
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.registry(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务器
//        HttpServer vertxHttpServer = new VertxHttpServer();
        HttpServer vertxTcpServer = new VertxTcpServer();

        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
