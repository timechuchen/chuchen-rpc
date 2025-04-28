package com.chuchen.ccrpc.server;

import io.vertx.core.Vertx;

/**
 * @author chuchen
 * @date 2025/4/27 18:20
 * 基于 Vert.x 实现的Web服务器 VertxHttpServer
 */
public class VertxHttpServer implements HttpServer{

    @Override
    public void doStart(int port) {
        // 创建 vertx 对象
        Vertx vertx = Vertx.vertx();

        // 创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(request -> {
            // 处理 HTTP 请求
            System.out.println("收到请求：" + request.method() + " " + request.uri());

            // 发送 HTTP 响应
            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x server!");
        });

        // 启动 HTTP 服务并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("HTTP server running on port " + port);
            } else {
                System.out.println("Failed to start HTTP server: " + result.cause());
            }
        });
    }
}