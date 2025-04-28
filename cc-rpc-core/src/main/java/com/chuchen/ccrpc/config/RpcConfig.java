package com.chuchen.ccrpc.config;

import lombok.Data;

/**
 * @author chuchen
 * @date 2025/4/28 16:09
 * <br>
 */
@Data
public class RpcConfig {

    /**
     * 服务名称
     */
    private String name = "cc-rpc";

    /**
     * 服务版本
     */
    private String version = "1.0.0";

    /**
     * 服务主机
     */
    private String serverHost = "localhost";

    /**
     * 服务端口
     */
    private int serverPort = 8080;
}
