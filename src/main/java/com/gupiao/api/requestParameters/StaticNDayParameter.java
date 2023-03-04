package com.gupiao.api.requestParameters;

import lombok.Data;

@Data
public class StaticNDayParameter {

    private Integer days;

    private Integer showRows;

    private String date;

    private Integer startPrice;

    private Integer endPrice;

    private String trendType;//走势upDays/downDays

    private String trendSort;//desc/asc

    private String priceDiffSort;//desc/asc

    private String turnoverAvgSort;//desc/asc

}
