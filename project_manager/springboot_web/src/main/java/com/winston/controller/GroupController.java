package com.winston.controller;

import com.winston.entity.Groups;
import com.winston.result.Result;
import com.winston.service.IGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GroupController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/6 9:08
 * @Version：
 */
@RestController
@RequestMapping("/web/group")
public class GroupController {

    @Autowired
    private IGroupsService groupsServiceBase;

    @GetMapping
    public Result queryAll(){
        List<Groups> groups = groupsServiceBase.queryAll();
        return Result.success(groups);
    }

    @GetMapping("/query")
    public Result query(Groups groups, Integer page, Integer length){
        return groupsServiceBase.query(groups, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        Groups groups = groupsServiceBase.queryById(id);
        return Result.success(groups);
    }

    @GetMapping("/queryByUserId/{userId}")
    public Result queryByUserId(@PathVariable Integer userId){
        List<Groups> groups = groupsServiceBase.queryByUserId(userId);
        return Result.success(groups);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Groups groups){
        groupsServiceBase.addGroups(groups);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Groups groups){
        groupsServiceBase.updateGroups(groups);
        return Result.success("修改成功！");
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        groupsServiceBase.delGroups(id);
        return Result.success("删除成功！");
    }

}
