package com.chuchen.ccrpc.springboot.starter.annotation;

import com.chuchen.ccrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.chuchen.ccrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.chuchen.ccrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chuchen
 * @date 2025/5/7 17:03
 * @description 启动 RPC 框架注解
 * <br>
 * 由于服务消费者和服务提供者的初始化模块不同，所以需要在 EnableRpc 注解中指定启动的服务器属性等
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcConsumerBootstrap.class, RpcProviderBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 server
     */
    boolean needServer() default true;
}
