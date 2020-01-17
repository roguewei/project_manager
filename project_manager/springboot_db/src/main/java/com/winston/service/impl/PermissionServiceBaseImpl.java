package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.*;
import com.winston.exception.ErrorException;
import com.winston.mapper.PermissionMapper;
import com.winston.redis.RedisService;
import com.winston.redis.key.PermissionKey;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IGroupRolePerService;
import com.winston.service.IGroupUserRoleService;
import com.winston.service.IPermissionService;
import com.winston.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Winston
 * @title: PermissionServiceImpl
 * @projectName shiroDemo
 * @description: 基本方法 没必要就尽量不改动这里的代码，而使用继承子类去扩展
 * @date 2019/7/24 14:32
 */
@Service("permissionServiceBase")
public class PermissionServiceBaseImpl implements IPermissionService {

    @Autowired
    private PermissionMapper mapper;

    @Autowired
    private IUserService userServiceBase;

    @Autowired
    private IGroupUserRoleService groupUserRoleServiceBase;

    @Autowired
    private IGroupRolePerService groupRolePerServiceBase;

    @Autowired
    private RedisService redisService;

    @Override
    public Result query(Permission permission, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(permission.getPername())){
            criteria.andPernameLike("%"+permission.getPername()+"%");
        }
        if(StringUtils.isNotBlank(permission.getType())){
            criteria.andTypeEqualTo(permission.getType());
        }
        List<Permission> permissionList = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(permissionList);
        return Result.success(permissionList, pageInfo.getTotal());
    }

    @Override
    public List<Permission> queryAll() {
        List<Permission> permissions = mapper.selectByExample(new PermissionExample());

        return permissions;
    }

    @Override
    public List<Permission> queryParant() {
        PermissionExample example = new PermissionExample();
        example.createCriteria().andTypeEqualTo("0");
        return mapper.selectByExample(example);
    }

    @Override
    public Permission queryById(Integer id) {
        Permission redisData = redisService.get(PermissionKey.PERMISSION_KEY, id + "", Permission.class);
        if(redisData != null){
            return redisData;
        }
        Permission permission = mapper.selectByPrimaryKey(id);
        redisService.set(PermissionKey.PERMISSION_KEY, id+"", permission);
        return permission;
    }

    /**
     * @auther: Winston
     * @Description: 根据用户名查询拥有的权限
     * @param:
     * @return:
     * @date: 2019/9/25 10:49
     */
    @Override
    public List<Permission> queryByUserName(String username) {
        User user = userServiceBase.queryByUsername(username);
        if(user != null){
            // 查询出用户拥有的角色和分组
            List<GroupUserRole> groupUserRoles = groupUserRoleServiceBase.queryByUserId(user.getId());
            if(groupUserRoles != null && groupUserRoles.size() > 0){
                List<Integer> roleIds = new ArrayList<>();
                List<Integer> groupIds = new ArrayList<>();
                for(GroupUserRole groupUserRole : groupUserRoles){

                    if(groupUserRole.getRoleId() != null)
                        roleIds.add(groupUserRole.getRoleId());

                    if(groupUserRole.getGroupId() != null)
                        groupIds.add(groupUserRole.getGroupId());
                }

                // 根据角色id和分组id分别查询出拥有的权限
                List<GroupRolePermission> queryByRoleIds = groupRolePerServiceBase.queryByRoleIds(roleIds);
                List<GroupRolePermission> queryByGroupIds = groupRolePerServiceBase.queryByGroupIds(groupIds);

                List<Integer> perIds = new ArrayList<>();
                if(queryByRoleIds != null){
                    for(GroupRolePermission groupRolePermission : queryByRoleIds){
                        perIds.add(groupRolePermission.getPerId());
                    }

                    if(queryByGroupIds != null){
                        for(GroupRolePermission groupRolePermission : queryByGroupIds){
                            if(!perIds.contains(groupRolePermission.getPerId())){
                                perIds.add(groupRolePermission.getPerId());
                            }
                        }
                    }

                    if(perIds.size() == 0){
                        return null;
                    }
                    PermissionExample example = new PermissionExample();
                    example.createCriteria().andIdIn(perIds);
                    return mapper.selectByExample(example);
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * @auther: Winston
     * @Description: 根据用户名查询未拥有的权限
     * @param:
     * @return:
     * @date: 2019/9/25 10:49
     */
    @Override
    public List<Permission> queryHaveNot(String username) {
        List<Permission> permissionsAll = queryAll();
        List<Permission> permissionsHave = queryByUserName(username);
        if(permissionsAll != null){
            if(permissionsHave == null){
                return permissionsAll;
            }
            for(Permission permission : permissionsHave){
                for(int perHave=0; perHave<permissionsAll.size() ; perHave++){
                    if(permission.getId().equals(permissionsAll.get(perHave).getId()) ){
                        permissionsAll.remove(permissionsAll.get(perHave));
                    }
                }
            }
        }
        return permissionsAll;
    }

    @Override
    public List<Permission> queryByRoleId(Integer roleId) {
        List<Permission> result = new ArrayList<>();
        List<GroupRolePermission> groupRolePermissions = groupRolePerServiceBase.queryByRoleId(roleId);

        if(groupRolePermissions != null && groupRolePermissions.size() > 0){
            for(GroupRolePermission grp : groupRolePermissions){
                result.add(queryById(grp.getPerId()));
            }
        }
        return result;
    }

    @Override
    public List<Permission> queryByGroupId(Integer groupId) {
        List<Permission> result = new ArrayList<>();
        List<GroupRolePermission> groupRolePermissions = groupRolePerServiceBase.queryByGroupId(groupId);

        if(groupRolePermissions != null && groupRolePermissions.size() > 0){
            for(GroupRolePermission grp : groupRolePermissions){
                result.add(queryById(grp.getPerId()));
            }
        }
        return result;
    }

    @Override
    public void addAllUrl(Permission permission) {
        mapper.insert(permission);
    }

    @Override
    public void addPermission(Permission permission) {
        boolean nameNotBlank = StringUtils.isNotBlank(permission.getPername());
        boolean urlNotBlank = StringUtils.isNotBlank(permission.getPerurl());

        if("0".equals(permission.getType())){
            if(nameNotBlank){
                if(perExist(permission.getPername())){
                    throw new ErrorException(CodeMsg.PERMISSION_ALERADY_EXIST);
                }
                mapper.insert(permission);
            }else{
                throw new ErrorException(CodeMsg.PERMISSION_PARAM_NULL);
            }
        }else{
            if(nameNotBlank && urlNotBlank){
                if(perExist(permission.getPername())){
                    throw new ErrorException(CodeMsg.PERMISSION_ALERADY_EXIST);
                }
                mapper.insert(permission);
            }else{
                throw new ErrorException(CodeMsg.PERMISSION_PARAM_NULL);
            }
        }
    }

    @Override
    public void updatePermission(Permission permission) {
        mapper.updateByPrimaryKeySelective(permission);
        redisService.del(PermissionKey.PERMISSION_KEY, permission.getId()+"");
    }

    @Override
    public void delPermission(Integer id) {
        groupRolePerServiceBase.delByPerId(id);
        mapper.deleteByPrimaryKey(id);
        redisService.del(PermissionKey.PERMISSION_KEY, id+"");
    }

    @Override
    public List<Map<String, Object>> setPer(List<Permission> permissions){

        List<Map<String, Object>> result = new ArrayList<>();

        if(permissions == null || permissions.size() <= 0){
            return result;
        }
        permissions.forEach(item -> {
            if(item.getParentid() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("icon", item.getIcon());
                map.put("id", item.getId());
                map.put("parentid", item.getParentid());
                map.put("pername", item.getPername());
                map.put("perurl", item.getPerurl());
                map.put("type", item.getType());
                List<Permission> children = new ArrayList<>();
                permissions.forEach(child -> {
                    if(child.getParentid() == item.getId()){
                        children.add(child);
                    }
                });
                map.put("children", children);

                result.add(map);
            }
        });
        return result;
    }

    boolean perExist(String pername){
        PermissionExample example = new PermissionExample();
        example.createCriteria()
                .andPernameEqualTo(pername);
        List<Permission> permissions = mapper.selectByExample(example);
        if(permissions != null && permissions.size() > 0){
            return true;
        }else{
            return false;
        }
    }
}
