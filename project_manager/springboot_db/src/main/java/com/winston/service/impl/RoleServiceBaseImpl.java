package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.GroupUserRole;
import com.winston.entity.Role;
import com.winston.entity.RoleExample;
import com.winston.entity.User;
import com.winston.exception.ErrorException;
import com.winston.mapper.RoleMapper;
import com.winston.redis.RedisService;
import com.winston.redis.key.RoleKey;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IGroupRolePerService;
import com.winston.service.IGroupUserRoleService;
import com.winston.service.IRoleService;
import com.winston.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Winston
 * @title: RoleServiceImpl
 * @projectName shiroDemo
 * @description: 基本方法 没必要就尽量不改动这里的代码，而使用继承子类去扩展
 * @date 2019/7/24 14:32
 */
@Service("roleServiceBase")
public class RoleServiceBaseImpl implements IRoleService {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IGroupUserRoleService groupUserRoleServiceBase;

    @Autowired
    private IGroupRolePerService groupRolePerServiceBase;

    @Override
    public List<Role> queryAll(Role role) {
        return mapper.selectByExample(new RoleExample());
    }

    @Override
    public Result query(Role role, Integer page, Integer length) {
        PageHelper.startPage(page, length);

        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(role.getRoleDesc())){
            criteria.andRoleDescLike("%"+role.getRoleDesc()+"%");
        }
        if(StringUtils.isNotBlank(role.getRoleName())){
            criteria.andRoleNameLike("%"+role.getRoleName()+"%");
        }
        List<Role> roles = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(roles);
        return Result.success(roles, pageInfo.getTotal());
    }

    @Override
    public Role queryById(Integer id) {
        Role redisData = redisService.get(RoleKey.ROLE_ID_KEY, id + "", Role.class);
        if(redisData != null){
            return redisData;
        }
        Role role = mapper.selectByPrimaryKey(id);
        if(role != null){
            redisService.set(RoleKey.ROLE_ID_KEY, id+"", role);
            return role;
        } else{
            return new Role();
        }
    }

    @Override
    public List<Role> queryByIds(List<Integer> ids) {
        RoleExample example = new RoleExample();
        example.createCriteria().andIdIn(ids);
        return mapper.selectByExample(example);
    }

    @Override
    public Role queryByUserId(Integer userId) {
        List<GroupUserRole> groupUserRoles = groupUserRoleServiceBase.queryByUserIdNotGroupId(userId);
        if(groupUserRoles != null && groupUserRoles.size() > 0){
            for(GroupUserRole gur : groupUserRoles){
                if(gur.getRoleId() != null){
                    return queryById(gur.getRoleId());
                }
            }
        }
        return new Role();
    }

    @Override
    public void addRole(Role role) {
        RoleExample example = new RoleExample();
        example.createCriteria().andRoleDescEqualTo(role.getRoleDesc());
        List<Role> roles = mapper.selectByExample(example);
        if(roles!=null && roles.size() > 0){
            throw new ErrorException(CodeMsg.ROLE_ALREADY_EXIST);
        }
        mapper.insertSelective(role);
    }

    @Override
    public void updateRole(Role role) {
        redisService.del(RoleKey.ROLE_ID_KEY, role.getId()+"");
        mapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void delRole(Integer roleid) {
        redisService.del(RoleKey.ROLE_ID_KEY, roleid+"");
        mapper.deleteByPrimaryKey(roleid);
        groupUserRoleServiceBase.delByRoleId(roleid);
        groupRolePerServiceBase.delByRoleId(roleid);
    }
}
