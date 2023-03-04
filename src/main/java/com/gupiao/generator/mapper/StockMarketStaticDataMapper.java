package com.gupiao.generator.mapper;
import com.gupiao.generator.domain.StockMarketStaticData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【stock_market_static_data(股票交易统计数据)】的数据库操作Mapper
* @createDate 2023-02-09 21:49:59
* @Entity com.gupiao.generator.domain.StockMarketStaticData
*/
@Mapper
public interface StockMarketStaticDataMapper{

    void insert(StockMarketStaticData stockMarketData);

    void batchInsert(List<StockMarketStaticData> records);

    StockMarketStaticData selectByCode(
            @Param("code") String code,
            @Param("tradeDate") String tradeDate);

    StockMarketStaticData selectMaxDate(
            @Param("code") String code);


    /**
     * 根据参数，查询需要提醒的股票信息
     * @param days
     * @param tradeDate
     * @param startPrice
     * @param endPrice
     * @param limited
     * @return
     */
    List<StockMarketStaticData> selectByParameter(
            @Param("days") Integer days,
            @Param("tradeDate") String tradeDate,
            @Param("startPrice") Integer startPrice,
            @Param("endPrice") Integer endPrice,
            @Param("limited") Integer limited,
            @Param("trendSort") String trendSort,
            @Param("priceDiffSort") String priceDiffSort,
            @Param("turnoverAvgSort") String turnoverAvgSort);

}




