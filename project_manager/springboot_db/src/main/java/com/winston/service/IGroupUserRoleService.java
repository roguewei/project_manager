package com.winston.service;

import com.winston.entity.GroupUserRole;
import com.winston.entity.GroupUserRoleDTO;

import java.util.List;

public interface IGroupUserRoleService {

    List<GroupUserRole> queryByUserId(Integer userId);

    List<GroupUserRole> queryByUserIdNotGroupId(Integer userId);

    List<GroupUserRole> queryByGroupId(Integer groupId);

    List<GroupUserRole> queryByUserIdNotRoleId(Integer userId);

    void save(GroupUserRoleDTO gur);

    void insert(GroupUserRole gur);

    void delByUserIdNotRoleId(Integer userId);

    void delByUserIdNotGroupId(Integer userId);

    void delByRoleId(Integer roleId);

    void delByGroupId(Integer groupId);

}
