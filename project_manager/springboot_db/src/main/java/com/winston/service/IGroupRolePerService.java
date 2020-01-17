package com.winston.service;

import com.winston.entity.GroupRolePermission;
import com.winston.entity.GroupRolePermissionDTO;

import java.util.List;

public interface IGroupRolePerService {

    List<GroupRolePermission> queryByRoleIds(List<Integer> roleIds);

    List<GroupRolePermission> queryByRoleId(Integer roleId);

    List<GroupRolePermission> queryByGroupIds(List<Integer> groupIds);

    List<GroupRolePermission> queryByGroupId(Integer groupId);

    void save(GroupRolePermissionDTO dto);

    void delByRoleId(Integer roleId);

    void delByPerId(Integer perId);

    void delByGroupId(Integer groupId);
}
