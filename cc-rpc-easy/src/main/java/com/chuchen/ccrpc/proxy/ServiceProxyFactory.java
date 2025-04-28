package com.chuchen.ccrpc.proxy;

import java.lang.reflect.Proxy;

/**
 * @author chuchen
 * @date 2025/4/28 15:04
 * <br>
 * 服务代理工厂，用于创建代理对象：主要是通过 Proxy.newProxyInstance 方法指定类型创建代理对象
 */
public class ServiceProxyFactory {

    /**
     *
     * @param serviceClass 需要代理的类
     * @return 代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
