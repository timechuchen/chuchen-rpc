package com.chuchen.ccrpc.registry;

import com.chuchen.ccrpc.spi.SpiLoader;

/**
 * @author chuchen
 * @date 2025/4/29 15:26
 * <br>
 * 注册中心工厂，用于获取注册中心对象
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     * @param key 注册中心名称
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
