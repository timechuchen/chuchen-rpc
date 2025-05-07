package com.chuchen.ccrpc.fault.retry;

import com.chuchen.ccrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author chuchen
 * @date 2025/5/7 15:06
 * @description 不重试 - 重试策略
 */
public class NoRetryStrategy implements RetryStrategy{

    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
