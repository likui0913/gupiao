package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockDetail;
import com.gupiao.generator.domain.StockMarketData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
* @author kuili
* @description 针对表【stock_market_data(股票交易数据)】的数据库操作Mapper
* @createDate 2022-09-12 20:36:10
* @Entity generator.domain.StockMarketData
*/
@Mapper
public interface StockMarketDataMapper {

    void insert(StockMarketData stockMarketData);

    StockMarketData selectByCode(@Param("code") String code);

}




