package com.winston.service;

import com.winston.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PerCache
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2020/1/10 10:05
 * @Version：
 */
@Component
public class PerCache {

    public static List<String> allPermission = new ArrayList<>();

    @Autowired
    private IPermissionService permissionServiceBase;

    @PostConstruct
    public void init(){
        //系统启动中。。。加载codeMap
        List<Permission> list = permissionServiceBase.queryAll();
        for(Permission permission : list){
            allPermission.add(permission.getPerurl());
        }
    }

    @PreDestroy
    public void destroy(){
        // 系统运行结束
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void testOne(){
        // 每2小时执行一次缓存
        init();
    }

}
