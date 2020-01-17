package com.winston.controller;

import com.winston.result.Result;
import com.winston.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName HomeController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/30 16:01
 * @Version：
 */
@RestController
@RequestMapping("/web/home")
public class HomeController {

    @Autowired
    private IHomeService homeServiceBase;

    @GetMapping
    public Result home(){
        Map<String, Object> homeInfo = homeServiceBase.getHomeInfo();
        return Result.success(homeInfo);
    }

}
