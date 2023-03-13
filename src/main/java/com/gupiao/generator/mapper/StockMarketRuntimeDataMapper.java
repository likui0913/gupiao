package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockMarketRuntimeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【stock_market_runtime_data(code实时交易数据)】的数据库操作Mapper
* @createDate 2023-03-10 15:42:45
* @Entity generator.domain.StockMarketRuntimeData
*/
@Mapper
public interface StockMarketRuntimeDataMapper {

    void batchInsert(List<StockMarketRuntimeData> records);

    void deleteByDate(@Param("date") String date);

    Integer selectCountByDate(@Param("date") String date);

}




