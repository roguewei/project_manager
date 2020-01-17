package com.winston.controller;

import com.winston.entity.Parameter;
import com.winston.result.Result;
import com.winston.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ParameterController
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/9 11:23
 * @Version：
 */
@RestController
@RequestMapping("/web/params")
public class ParameterController {

    @Autowired
    private IParameterService parameterServiceBase;

    @GetMapping
    public Result query(Parameter parameter,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "length",defaultValue = "1000") Integer length){
        return parameterServiceBase.query(parameter, page, length);
    }

}
