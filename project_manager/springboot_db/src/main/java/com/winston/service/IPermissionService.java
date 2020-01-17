package com.winston.service;

import com.winston.entity.Permission;
import com.winston.result.Result;

import java.util.List;
import java.util.Map;

public interface IPermissionService {

    Result query(Permission permission, Integer page, Integer length);

    List<Permission> queryAll();

    List<Permission> queryParant();

    Permission queryById(Integer id);

    // 根据用户名查询拥有的权限（包括在组内的权限）
    List<Permission> queryByUserName(String username);

    List<Permission> queryHaveNot(String username);

    List<Permission> queryByRoleId(Integer roleId);

    List<Permission> queryByGroupId(Integer groupId);

    void addAllUrl(Permission permission);

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    void delPermission(Integer id);

    public List<Map<String, Object>> setPer(List<Permission> permissions);

}
