package com.chuchen.ccrpc.loadbalancer;

import com.chuchen.ccrpc.spi.SpiLoader;

/**
 * @author chuchen
 * @date 2025/5/6 15:32
 * @description 负载均衡器工厂，支持 key 从 SPI 中获取
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 根据 key 获取负载均衡器
     * @param key 一般是配置文件中的参数
     * @return LoadBalancer
     */
    public static LoadBalancer getLoadBalancer(String key) {
        LoadBalancer loadBalancer = SpiLoader.getInstance(LoadBalancer.class, key);
        return loadBalancer == null ? DEFAULT_LOAD_BALANCER : loadBalancer;
    }
}
