package com.chuchen.ccrpc.bootstrap;

import com.chuchen.ccrpc.RpcApplication;
import com.chuchen.ccrpc.config.RegistryConfig;
import com.chuchen.ccrpc.config.RpcConfig;
import com.chuchen.ccrpc.model.ServiceMetaInfo;
import com.chuchen.ccrpc.model.ServiceRegisterInfo;
import com.chuchen.ccrpc.registry.LocalRegistry;
import com.chuchen.ccrpc.registry.Registry;
import com.chuchen.ccrpc.registry.RegistryFactory;
import com.chuchen.ccrpc.server.HttpServer;
import com.chuchen.ccrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author chuchen
 * @date 2025/5/7 16:32
 * @description 服务提供者初始化
 */
public class ProviderBootstrap {

    /**
     * 初始化
     * @param serviceRegisterInfoList 服务注册信息列表
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化
        RpcApplication.init();

        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 服务注册
        for(ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            // 注册服务
            String serviceName = serviceRegisterInfo.getServiceName();
            LocalRegistry.registry(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败" + e);
            }
        }

        // 启动 web 服务器
        HttpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
