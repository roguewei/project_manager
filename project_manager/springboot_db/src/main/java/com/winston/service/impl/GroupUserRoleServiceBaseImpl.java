package com.winston.service.impl;

import com.winston.entity.GroupUserRole;
import com.winston.entity.GroupUserRoleDTO;
import com.winston.entity.GroupUserRoleExample;
import com.winston.mapper.GroupUserRoleMapper;
import com.winston.service.IGroupUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName GroupUserRoleServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/13 11:56
 * @Version：
 */
@Service("groupUserRoleServiceBase")
public class GroupUserRoleServiceBaseImpl implements IGroupUserRoleService {

    @Autowired
    private GroupUserRoleMapper mapper;

    @Override
    public List<GroupUserRole> queryByUserId(Integer userId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupUserRole> queryByUserIdNotGroupId(Integer userId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andGroupIdIsNull();

        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupUserRole> queryByGroupId(Integer groupId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupUserRole> queryByUserIdNotRoleId(Integer userId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andRoleIdIsNull();

        return mapper.selectByExample(example);
    }

    @Override
    @Transactional
    public void save(GroupUserRoleDTO dto) {
        GroupUserRole gur = new GroupUserRole();
        gur.setUserId(dto.getUserId());

        delByUserIdNotRoleId(dto.getUserId());
        delByUserIdNotGroupId(dto.getUserId());

        if(dto.getRoleId() != null){
            gur.setRoleId(dto.getRoleId());
            gur.setGroupId(null);
            insert(gur);
        }
        if(dto.getGroupId() != null && dto.getGroupId().length > 0){
            gur.setRoleId(null);
            for(Integer groupId: dto.getGroupId()){
                gur.setGroupId(groupId);
                insert(gur);
            }
        }
    }

    @Override
    public void insert(GroupUserRole gur) {
        mapper.insertSelective(gur);
    }

    @Override
    public void delByUserIdNotRoleId(Integer userId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdIsNotNull();
        mapper.deleteByExample(example);
    }

    @Override
    public void delByUserIdNotGroupId(Integer userId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId).andGroupIdIsNotNull();
        mapper.deleteByExample(example);
    }

    @Override
    public void delByRoleId(Integer roleId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByGroupId(Integer groupId) {
        GroupUserRoleExample example = new GroupUserRoleExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        mapper.deleteByExample(example);
    }
}
