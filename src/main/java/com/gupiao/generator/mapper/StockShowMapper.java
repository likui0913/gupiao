package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockMarketTowDayDiffData;
import com.gupiao.generator.domain.StockShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author kuili
* @description 针对表【stock_show(展示明细批次表)】的数据库操作Mapper
* @createDate 2023-03-04 21:06:16
* @Entity generator.domain.StockShow
*/
@Mapper
public interface StockShowMapper {

    List<StockShow> selectByParameter( @Param("batch") Integer batch );

}




