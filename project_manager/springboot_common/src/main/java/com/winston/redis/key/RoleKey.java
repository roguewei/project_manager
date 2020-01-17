package com.winston.redis.key;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class RoleKey extends BasePrefix {

    private RoleKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private RoleKey(String preFix) {
        super(preFix);
    }


    public static RoleKey ROLE_ID_KEY = new RoleKey(60 * 60, "role");

}
