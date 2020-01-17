package com.winston.controller;

import com.winston.entity.Role;
import com.winston.result.Result;
import com.winston.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/4 17:40
 * @Version：
 */
@RestController
@RequestMapping("/web/role")
public class RoleController {

    @Autowired
    private IRoleService roleServiceBase;

    @GetMapping
    public Result queryAll(){
        List<Role> roles = roleServiceBase.queryAll(new Role());
        return Result.success(roles);
    }

    @GetMapping("/query")
    public Result query(Role role, Integer page, Integer length){
        return roleServiceBase.query(role, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        Role role = roleServiceBase.queryById(id);
        return Result.success(role);
    }

    @GetMapping("/queryByUserId/{userId}")
    public Result queryByUserId(@PathVariable Integer userId){
        Role role = roleServiceBase.queryByUserId(userId);
        return Result.success(role);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role){
        roleServiceBase.addRole(role);
        return Result.success("添加成功!");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role){
        roleServiceBase.updateRole(role);
        return Result.success("修改成功!");
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        roleServiceBase.delRole(id);
        return Result.success("删除成功!");
    }

}
