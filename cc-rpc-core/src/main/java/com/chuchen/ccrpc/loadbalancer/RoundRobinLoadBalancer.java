package com.chuchen.ccrpc.loadbalancer;

import com.chuchen.ccrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chuchen
 * @date 2025/5/6 15:12
 * @description 轮询负载均衡器
 */
public class RoundRobinLoadBalancer implements LoadBalancer{

    /**
     * 当前轮训下标
     */
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()) {
            return null;
        }
        int size = serviceMetaInfoList.size();
        // 只有一个服务，无需轮询
        if(size == 1) {
            return serviceMetaInfoList.get(0);
        }
        // 取模算法轮询
        int index = currentIndex.getAndIncrement() % serviceMetaInfoList.size();
        return  serviceMetaInfoList.get(index);
    }
}
