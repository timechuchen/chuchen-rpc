package com.chuchen.ccrpc.fault.retry;

/**
 * @author chuchen
 * @date 2025/5/7 15:19
 * @description 重试策略键名常量
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间重试
     */
    String FIXED_INTERVAL = "fixedInterval";
}
