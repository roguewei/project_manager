package com.winston.service;


import com.winston.entity.User;
import com.winston.result.Result;

import java.util.List;

public interface IUserService {

    Long queryCount();

    List<User> queryAll();

    Result queryByUser(User user, int page, int length);

    User queryById(Integer id);

    List<User> queryByIds(List<Integer> ids);

    User queryByUsername(String username);

    Result queryByGroupId(Integer groupId, Integer page, Integer length);

    User queryByOpenId(String openId);

    int save(User user);

    void update(User user);

    void updatePwd(User user);

    void delete(Integer id);
}
