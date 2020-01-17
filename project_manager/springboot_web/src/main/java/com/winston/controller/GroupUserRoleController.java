package com.winston.controller;

import com.winston.entity.GroupUserRoleDTO;
import com.winston.result.Result;
import com.winston.service.IGroupUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName GroupUserRoleController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/6 12:00
 * @Version：
 */
@RestController
@RequestMapping("/web/gur")
public class GroupUserRoleController {

    @Autowired
    private IGroupUserRoleService groupUserRoleServiceBase;

    @PostMapping
    public Result save(@RequestBody GroupUserRoleDTO dto){
        groupUserRoleServiceBase.save(dto);
        return Result.success("编辑成功！");
    }

}
