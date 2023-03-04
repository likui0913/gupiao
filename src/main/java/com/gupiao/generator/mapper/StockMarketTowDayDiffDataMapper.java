package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockMarketStaticData;
import com.gupiao.generator.domain.StockMarketTowDayDiffData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author kuili
* @description 针对表【stock_market_tow_day_diff_data(统计两个时间间隔内价格差异和百分比，判断走势)】的数据库操作Mapper
* @createDate 2023-02-27 22:46:46
* @Entity generator.domain.StockMarketTowDayDiffData
*/
@Mapper
public interface StockMarketTowDayDiffDataMapper {

    void insert(StockMarketTowDayDiffData stockMarketTowDayDiffData);

    StockMarketTowDayDiffData selectMaxDate( @Param("code") String code );

    List<StockMarketTowDayDiffData> selectByParameter(
            @Param("tradeDate") String tradeDate,
            @Param("startPrice") BigDecimal startPrice,
            @Param("endPrice") BigDecimal endPrice,
            @Param("daysX") Integer daysX,
            @Param("daysY") Integer daysY,
            @Param("closingPriceDiffXStart") BigDecimal closingPriceDiffXStart,
            @Param("closingPriceDiffXEnd") BigDecimal closingPriceDiffXEnd,
            @Param("closingPriceDiffYStart") BigDecimal closingPriceDiffYStart,
            @Param("closingPriceDiffYEnd") BigDecimal closingPriceDiffYEnd,
            @Param("closingPriceDiffXSort") String closingPriceDiffXSort,
            @Param("closingPriceDiffYSort") String closingPriceDiffYSort
            );

}




