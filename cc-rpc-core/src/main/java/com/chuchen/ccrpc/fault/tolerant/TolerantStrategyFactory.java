package com.chuchen.ccrpc.fault.tolerant;

import com.chuchen.ccrpc.spi.SpiLoader;

/**
 * @author chuchen
 * @date 2025/5/7 16:17
 * @description  容错策略工厂
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    public static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取容错策略
     * @param key key
     * @return TolerantStrategy
     */
    public static TolerantStrategy getInstance(String key) {
        return key == null ? DEFAULT_TOLERANT_STRATEGY : SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
