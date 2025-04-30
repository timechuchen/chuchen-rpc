package com.chuchen.ccrpc.registry;

import com.chuchen.ccrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author chuchen
 * @date 2025/4/30 09:06
 * <br>
 * 注册中心服务本地缓存
 */
public class RegistryServiceCache {

    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     */
    void writeCache(List<ServiceMetaInfo> serviceCache) {
        this.serviceCache = serviceCache;
    }

    /**
     * 读缓存
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
