package com.gupiao.enums;

/**
 * 日志开关名称
 */
public enum LogSwitchEnums {

    STOCK_MSG("stock_msg_log_switch","更新基础信息"),
    STOCK_TRADE("stock_trade_log_switch","更新历史交易信息"),
    STOCK_STATIC("stock_static_log_switch","更新统计信息"),
    STOCK_TRADE_RUNTIME("stock_trade_runtime_log_switch","更新实时交易数据信息"),
    STOCK_STATIC_TWO_DAY("stock_static_two_day_log_switch","更新统计两段统计信息"),
    STOCK_DETAIL_ORDER_ALL("stock_detail_order_all_log_switch","更新股票交易明细数据"),
    ;


    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    LogSwitchEnums(String n, String d) {
        this.name = n;
        this.desc = d;
    }

}
