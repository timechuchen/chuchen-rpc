package com.chuchen.ccrpc.fault.retry;

import com.chuchen.ccrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author chuchen
 * @date 2025/5/7 15:02
 * @description 重试策略
 */
public interface RetryStrategy {

    /**
     * 重试
     * @param callable 重试的响应任务
     * @return 响应结果
     * @throws Exception 异常
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
