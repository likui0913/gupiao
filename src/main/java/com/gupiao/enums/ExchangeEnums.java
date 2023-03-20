package com.gupiao.enums;

/**
 * 交易所分类
 */
public enum ExchangeEnums {

    //上海证券交易所: sh, 深证证券交易所: sz, 北京证券交易所: bj
    SH("sh","上海证券交易所"),SZ("sz","深证证券交易所"),BJ("bj","北京证券交易所");

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

    ExchangeEnums(String n, String d) {
        this.name = n;
        this.desc = d;
    }

}
