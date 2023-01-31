package com.gupiao.generator.mapper;

import generator.domain.StockDailyAverageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【stock_daily_average_data(日均线数据)】的数据库操作Mapper
* @createDate 2022-11-08 22:03:06
* @Entity generator.domain.StockDailyAverageData
*/
@Mapper
public interface StockDailyAverageDataMapper {

    void insert(StockDailyAverageData stockDailyAverageData);

    void batchInsert(List<StockDailyAverageData> stockDailyAverageDatas);

    List<StockDailyAverageData> selectByCodeAdaysADates(
            @Param("stockCode")String stockCode,
            @Param("days")Integer days,
            @Param("startDate")String startDate,
            @Param("endDate")String endDate);

    StockDailyAverageData selectByCodeAdaysADate(
            @Param("stockCode")String stockCode,
            @Param("days")Integer days,
            @Param("date")String date);

}




