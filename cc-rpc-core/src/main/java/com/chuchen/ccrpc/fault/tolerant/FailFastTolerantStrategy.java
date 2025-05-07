package com.chuchen.ccrpc.fault.tolerant;

import com.chuchen.ccrpc.model.RpcResponse;

import java.util.Map;

/**
 * @author chuchen
 * @date 2025/5/7 16:09
 * @description 快速失败 - 容错策略（立刻通知外层调用方法）
 */
public class FailFastTolerantStrategy implements TolerantStrategy{

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务错误：", e);
    }
}
