package com.winston.redis.key;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class GroupKey extends BasePrefix {

    private GroupKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private GroupKey(String preFix) {
        super(preFix);
    }


    public static GroupKey GROUP_ID_KEY = new GroupKey(60 * 60, "group");

}
