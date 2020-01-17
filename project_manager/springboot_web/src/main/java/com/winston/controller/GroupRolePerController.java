package com.winston.controller;

import com.winston.entity.GroupRolePermission;
import com.winston.entity.GroupRolePermissionDTO;
import com.winston.result.Result;
import com.winston.service.IGroupRolePerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName GroupRolePerController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/7 16:44
 * @Version：
 */
@RestController
@RequestMapping("/web/grp")
public class GroupRolePerController {

    @Autowired
    private IGroupRolePerService groupRolePerServiceBase;

    @PostMapping("/save")
    public Result save(@RequestBody GroupRolePermissionDTO dto){
        groupRolePerServiceBase.save(dto);
        return Result.success("编辑成功！");
    }

}
