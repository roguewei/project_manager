package com.winston.service;

import com.winston.entity.Parameter;
import com.winston.result.Result;

public interface IParameterService {

    Result query(Parameter parameter, Integer page, Integer length);

}
