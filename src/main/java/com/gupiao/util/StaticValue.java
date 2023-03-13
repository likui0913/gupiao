package com.gupiao.util;

/**
 * 常量类
 */
public class StaticValue {

    /**
     * 获取股票数据网址根路径信息
     */

    public static String DATA_URL_REMOTE = "remote";
    public static String DATA_URL_LOCAL = "local";

    public static String DATA_URL_PATH = "";
    public static String DATA_URL_PATH_SETTING = "remote";//local,
    public static String PIC_PATH_SETTING = "/Users/kuili/source-code/gupiao-github/pic/";
    public static String DATA_URL_PATH_REMOTE = "http://8.130.68.34:443/";
    public static String DATA_URL_PATH_LOCAL = "http://127.0.0.1:443/";

    /**
     * 获取访问数据Url
     *
     * @return
     */
    public static String getDateUrlPath(){
        if("remote".equals(DATA_URL_PATH_SETTING)){
            return DATA_URL_PATH_REMOTE;
        }else {
            return DATA_URL_PATH_LOCAL;
        }
    }

    /**
     * 获取运行环境名称
     *
     * @return
     */
    public static String getEnvName(){
        if("remote".equals(DATA_URL_PATH_SETTING)){
            return "开发环境";
        }else {
            return "线上环境";
        }
    }

    public static String UPDATE_ALL_STOCK_DATA_KEY = "update_stock_market_all_data_last_date";

    public static String UPDATE_STOCK_DATA_KEY = "update_stock_market_data_last_date";

    public static String UPDATE_STOCK_DATA_KEY_THREAD_SIZE = "update_stock_market_data_thread_size";

    public static String STOCK_DAILY_SALE_DATE_KEY = "_sale_daily_date";

    public static String STOCK_DAILY_SALE_DATE_DEFAULT = "2000-01-01";

    public static String DING_DING_URL = "https://oapi.dingtalk.com/robot/send?access_token=c3925258af0b1dd2d3076481e4859c66ca365d29bf620f73da6c484808cd37c5";

    public static String STOCK_MONEY_TO_NORTH = "to_north";

    public static String STOCK_MONEY_TO_SOUTH = "to_south";

    //日志部分

}
