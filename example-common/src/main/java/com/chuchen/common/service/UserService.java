package com.chuchen.common.service;

import com.chuchen.common.model.User;

/**
 * @author chuchen
 * @date 2025/4/27 17:16
 */
public interface UserService {

    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 获取数字
     */
    default short getNumber() {
        return 1;
    }
}
