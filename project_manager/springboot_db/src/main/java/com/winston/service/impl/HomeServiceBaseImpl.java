package com.winston.service.impl;

import com.winston.service.IGroupsService;
import com.winston.service.IHomeService;
import com.winston.service.IProjectService;
import com.winston.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HomeServiceBaseImpl
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/12/25 10:31
 * @Version：
 */
@Service("homeServiceBase")
public class HomeServiceBaseImpl implements IHomeService {

    @Autowired
    private IUserService userServiceBase;

    @Autowired
    private IGroupsService groupsServiceBase;

    @Autowired
    private IProjectService projectServiceBase;

    @Override
    public Map<String, Object> getHomeInfo() {
        Long usersCount = userServiceBase.queryCount();
        Long groupsCount = groupsServiceBase.queryCount();
        Long projectsCount = projectServiceBase.queryCount();

        Map<String, Object> result = new HashMap<>();
        result.put("usersCount", usersCount);
        result.put("groupsCount", groupsCount);
        result.put("projectsCount", projectsCount);
        return result;
    }
}
