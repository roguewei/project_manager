package com.winston.redis.key;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class ProjectKey extends BasePrefix {

    private ProjectKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private ProjectKey(String preFix) {
        super(preFix);
    }


    public static ProjectKey PROJECT_ID_KEY = new ProjectKey(60 * 60, "project");

}
