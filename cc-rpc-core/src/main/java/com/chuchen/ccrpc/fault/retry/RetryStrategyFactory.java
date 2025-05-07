package com.chuchen.ccrpc.fault.retry;

import com.chuchen.ccrpc.spi.SpiLoader;

/**
 * @author chuchen
 * @date 2025/5/7 15:21
 * @description 重试策略工厂
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    public static RetryStrategy getInstance(String key) {
        return key == null ? DEFAULT_RETRY_STRATEGY : SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
