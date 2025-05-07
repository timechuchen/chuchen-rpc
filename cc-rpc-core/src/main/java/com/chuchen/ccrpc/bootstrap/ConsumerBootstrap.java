package com.chuchen.ccrpc.bootstrap;

import com.chuchen.ccrpc.RpcApplication;

/**
 * @author chuchen
 * @date 2025/5/7 16:51
 * @description 消费者启动类
 */
public class ConsumerBootstrap {

    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
