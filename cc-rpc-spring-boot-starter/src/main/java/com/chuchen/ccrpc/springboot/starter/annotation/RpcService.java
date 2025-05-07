package com.chuchen.ccrpc.springboot.starter.annotation;

import com.chuchen.ccrpc.constant.RpcConstant;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chuchen
 * @date 2025/5/7 17:06
 * @description 服务提供者注解（用于注册服务）
 * <br>
 * RpcService 注解中，需要指定服务注册信息属性，比如接口实现类，版本号等
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 服务版本号
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
}
