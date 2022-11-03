package com.gupiao.api.requestParameters;

import lombok.Data;

@Data
public class StockParameter {

    private String code;

    private String param;

    private String isFocus;

    private String pages;

    private String order;

    private String industry;

}
