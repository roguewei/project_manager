package com.winston.redis.key;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class UserKey extends BasePrefix {

    private UserKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private UserKey(String preFix) {
        super(preFix);
    }


    public static UserKey USER_ID_KEY = new UserKey(60 * 60, "user");

}
