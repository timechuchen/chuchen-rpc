package com.chuchen.ccrpc.springboot.starter.annotation;

import com.chuchen.ccrpc.constant.RpcConstant;
import com.chuchen.ccrpc.fault.retry.RetryStrategyKeys;
import com.chuchen.ccrpc.fault.tolerant.TolerantStrategyKeys;
import com.chuchen.ccrpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chuchen
 * @date 2025/5/7 17:13
 * @description 服务消费者注解（用于注入服务）。在需要注入的服务代理对象的属性上使用，类似于 Spring 的 @Resource 注解。
 * <br/>
 * RpcReference 注解需要指定调用服务的相关属性，比如服务接口类（可能存在多个接口）、服务版本号、负载均衡器、重试策略、是否 mock 模拟等。
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {

    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    String retryStrategy() default RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     */
    boolean mock() default false;
}
