package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.GroupUserProject;
import com.winston.entity.Project;
import com.winston.entity.ProjectDTO;
import com.winston.entity.ProjectExample;
import com.winston.exception.ErrorException;
import com.winston.mapper.ProjectMapper;
import com.winston.redis.RedisService;
import com.winston.redis.key.ProjectKey;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.service.IGroupUserProjectService;
import com.winston.service.IProjectService;
import com.winston.utils.TimeUtile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProjectServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/13 15:33
 * @Version：
 */
@Service("projectServiceBase")
public class ProjectServiceBaseImpl implements IProjectService {

    @Autowired
    private ProjectMapper mapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IGroupUserProjectService groupUserProjectServiceBase;

    @Override
    public Long queryCount() {
        return mapper.countByExample(new ProjectExample());
    }

    @Override
    public Result query(Project project, Integer page, Integer length) {
        PageHelper.startPage(page, length);

        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(project.getProName())){
            criteria.andProNameLike("%"+ project.getProName() +"%");
        }
        if(project.getStartTime() != null){
            criteria.andStartTimeGreaterThanOrEqualTo(project.getStartTime());
        }
        if(project.getEndTime() != null){
            criteria.andEndTimeLessThanOrEqualTo(project.getEndTime());
        }
        List<Project> projects = mapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo(projects);

        List<ProjectDTO> dtoList = new ArrayList<>();
        if(projects != null && projects.size() > 0){
            for(Project pro : projects){
                dtoList.add(setDtoResult(pro));
            }
        }

        return Result.success(dtoList, pageInfo.getTotal());
    }

    @Override
    public ProjectDTO queryById(Integer id) {
        ProjectDTO redisData = redisService.get(ProjectKey.PROJECT_ID_KEY, id + "", ProjectDTO.class);
        if(redisData == null){
            Project project = mapper.selectByPrimaryKey(id);
            ProjectDTO dto = setDtoResult(project);
            redisService.set(ProjectKey.PROJECT_ID_KEY, id+"", dto);
            return setDtoResult(project);
        }else{
            return redisData;
        }
    }

    @Override
    public List<Project> queryParent(Integer id) {
        ProjectExample example = new ProjectExample();
        example.createCriteria().andIdNotEqualTo(id);
        return mapper.selectByExample(example);
    }

    @Override
    public List<Project> queryMyParent(List<Integer> ids, Integer id) {
        List<Project> result = new ArrayList<>();
        if(ids != null && ids.size() > 0) {

            ProjectExample example = new ProjectExample();
            example.createCriteria().andIdIn(ids).andIdNotEqualTo(id);
            return mapper.selectByExample(example);
        }
        return result;
    }

    @Override
    public List<ProjectDTO> queryByIds(List<Integer> ids, String proName, Long startTime, Long endTime) {
        List<ProjectDTO> result = new ArrayList<>();
        if(ids != null && ids.size() > 0){
            ProjectExample example = new ProjectExample();
            ProjectExample.Criteria criteria = example.createCriteria().andIdIn(ids);
            if(StringUtils.isNotBlank(proName)){
                criteria.andProNameLike("%"+proName+"%");
            }
            if(startTime != null && startTime != 0){
                criteria.andStartTimeGreaterThanOrEqualTo(startTime);
            }
            if(endTime != null && endTime != 0){
                criteria.andEndTimeLessThanOrEqualTo(endTime);
            }
            List<Project> projects = mapper.selectByExample(example);

            if(projects != null && projects.size() > 0){
                for(Project pro : projects){
                    result.add(setDtoResult(pro));
                }
            }
        }
        return result;
    }

    @Override
    public List<ProjectDTO> queryByStaffId(Integer staffId) {
        List<GroupUserProject> groupUserProjects = groupUserProjectServiceBase.queryByStaffId(staffId);
        return setResult(groupUserProjects);
    }

    @Override
    public List<ProjectDTO> queryByCliId(Integer cliId) {
        List<GroupUserProject> groupUserProjects = groupUserProjectServiceBase.queryByCliId(cliId);
        return setResult(groupUserProjects);
    }

    @Override
    public List<ProjectDTO> queryByGroupId(Integer groupId) {
        List<GroupUserProject> groupUserProjects = groupUserProjectServiceBase.queryByGroupId(groupId);
        return setResult(groupUserProjects);
    }

    @Override
    public Integer add(Project project) {
        if(project.getParentId() == null){
            project.setParentId(0);
        }
        if(project.getProSchedule() == null){
            project.setProSchedule(0);
        }
        if(project.getProSchedule() == 100){
            project.setState(1);
        }else{
            project.setState(0);
        }
        mapper.insertSelective(project);
        return project.getId();
    }

    @Override
    @Transactional
    public void addMyProject(Project project, Integer userId, Integer groupId) {
        Integer proId = add(project);
        GroupUserProject gup = new GroupUserProject();
        gup.setProId(proId);
        gup.setStaffId(userId);
        gup.setGroupId(groupId);
        groupUserProjectServiceBase.save(gup);
    }

    @Override
    public void update(Project project) {
        if(project.getProSchedule() == 100){
            project.setState(1);
        }else{
            project.setState(0);
        }
        mapper.updateByPrimaryKeySelective(project);

        redisService.del(ProjectKey.PROJECT_ID_KEY, project.getId()+"");
    }

    @Override
    @Transactional
    public void del(Integer id) {

        ProjectExample example = new ProjectExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<Project> projects = mapper.selectByExample(example);

        if(projects != null && projects.size() > 0){
            for(Project pro : projects){
                del(pro.getId());
                mapper.deleteByPrimaryKey(pro.getId());
            }
            mapper.deleteByPrimaryKey(id);
        }else{
            mapper.deleteByPrimaryKey(id);
        }

        redisService.del(ProjectKey.PROJECT_ID_KEY, id+"");

//        ProjectExample example = new ProjectExample();
//        example.createCriteria().andParentIdEqualTo(id);
//
//        mapper.deleteByExample(example);
//        mapper.deleteByPrimaryKey(id);
//        groupUserProjectServiceBase.delByProjectId(id);
//
//        redisService.del(ProjectKey.PROJECT_ID_KEY, id+"");
    }

    private List<ProjectDTO> setResult(List<GroupUserProject> groupUserProjects){
        if(groupUserProjects==null || groupUserProjects.size()<=0){
            throw new ErrorException(CodeMsg.USER_HAVE_NOT_PROJECT);
        }
        List<Integer> proIds = new ArrayList<>();
        groupUserProjects.forEach(item -> {
            proIds.add(item.getProId());
        });

        ProjectExample example = new ProjectExample();
        example.createCriteria().andIdIn(proIds).andStateNotEqualTo(0);

        List<ProjectDTO> dtoList = new ArrayList<>();
        List<Project> projectList = mapper.selectByExample(example);
        if(projectList != null && projectList.size()>0){
            for(Project project : projectList){
                ProjectDTO dto = setDtoResult(project);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private ProjectDTO setDtoResult(Project project){
        ProjectDTO dto = new ProjectDTO();
        if(project != null){
            List<GroupUserProject> gup = groupUserProjectServiceBase.queryByProjectId(project.getId());
            if(gup != null && gup.size()>0){
                dto.setGroupId(gup.get(0).getGroupId());
                dto.setStaffId(gup.get(0).getStaffId());
            }
            BeanUtils.copyProperties(project, dto);
            if(project.getStartTime() != null){
                dto.setStartTimeFormat(TimeUtile.getTimeFormatDate(project.getStartTime()));
            }
            if(project.getEndTime() != null){
                dto.setEndTimeFormat(TimeUtile.getTimeFormatDate(project.getEndTime()));
            }
            if(project.getRelEndTime() != null){
//                dto.setRelEndTimeFormat(TimeUtile.getTimeFormatDate(project.getRelEndTime()));
                dto.setEndTimeFormat(TimeUtile.getTimeFormatDate(project.getRelEndTime()));
            }
        }

        return dto;
    }
}
