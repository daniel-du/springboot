package com.daniel.test.springboot.entity;

import java.io.Serializable;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/7/4 12:07
 */
public class Member implements Serializable{
    private static final long serialVersionUID = 1L;

    private long id;
    private String loginName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
