package com.chuchen.ccrpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chuchen
 * @date 2025/4/28 10:45
 * 本地注册中心
 */
public class LocalRegistry {

    /**
     * 注册信息的存储
     */
    private static final Map<String, Class<?>> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * 注册服务
     *
     * @param serviceName 服务名称
     * @param serviceClass 服务的实现类
     */
    public static void registry(String serviceName, Class<?> serviceClass) {
        SERVICE_MAP.put(serviceName, serviceClass);
    }

    /**
     * 获取服务
     * @param serviceName 服务名称
     * @return 服务实现类
     */
    public static Class<?> getService(String serviceName) {
        return SERVICE_MAP.get(serviceName);
    }
}
