package com.nmssdmf.commonlib.util;

import java.util.List;

/**
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class CommonUtils {

    /**
     * 判断list里都有不为空的值
     *
     * @param args
     * @param index 事先已知数组里有几个数值
     * @return
     */
    public static boolean isFull(List<String> args, int index) {
        for (int i = 0; i < index; i++) {
            if (isEmpty(args.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否为null
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
//        JLog.i(TAG, "isEmpty obj:" + obj);
        String str = obj + "";
        if (str.equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }
}
