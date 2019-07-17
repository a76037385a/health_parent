package com.app01.service;

import com.app01.entity.Result;
import com.app01.pojo.User;

public interface UserService {

    User findUserByUsername(String username);


}
