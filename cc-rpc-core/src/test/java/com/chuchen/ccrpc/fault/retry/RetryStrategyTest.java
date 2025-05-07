package com.chuchen.ccrpc.fault.retry;

import com.chuchen.ccrpc.model.RpcResponse;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author chuchen
 * @date 2025/5/7 15:14
 */
public class RetryStrategyTest extends TestCase {

    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    public void testDoRetry() {
        try {
            RpcResponse response = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模式重试失败");
            });
            System.out.println(response);
        }catch (Exception e){
            System.out.println("多次重试失败");
            e.printStackTrace();
        }
    }
}