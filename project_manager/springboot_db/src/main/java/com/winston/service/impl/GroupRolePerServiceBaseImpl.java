package com.winston.service.impl;

import com.winston.entity.GroupRolePermission;
import com.winston.entity.GroupRolePermissionDTO;
import com.winston.entity.GroupRolePermissionExample;
import com.winston.mapper.GroupRolePermissionMapper;
import com.winston.service.IGroupRolePerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName GroupRolePerServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/13 11:57
 * @Version：
 */
@Service("groupRolePerServiceBase")
public class GroupRolePerServiceBaseImpl implements IGroupRolePerService {

    @Autowired
    private GroupRolePermissionMapper mapper;

    @Override
    public List<GroupRolePermission> queryByRoleIds(List<Integer> roleId) {
        if(roleId == null || roleId.size() <= 0){
            return null;
        }

        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andRoleIdIn(roleId);

        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupRolePermission> queryByRoleId(Integer roleId) {
        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);

        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupRolePermission> queryByGroupIds(List<Integer> groupId) {

        if(groupId == null || groupId.size() <= 0){
            return null;
        }
        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andGroupIdIn(groupId);

        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupRolePermission> queryByGroupId(Integer groupId) {
        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return mapper.selectByExample(example);
    }

    @Override
    @Transactional
    public void save(GroupRolePermissionDTO dto) {
        GroupRolePermission grp = new GroupRolePermission();

        if(dto.getRoleId() != null && dto.getRoleId() != 0){
            // 先删除原来该角色拥有的权限
            delByRoleId(dto.getRoleId());

            grp.setRoleId(dto.getRoleId());
            if(dto.getPerId() != null && dto.getPerId().length > 0){
                grp.setGroupId(null);
                for(Integer perId : dto.getPerId()){
                    grp.setPerId(perId);
                    mapper.insertSelective(grp);
                }
            }
        }else if(dto.getGroupId() != null && dto.getGroupId() != 0){
            // 先删除原来该部门拥有的权限
            delByGroupId(dto.getGroupId());

            grp.setGroupId(dto.getGroupId());
            if(dto.getPerId() != null && dto.getPerId().length > 0){
                grp.setRoleId(null);
                for(Integer perId : dto.getPerId()){
                    grp.setPerId(perId);
                    mapper.insertSelective(grp);
                }
            }
        }
    }

    @Override
    public void delByRoleId(Integer roleId) {
        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByPerId(Integer perId) {
        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andPerIdEqualTo(perId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByGroupId(Integer groupId) {
        GroupRolePermissionExample example = new GroupRolePermissionExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        mapper.deleteByExample(example);
    }
}
