package com.gupiao.bean;

import lombok.Data;

/**
 * 股票元数据
 */
@Data
public class StockBean {

    /**
     * 股票代码
     */
    protected String code;

    /**
     * 股票名称
     */
    protected String name;

}
