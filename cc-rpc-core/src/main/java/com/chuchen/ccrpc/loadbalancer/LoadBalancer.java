package com.chuchen.ccrpc.loadbalancer;

import com.chuchen.ccrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author chuchen
 * @date 2025/5/6 15:06
 * @description 负载均衡器，消费者使用
 */
public interface LoadBalancer {

    /**
     * 选择服务提供者
     * @param requestParams 请求参数
     * @param serviceMetaInfoList 服务提供者列表
     * @return 选择出来的服务提供者
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
