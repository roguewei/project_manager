package com.winston.redis.key;

/**
 * @author weigaosheng
 * @description
 * @CalssName UserKey
 * @date 2019/3/4
 * @params
 * @return
 */
public class IconKey extends BasePrefix {

    private IconKey(int expireSeconds, String preFix) {
        super(expireSeconds, preFix);
    }

    private IconKey(String preFix) {
        super(preFix);
    }


    public static IconKey ICON_ID_KEY = new IconKey(60 * 60, "icon");

}
