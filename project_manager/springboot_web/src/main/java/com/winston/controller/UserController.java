package com.winston.controller;

import com.winston.entity.User;
import com.winston.result.Result;
import com.winston.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/4 14:33
 * @Version：
 */
@RestController
@RequestMapping("/web/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * @auther: Winston
     * @Description: 查询所有用户
     * @param:
     * @return:
     * @date: 2020/1/9 16:03
     */
    @GetMapping
    public Result queryAll(User user, Integer page, Integer length){
        Result users = userService.queryByUser(user, page, length);
        return users;
    }

    /**
     * @auther: Winston
     * @Description: 根据id查询用户详情
     * @param:
     * @return:
     * @date: 2020/1/9 16:02
     */
    @GetMapping("/{userId}")
    public Result queryById(@PathVariable Integer userId){
        User user = userService.queryById(userId);
        return Result.success(user);
    }

    /**
     * @auther: Winston
     * @Description: 查询本组成员
     * @param:
     * @return:
     * @date: 2020/1/9 16:02
     */
    @GetMapping("/queryByGroupId")
    public Result queryByGroupId(Integer groupId,
                                 @RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "length",defaultValue = "10") Integer length){
        return userService.queryByGroupId(groupId, page, length);
    }

    /**
     * @auther: Winston
     * @Description: 添加用户
     * @param:
     * @return:
     * @date: 2020/1/9 16:03
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        userService.save(user);
        return Result.success("添加成功");
    }

    /**
     * @auther: Winston
     * @Description: 修改用户
     * @param:
     * @return:
     * @date: 2020/1/9 16:03
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success("修改成功");
    }

    /**
     * @auther: Winston
     * @Description: 删除用户
     * @param:
     * @return:
     * @date: 2020/1/9 16:03
     */
    @GetMapping("/del/{userId}")
    public Result del(@PathVariable Integer userId){
        userService.delete(userId);
        return Result.success("删除成功");
    }

    /**
     * @auther: Winston
     * @Description: 查询用户数
     * @param:
     * @return:
     * @date: 2020/1/9 16:03
     */
    @GetMapping("/usersCount")
    public Result usersCount(){
        Long count = userService.queryCount();
        return Result.success(count);
    }

}
