package com.winston.service;


import com.winston.entity.GroupUserProject;
import com.winston.entity.Project;
import com.winston.result.Result;

import java.util.List;

public interface IGroupUserProjectService {

    Long queryCountByGroupId(Integer groupId);

    Result query(GroupUserProject gup, String proName, Long startTime, Long endTime, Integer page, Integer length);

    List<Project> queryMyParentProject(GroupUserProject gup, Integer id);

    List<GroupUserProject> queryByGroupId(Integer groupId);

    List<GroupUserProject> queryByStaffId(Integer staffId);

    List<GroupUserProject> queryByCliId(Integer cliId);

    List<GroupUserProject> queryByProjectId(Integer projectId);

    void save(GroupUserProject groupUserProject);

    void updateByProjectId(GroupUserProject groupUserProject);

    void delByGroupId(Integer groupId);

    void delByStaffId(Integer staffId);

    void delByCliId(Integer cliId);

    void delByProjectId(Integer projectId);

}
