package com.nmssdmf.commonlib.config;

/**
 * Created by ${nmssdmf} on 2018/10/15 0015.
 */

public class BaseConfig {
    public static final boolean DEBUG = false;//false:不打印log，true：打印log

//    public static final String IP = "http://h5.mobilekoudai.com";         //线下ip地址
    public static final String IP = "http://app.zxjshg.com ";               //线上ip地址

    // star file config
    public static final String APP_IMAGE_PATH = "trading_image";//缓存图片目录
    public static final String APP_TEMP_PATH = "trading_temp";//缓存文件目录
//    public static final String IMAGEUPLOADIP = "http://h6.mobilekoudai.com/";     //线下图片上传ip地址
    public static final String IMAGEUPLOADIP = "http://www.zxjshg.com";             //线上图片上传ip地址
    public static final int MAX_IMG = 10;
    public static final String WXAPI_APPID = "wx74034cb7f59a0bc1";
    public static final String WXAPI_APPSECRET = "1e44c8f1e9fe81964ef7144cbdf8b62b";
    //微信获取access_token api地址
    public static final String WXACCESSTOKEN_API_URL = "https://api.weixin.qq.com";

    /**
     * 自动更新服务器地址
     * http://jushiyun-line.oss-cn-hangzhou.aliyuncs.com/jushiTrading.apk
     */
//    public static final String XML_URL = "http://h6.mobilekoudai.com";        //线下资源下载ip地址
    public static final String XML_URL = "http://www.zxjshg.com";               //线上资源下载ip地址

    public static final String PUBLIC_KEY = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgHz9uY5sFdnBV2lxlAk9G4H6YV5u\r" +
            "zGf/osJS7Av/VgOWrOLhCCyGbBJylAozdcWTl/sK03EY8IxLEYxNQnvWzWoThIsF\r" +
            "HdlCZHbqLJKbXIm6KCvMCWUTiAQiMPBsWCCsHKwOb8vlD3eUsHk09it862jN9Zkw\r" +
            "eoXROaIugWINGcH1AgMBAAE=";
}
