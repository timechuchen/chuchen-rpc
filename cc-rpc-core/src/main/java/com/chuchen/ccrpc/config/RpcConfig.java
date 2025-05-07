package com.chuchen.ccrpc.config;

import com.chuchen.ccrpc.fault.retry.RetryStrategyKeys;
import com.chuchen.ccrpc.loadbalancer.LoadBalancerKeys;
import com.chuchen.ccrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * @author chuchen
 * @date 2025/4/28 16:09
 * <br>
 */
@Data
public class RpcConfig {

    /**
     * 服务名称
     */
    private String name = "cc-rpc";

    /**
     * 服务版本
     */
    private String version = "1.0.0";

    /**
     * 服务主机
     */
    private String serverHost = "localhost";

    /**
     * 服务端口
     */
    private int serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     *  序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
}
