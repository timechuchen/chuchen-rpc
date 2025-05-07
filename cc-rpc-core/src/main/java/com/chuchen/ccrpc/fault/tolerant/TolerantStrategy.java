package com.chuchen.ccrpc.fault.tolerant;

import com.chuchen.ccrpc.model.RpcResponse;

import java.util.Map;

/**
 * @author chuchen
 * @date 2025/5/7 16:06
 * @description 容错策略
 */
public interface TolerantStrategy {

    /**
     * 容错
     * @param context 上下文，用于传递数据
     * @param e 异常
     * @return 响应
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
