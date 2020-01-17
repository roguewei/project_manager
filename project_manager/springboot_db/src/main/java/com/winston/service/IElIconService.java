package com.winston.service;

import com.winston.entity.ElIcon;
import com.winston.result.Result;

import java.util.List;

public interface IElIconService {

    List<ElIcon> queryAll();

    Result query(ElIcon elIcon, Integer page, Integer length);

    ElIcon queryById(Integer id);

    void add(ElIcon elIcon);

    void update(ElIcon elIcon);

    void del(Integer id);

}
