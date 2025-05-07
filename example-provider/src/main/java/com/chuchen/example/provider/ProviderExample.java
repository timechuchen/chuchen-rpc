package com.chuchen.example.provider;

import com.chuchen.ccrpc.bootstrap.ProviderBootstrap;
import com.chuchen.ccrpc.model.ServiceRegisterInfo;
import com.chuchen.common.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chuchen
 * @date 2025/4/27 17:22
 * @description 简单服务提供者示例
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
