package com.winston.controller;

import com.winston.entity.Permission;
import com.winston.result.Result;
import com.winston.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PermissionController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/11/28 9:42
 * @Version：
 */
@RestController
@RequestMapping("/web/per")
public class PermissionController {

    @Autowired
    private IPermissionService permissionServiceBase;

    @GetMapping
    public Result queryAll(){
        List<Permission> permissions = permissionServiceBase.queryAll();
        return Result.success(permissionServiceBase.setPer(permissions));
    }

    @GetMapping("/query")
    public Result query(Permission permission,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "length",defaultValue = "10") Integer length){
        return permissionServiceBase.query(permission, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        Permission permission = permissionServiceBase.queryById(id);
        return Result.success(permission);
    }

    @GetMapping("/queryParant")
    public Result queryParant(){
        List<Permission> permission = permissionServiceBase.queryParant();
        return Result.success(permission);
    }

    @GetMapping("/queryByUsername")
    public Result queryByUsername(String username){
        List<Permission> permissions = permissionServiceBase.queryByUserName(username);
        return Result.success(permissionServiceBase.setPer(permissions));
    }

    @GetMapping("/queryByRoleId")
    public Result queryByRoleId(Integer roleId){
        List<Permission> permissions = permissionServiceBase.queryByRoleId(roleId);
        return Result.success(permissions);
    }

    @GetMapping("/queryByGroupId")
    public Result queryByGroupId(Integer groupId){
        List<Permission> permissions = permissionServiceBase.queryByGroupId(groupId);
        return Result.success(permissions);
    }

    @GetMapping("/queryHaveNot")
    public Result queryHaveNot(String username){
        List<Permission> permissions = permissionServiceBase.queryHaveNot(username);
        return Result.success(permissionServiceBase.setPer(permissions));
    }

    @PostMapping("/add")
    public Result add(@RequestBody Permission permission){
        permissionServiceBase.addPermission(permission);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Permission permission){
        permissionServiceBase.updatePermission(permission);
        return Result.success("添加成功！");
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        permissionServiceBase.delPermission(id);
        return Result.success("删除成功！");
    }

}
