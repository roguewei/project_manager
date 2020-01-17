package com.winston.service;

import com.winston.entity.Role;
import com.winston.result.Result;

import java.util.List;

public interface IRoleService {

    List<Role> queryAll(Role role);

    Result query(Role role, Integer page, Integer length);

    Role queryById(Integer id);

    List<Role> queryByIds(List<Integer> ids);

    Role queryByUserId(Integer userId);

    void addRole(Role role);

    void updateRole(Role role);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleid
     */
    public void delRole(Integer roleid);

}
