package cn.xzl.service;

import cn.xzl.domain.User;

/*
* 
* 
* */
public interface Userservice {

    public User findAccountByName(String username);
}
