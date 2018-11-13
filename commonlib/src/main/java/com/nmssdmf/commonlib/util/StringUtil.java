package com.nmssdmf.commonlib.util;

import java.util.regex.Pattern;

/**
 * Created by ${nmssdmf} on 2018/10/15 0015.
 */

public class StringUtil {
    public static boolean isEmpty(String string){
        if (string == null || string.length() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 判断密码格式
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        Pattern pattern = Pattern.compile("[0-9A-Za-z] {6,14}");
        return pattern.matcher(pwd).find();

    }
}
