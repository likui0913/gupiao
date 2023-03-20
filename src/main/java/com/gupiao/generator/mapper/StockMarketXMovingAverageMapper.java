package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketXMovingAverage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kuili
* @description 针对表【stock_market_x_moving_average(股票n日线统计数据)】的数据库操作Mapper
* @createDate 2022-09-18 21:19:52
* @Entity com.gupiao.generator.domain.StockMarketXMovingAverage
*/
@Mapper
public interface StockMarketXMovingAverageMapper {

    void insert(StockMarketXMovingAverage stockMarketXMovingAverage);

    StockMarketXMovingAverage selectByCodeAndDate(@Param("code") String code,@Param("tradeDate") String tradeDate);


}




