package com.chuchen.example.consumer;

import com.chuchen.ccrpc.config.RpcConfig;
import com.chuchen.ccrpc.utils.ConfigUtils;

/**
 * @author chuchen
 * @date 2025/4/28 16:49
 * <br>
 * 简单消费者服务示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpcConfig);
    }
}
