package com.gupiao.enums;

/**
 * 获取股票交易数据接口网址映射
 */
public enum ApiUrlPath {

    STOCK_SSE_SUMMARY("api/public/stock_sse_summary","上海证券交易所-股票数据总貌"),
    STOCK_SZSE_SUMMARY("api/public/stock_szse_summary","深圳证券交易所-市场总貌-证券类别统计"),
    STOCK_SZSE_AREA_SUMMARY("api/public/stock_szse_area_summary","深圳证券交易所-市场总貌-地区交易排序"),
    STOCK_SZSE_SECTOR_SUMMARY("api/public/stock_szse_sector_summary?date=DATE_PARAMS","深圳证券交易所-统计资料-股票行业成交,参数格式为：202201 "),
    STOCK_SSE_DEAL_DAILY("api/public/stock_sse_deal_daily","上海证券交易所-数据-股票数据-成交概况-股票成交概况-每日股票情况"),
    STOCK_INDIVIDUAL_INFO_EM("api/public/stock_individual_info_em?symbol=CODE_ID","东方财富-个股-股票信息"),
    STOCK_ZH_A_SPOT_EM("api/public/stock_zh_a_spot_em","东方财富网-沪深京 A 股-实时行情数据"),
    STOCK_SH_A_SPOT_EM("api/public/stock_sh_a_spot_em","东方财富网-沪 A 股-实时行情数据"),
    STOCK_SZ_A_SPOT_EM("api/public/stock_sz_a_spot_em","东方财富网-深 A 股-实时行情数据"),
    STOCK_BJ_A_SPOT_EM("api/public/stock_bj_a_spot_em","东方财富网-京 A 股-实时行情数据"),
    STOCK_NEW_A_SPOT_EM("api/public/stock_new_a_spot_em","东方财富网-新股-实时行情数据"),
    STOCK_KC_A_SPOT_EM("api/public/stock_kc_a_spot_em","东方财富网-科创板-实时行情"),
    STOCK_ZH_A_SPOT("api/public/stock_zh_a_spot","新浪财经-沪深京 A 股数据, 重复运行本函数会被新浪暂时封 IP, 建议增加时间间隔"),
    STOCK_ZH_A_HIST("api/public/stock_zh_a_hist?symbol=CODE_ID&period=daily&adjust=hfq&start_date=START_DATE&end_date=END_DATE",
            "东方财富-沪深京 A 股日频率数据; 历史数据按日频率更新, 当日收盘价请在收盘后获取"),
    STOCK_ZH_A_HIST_163("api/public/stock_zh_a_hist_163","网易财经-行情首页-沪深 A 股-每日行情; 该接口主要用户获取流通市值、总市值等指标"),
    STOCK_ZH_A_MINUTE("api/public/stock_zh_a_minute","新浪财经-沪深京 A 股股票或者指数的分时数据，目前可以获取 1, 5, 15, 30, 60 分钟的数据频率, 可以指定是否复权"),
    STOCK_ZH_A_HIST_MIN_EM("api/public/stock_zh_a_hist_min_em","东方财富网-行情首页-沪深京 A 股-每日分时行情; 该接口只能获取近期的分时数据，注意时间周期的设置"),
    STOCK_ZH_A_HIST_PRE_MIN_EM("api/public/stock_zh_a_hist_pre_min_em","东方财富-股票行情-盘前数据"),
    STOCK_ZH_INDEX_SPOT("api/public/stock_zh_index_spot","中国股票指数数据, 注意该股票指数指新浪提供的国内股票指数"),
    STOCK_ZH_INDEX_DAILY("api/public/stock_zh_index_daily","股票指数数据是从新浪财经获取的数据, 历史数据按日频率更新"),
    STOCK_ZH_AH_NAME("api/public/stock_zh_ah_name","单次返回所有 A+H 上市公司的代码和名称字典"),
    STOCK_INFO_SH_NAME_CODE("api/public/stock_info_sh_name_code","上海证券交易所股票代码和简称数据"),
    STOCK_INFO_BJ_NAME_CODE("api/public/stock_info_bj_name_code","北京证券交易所股票代码和简称数据"),
    STOCK_INFO_SZ_NAME_CODE("api/public/stock_info_sz_name_code","深证证券交易所股票代码和股票简称数据"),
    STOCK_INFO_A_CODE_NAME("api/public/stock_info_a_code_name","沪深京三个交易所,沪深京 A 股股票代码和股票简称数据");

    private String path;
    private String desc;

    ApiUrlPath(String p,String d){
        this.path = p;
        this.desc = d;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
