package com.winston.redis.key;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class PermissionKey extends BasePrefix {

    private PermissionKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private PermissionKey(String preFix) {
        super(preFix);
    }


    public static PermissionKey PERMISSION_KEY = new PermissionKey(60 * 60, "permission");
    public static PermissionKey PERMISSION_USER_KEY = new PermissionKey(60 * 60, "_user");

}
