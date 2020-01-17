package com.winston.controller;

import com.winston.entity.ElIcon;
import com.winston.result.Result;
import com.winston.service.IElIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ElIconController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/8 11:47
 * @Version：
 */
@RestController
@RequestMapping("/web/icon")
public class ElIconController {

    @Autowired
    private IElIconService elIconServiceBase;

    @GetMapping
    public Result queryAll(){
        List<ElIcon> elIcons = elIconServiceBase.queryAll();
        return Result.success(elIcons);
    }

    @GetMapping("/query")
    public Result query(ElIcon elIcon,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "length", defaultValue = "10") Integer length){
        return elIconServiceBase.query(elIcon, page, length);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Integer id){
        ElIcon elIcon = elIconServiceBase.queryById(id);
        return Result.success(elIcon);
    }

    @PostMapping
    public Result add(@RequestBody ElIcon elIcon){
        elIconServiceBase.add(elIcon);
        return Result.success("添加成功！");
    }

    @PostMapping("/update")
    public Result update(@RequestBody ElIcon elIcon){
        elIconServiceBase.update(elIcon);
        return Result.success("修改成功！");
    }

    @GetMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        elIconServiceBase.del(id);
        return Result.success("删除成功");
    }

}
