package com.chuchen.ccrpc.registry;

import com.chuchen.ccrpc.config.RegistryConfig;
import com.chuchen.ccrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author chuchen
 * @date 2025/4/29 14:53
 * <br>
 * 注册中心
 */
public interface Registry {

    /**
     * 初始化
     * @param registryConfig 配置信息
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     * @param serviceMetaInfo 服务信息
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务端所有节点，消费端）
     * @param serviceKey 服务名
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测，服务端
     */
    void heartBeat();

    /**
     * 监听（消费端）
     * @param serviceNodeKey 服务节点
     */
    void watch(String serviceNodeKey);
}
