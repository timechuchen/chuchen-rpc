package com.chuchen.ccrpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chuchen
 * @date 2025/5/7 16:33
 * @description 服务注册信息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterInfo<T> {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务实现类
     */
    private Class<? extends T> implClass;
}
