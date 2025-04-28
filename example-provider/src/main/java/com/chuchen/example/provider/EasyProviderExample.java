package com.chuchen.example.provider;

import com.chuchen.ccrpc.registry.LocalRegistry;
import com.chuchen.ccrpc.server.HttpServer;
import com.chuchen.ccrpc.server.VertxHttpServer;
import com.chuchen.common.service.UserService;

/**
 * @author chuchen
 * @date 2025/4/27 17:22
 * @description 简单服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        LocalRegistry.registry(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务器
        HttpServer httpServer = new VertxHttpServer();

        httpServer.doStart(8080);
    }
}
