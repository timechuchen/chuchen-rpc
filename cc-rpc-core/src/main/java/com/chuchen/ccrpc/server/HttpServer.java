package com.chuchen.ccrpc.server;

/**
 * @author chuchen
 * @date 2025/4/27 18:17
 * @description HTTP 服务器接口，编写一个 Web 服务器的统一接口，便于后续的拓展，比如用不同的web服务器来实现
 */
public interface HttpServer {

    /**
     * 启动服务器
     * @param port 启动的端口号
     */
    void doStart(int port);
}
