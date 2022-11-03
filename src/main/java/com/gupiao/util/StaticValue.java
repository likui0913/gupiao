package com.gupiao.util;

/**
 * 常量类
 */
public class StaticValue {

    /**
     * 获取股票数据网址根路径信息
     */
    public static String DATA_URL_PATH = "http://118.190.147.193:8080/";
    //public static String DATA_URL_PATH = "http://127.0.0.1:8080/";

    public static String UPDATE_ALL_STOCK_DATA_KEY = "update_stock_market_all_data_last_date";

    public static String UPDATE_STOCK_DATA_KEY = "update_stock_market_data_last_date";

    public static String UPDATE_STOCK_DATA_KEY_THREAD_SIZE = "update_stock_market_data_thread_size";

    public static String STOCK_DAILY_SALE_DATE_KEY = "_sale_daily_date";

    public static String STOCK_DAILY_SALE_DATE_DEFAULT = "2000-01-01";

    public static String DING_DING_URL = "https://oapi.dingtalk.com/robot/send?access_token=c3925258af0b1dd2d3076481e4859c66ca365d29bf620f73da6c484808cd37c5";

    public static String STOCK_MONEY_TO_NORTH = "to_north";

    public static String STOCK_MONEY_TO_SOUTH = "to_south";

}
