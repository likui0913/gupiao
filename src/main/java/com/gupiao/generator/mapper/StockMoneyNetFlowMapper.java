package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockMarketData;
import com.gupiao.generator.domain.StockMoneyNetFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【stock_money_net_flow】的数据库操作Mapper
* @createDate 2022-10-23 15:50:17
* @Entity generator.domain.StockMoneyNetFlow
*/
@Mapper
public interface StockMoneyNetFlowMapper{

    List<StockMoneyNetFlow> selectByDate(@Param("flowDate") String flowDate);

    List<StockMoneyNetFlow> selectByDateAndDateRange(
            @Param("flowType") String flowType,
            @Param("flowStartDate") String flowStartDate,
            @Param("flowEndDate") String flowEndDate);

    List<StockMoneyNetFlow> selectByTypeAndDate(@Param("flowType") String flowType, @Param("flowDate") String flowDate);

    StockMoneyNetFlow selectMaxRowByType(@Param("flowType") String flowType);

    void batchInsert(List<StockMoneyNetFlow> records);

}




