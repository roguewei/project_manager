package com.winston.service;

import com.winston.entity.Groups;
import com.winston.result.Result;

import java.util.List;

public interface IGroupsService {

    Long queryCount();

    Result query(Groups groups, Integer page, Integer length);

    List<Groups> queryAll();

    Groups queryById(Integer id);

    List<Groups> queryByUserId(Integer userId);

    List<Groups> queryByIds(List<Integer> ids);

    void addGroups(Groups groups);

    void updateGroups(Groups groups);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param groupsid
     */
    public void delGroups(Integer groupsid);
    
}
