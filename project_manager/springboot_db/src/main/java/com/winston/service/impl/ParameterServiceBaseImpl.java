package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.Parameter;
import com.winston.entity.ParameterExample;
import com.winston.mapper.ParameterMapper;
import com.winston.result.Result;
import com.winston.service.IParameterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ParameterServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/9 11:25
 * @Version：
 */
@Service("parameterServiceBase")
public class ParameterServiceBaseImpl implements IParameterService {

    @Autowired
    private ParameterMapper mapper;

    @Override
    public Result query(Parameter parameter, Integer page, Integer length) {
        PageHelper.startPage(page, length);
        ParameterExample example = new ParameterExample();
        ParameterExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(parameter.getParamType())){
            criteria.andParamTypeEqualTo(parameter.getParamType());
        }
        if(StringUtils.isNotBlank(parameter.getParamName())){
            criteria.andParamNameLike("%"+parameter.getParamName()+"%");
        }
        List<Parameter> parameters = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(parameters);
        return Result.success(parameters, pageInfo.getTotal());
    }
}
