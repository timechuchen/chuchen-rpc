package com.chuchen.ccrpc.model;

import java.io.Serializable;

/**
 * @author chuchen
 * @date 2025/4/27 17:16
 */
public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
