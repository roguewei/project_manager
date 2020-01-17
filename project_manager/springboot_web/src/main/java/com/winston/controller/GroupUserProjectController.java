package com.winston.controller;

import com.winston.entity.GroupUserProject;
import com.winston.entity.Project;
import com.winston.result.Result;
import com.winston.service.IGroupUserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GroupUserProjectController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/14 10:13
 * @Version：
 */
@RestController
@RequestMapping("/web/gup")
public class GroupUserProjectController {

    @Autowired
    private IGroupUserProjectService groupUserProjectServiceBase;

    @GetMapping
    public Result query(GroupUserProject gup, String proName, Long startTime, Long endTime,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "length",defaultValue = "10") Integer length){
        return groupUserProjectServiceBase.query(gup, proName, startTime, endTime, page, length);
    }

    @GetMapping("/queryMyParent")
    public Result queryMyParent(GroupUserProject gup, Integer id){
        List<Project> projects = groupUserProjectServiceBase.queryMyParentProject(gup, id);
        return Result.success(projects);
    }

    @PostMapping
    public Result add(GroupUserProject gup){
        groupUserProjectServiceBase.save(gup);
        return Result.success("分配成功！");
    }

}
