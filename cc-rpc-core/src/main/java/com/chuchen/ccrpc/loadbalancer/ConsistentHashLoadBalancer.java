package com.chuchen.ccrpc.loadbalancer;

import com.chuchen.ccrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author chuchen
 * @date 2025/5/6 15:20
 * @description 一致性 hash 负载均衡器
 */
public class ConsistentHashLoadBalancer implements LoadBalancer{

    /**
     * 一致性哈希环，存放虚拟节点
     */
    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();
    /**
     * 虚拟节点个数
     */
    private static final int VIRTUAL_NODES = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()) {
            return null;
        }
        // 构建虚拟节点
        for(ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for(int i = 0; i < VIRTUAL_NODES; i++) {
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }
        // 获取请求调用的哈希值
        int hash = getHash(requestParams);
        // 从哈希环中找到最近且大于等于 hash 值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        // 如果没有找到大于等于 hash 值的虚拟节点，就返回第一个虚拟节点
        if(entry == null) {
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    /**
     * hash 算法，后续可自己实现，这里只是简单的调用 Object 的 hashCode 方法
     * @param key key
     * @return hash 值
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
