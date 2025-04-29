package com.chuchen.ccrpc;

import com.chuchen.ccrpc.config.RegistryConfig;
import com.chuchen.ccrpc.config.RpcConfig;
import com.chuchen.ccrpc.constant.RpcConstant;
import com.chuchen.ccrpc.registry.Registry;
import com.chuchen.ccrpc.registry.RegistryFactory;
import com.chuchen.ccrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chuchen
 * @date 2025/4/28 16:31
 * <br>
 * RPC 框架应用
 * <br>
 * 相当于 holder，存放了项目全局用到的变量。
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持自定义配置
     * @param newRpcConfig 自定义配置类
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config: {}", rpcConfig);
        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config: {}", registryConfig);
    }

    /**
     * 初始化，从配置文件中获取
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            // 配置文件加载失败，使用默认的配置
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置：使用双检测单例模式实现
     */
    public static RpcConfig getRpcConfig() {
        if(rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if(rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
