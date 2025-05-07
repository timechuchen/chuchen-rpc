package com.chuchen.ccrpc.fault.retry;

import com.chuchen.ccrpc.model.RpcResponse;
import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author chuchen
 * @date 2025/5/7 15:08
 * @description 固定间隔重试 - 重试策略
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy{

    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws ExecutionException, RetryException {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试第{}次", attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }
}
