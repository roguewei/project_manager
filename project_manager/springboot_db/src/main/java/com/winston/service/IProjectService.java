package com.winston.service;

import com.winston.entity.Project;
import com.winston.entity.ProjectDTO;
import com.winston.result.Result;

import java.util.List;

public interface IProjectService {

    Long queryCount();

    Result query(Project project, Integer page, Integer length);

    ProjectDTO queryById(Integer id);

    List<Project> queryParent(Integer id);

    List<Project> queryMyParent(List<Integer> ids, Integer id);

    List<ProjectDTO> queryByIds(List<Integer> ids, String proName, Long startTime, Long endTime);

    List<ProjectDTO> queryByStaffId(Integer staffId);

    List<ProjectDTO> queryByCliId(Integer cliId);

    List<ProjectDTO> queryByGroupId(Integer groupId);

    Integer add(Project project);

    void addMyProject(Project project, Integer userId, Integer groupId);

    void update(Project project);

    void del(Integer id);

}
