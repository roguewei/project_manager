package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.GroupUserRole;
import com.winston.entity.Groups;
import com.winston.entity.GroupsExample;
import com.winston.mapper.GroupsMapper;
import com.winston.redis.RedisService;
import com.winston.redis.key.GroupKey;
import com.winston.result.Result;
import com.winston.service.IGroupRolePerService;
import com.winston.service.IGroupUserRoleService;
import com.winston.service.IGroupsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GroupServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/30 16:21
 * @Version：
 */
@Service("groupsServiceBase")
public class GroupsServiceBaseImpl implements IGroupsService {

    @Autowired
    private GroupsMapper mapper;

    @Autowired
    private IGroupUserRoleService groupUserRoleServiceBase;

    @Autowired
    private IGroupRolePerService groupRolePerServiceBase;

    @Autowired
    private RedisService redisService;

    @Override
    public Long queryCount() {
        return mapper.countByExample(new GroupsExample());
    }

    @Override
    public Result query(Groups groups, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        GroupsExample example = new GroupsExample();
        GroupsExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(groups.getGroupName())){
            criteria.andGroupNameLike("%"+groups.getGroupName()+"%");
        }
        List<Groups> result = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(result);
        return Result.success(result, pageInfo.getTotal());
    }

    @Override
    public List<Groups> queryAll() {
        return mapper.selectByExample(new GroupsExample());
    }

    @Override
    public Groups queryById(Integer id) {
        Groups redisData = redisService.get(GroupKey.GROUP_ID_KEY, id + "", Groups.class);
        if(redisData != null){
            return redisData;
        }
        Groups groups = mapper.selectByPrimaryKey(id);
        if(groups != null){
            redisService.set(GroupKey.GROUP_ID_KEY, id+"", groups);
            return groups;
        } else{
            return new Groups();
        }
    }

    @Override
    public List<Groups> queryByUserId(Integer userId) {
        List<GroupUserRole> groupUserRoles = groupUserRoleServiceBase.queryByUserIdNotRoleId(userId);
        List<Groups> result = new ArrayList<>();
        if(groupUserRoles != null && groupUserRoles.size() > 0){
            for(GroupUserRole gur : groupUserRoles){
                result.add(queryById(gur.getGroupId()));
            }
        }
        return result;
    }

    @Override
    public List<Groups> queryByIds(List<Integer> ids) {
        GroupsExample example = new GroupsExample();
        example.createCriteria().andIdIn(ids);
        return mapper.selectByExample(example);
    }

    @Override
    public void addGroups(Groups groups) {
        mapper.insertSelective(groups);
    }

    @Override
    public void updateGroups(Groups groups) {
        redisService.del(GroupKey.GROUP_ID_KEY, groups.getId()+"");
        mapper.updateByPrimaryKeySelective(groups);
    }

    @Override
    public void delGroups(Integer groupid) {
        redisService.del(GroupKey.GROUP_ID_KEY, groupid+"");
        mapper.deleteByPrimaryKey(groupid);
        groupRolePerServiceBase.delByGroupId(groupid);
        groupUserRoleServiceBase.delByGroupId(groupid);
    }
}
