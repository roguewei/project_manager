package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.GroupUserProject;
import com.winston.entity.GroupUserProjectExample;
import com.winston.entity.Project;
import com.winston.entity.ProjectDTO;
import com.winston.mapper.GroupUserProjectMapper;
import com.winston.result.Result;
import com.winston.service.IGroupUserProjectService;
import com.winston.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GroupProjectServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/13 15:40
 * @Version：
 */
@Service("groupUserProjectServiceBase")
public class GroupUserProjectServiceBaseImpl implements IGroupUserProjectService {

    @Autowired
    private GroupUserProjectMapper mapper;

    @Autowired
    private IProjectService projectServiceBase;

    @Override
    public Long queryCountByGroupId(Integer groupId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        return mapper.countByExample(example);
    }

    @Override
    public Result query(GroupUserProject gup, String proName, Long startTime, Long endTime, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        GroupUserProjectExample example = new GroupUserProjectExample();
        if(gup.getStaffId() != null){
            GroupUserProjectExample.Criteria criteria1 = example.createCriteria();
            criteria1.andStaffIdEqualTo(gup.getStaffId());
            example.or(criteria1);
        }
        if(gup.getCliId() != null){
            GroupUserProjectExample.Criteria criteria2 = example.createCriteria();
            criteria2.andCliIdEqualTo(gup.getCliId());
            example.or(criteria2);
        }
        if(gup.getGroupId() != null){
            GroupUserProjectExample.Criteria criteria3 = example.createCriteria();
            criteria3.andGroupIdEqualTo(gup.getGroupId());
            example.or(criteria3);
        }
        List<GroupUserProject> groupUserProjects = mapper.selectByExample(example);

        List<Integer> proIds = new ArrayList<>();
        if(groupUserProjects != null && groupUserProjects.size() > 0){
            for(GroupUserProject groupUserProject : groupUserProjects){
                proIds.add(groupUserProject.getProId());
            }
        }
        List<ProjectDTO> dtoList = projectServiceBase.queryByIds(proIds, proName, startTime, endTime);

        PageInfo pageInfo = new PageInfo(groupUserProjects);
        return Result.success(dtoList, pageInfo.getTotal());
    }

    @Override
    public List<Project> queryMyParentProject(GroupUserProject gup, Integer id) {
        List<Project> result = new ArrayList<>();
        GroupUserProjectExample example = new GroupUserProjectExample();
        if(gup.getStaffId() == null && gup.getGroupId() == null){
            return result;
        }
        if(gup.getStaffId() != null){
            GroupUserProjectExample.Criteria criteria1 = example.createCriteria();
            criteria1.andStaffIdEqualTo(gup.getStaffId());
            example.or(criteria1);
        }
//        else if(gup.getStaffId() != null && gup.getGroupId() != null){
//            GroupUserProjectExample.Criteria criteria1 = example.createCriteria();
//            criteria1.andStaffIdEqualTo(gup.getStaffId());
//            example.or(criteria1);
//
//            GroupUserProjectExample.Criteria criteria2 = example.createCriteria();
//            criteria2.andGroupIdEqualTo(gup.getGroupId()).andStaffIdEqualTo(gup.getStaffId());
//            example.or(criteria2);
//        }
        if(gup.getGroupId() != null){
            GroupUserProjectExample.Criteria criteria2 = example.createCriteria();
            criteria2.andGroupIdEqualTo(gup.getGroupId());
            example.or(criteria2);
        }
        List<GroupUserProject> groupUserProjects = mapper.selectByExample(example);
        List<Integer> proIds = new ArrayList<>();
        if(groupUserProjects != null && groupUserProjects.size() > 0){
            for(GroupUserProject groupUserProject : groupUserProjects){
                proIds.add(groupUserProject.getProId());
            }
        }
        result = projectServiceBase.queryMyParent(proIds, id);
        return result;
    }

    @Override
    public List<GroupUserProject> queryByGroupId(Integer groupId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupUserProject> queryByStaffId(Integer staffId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andStaffIdEqualTo(staffId);
        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupUserProject> queryByCliId(Integer cliId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andCliIdEqualTo(cliId);
        return mapper.selectByExample(example);
    }

    @Override
    public List<GroupUserProject> queryByProjectId(Integer projectId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andProIdEqualTo(projectId);
        return mapper.selectByExample(example);
    }

    @Override
    public void save(GroupUserProject groupUserProject) {
        delByProjectId(groupUserProject.getProId());
        mapper.insert(groupUserProject);
    }

    @Override
    public void updateByProjectId(GroupUserProject groupUserProject) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andProIdEqualTo(groupUserProject.getProId());
        mapper.updateByExampleSelective(groupUserProject, example);
    }

    @Override
    public void delByGroupId(Integer groupId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andGroupIdEqualTo(groupId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByStaffId(Integer staffId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andStaffIdEqualTo(staffId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByCliId(Integer cliId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andCliIdEqualTo(cliId);
        mapper.deleteByExample(example);
    }

    @Override
    public void delByProjectId(Integer projectId) {
        GroupUserProjectExample example = new GroupUserProjectExample();
        example.createCriteria().andProIdEqualTo(projectId);
        mapper.deleteByExample(example);
    }
}
