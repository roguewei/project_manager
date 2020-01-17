package com.winston.controller;

import com.winston.entity.Project;
import com.winston.entity.ProjectDTO;
import com.winston.result.Result;
import com.winston.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ProjectController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/10 11:03
 * @Version：
 */
@RestController
@RequestMapping("/web/project")
public class ProjectController {

    @Autowired
    IProjectService projectServiceBase;

    @GetMapping
    public Result query(Project project,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "length",defaultValue = "10") Integer length){
        return projectServiceBase.query(project, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        ProjectDTO project = projectServiceBase.queryById(id);
        return Result.success(project);
    }

    @GetMapping("/queryParent")
    public Result queryParent(Integer id){
        List<Project> projects = projectServiceBase.queryParent(id);
        return Result.success(projects);
    }

    @PostMapping
    public Result add(@RequestBody Project project){
        projectServiceBase.add(project);
        return Result.success("添加成功！");
    }

    @PostMapping("/addMyProject")
    public Result addMyProject(@RequestBody Project project, Integer userId, Integer groupId){
        projectServiceBase.addMyProject(project, userId, groupId);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Project project){
        projectServiceBase.update(project);
        return Result.success("修改成功！");
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        projectServiceBase.del(id);
        return Result.success("删除成功！");
    }

}
