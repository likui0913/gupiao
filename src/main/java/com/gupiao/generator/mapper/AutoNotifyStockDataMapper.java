package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.AutoNotifyStockData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【auto_notify_stock_data(需要自动发送的数据)】的数据库操作Mapper
* @createDate 2023-03-20 15:37:07
* @Entity generator.domain.AutoNotifyStockData
*/
@Mapper
public interface AutoNotifyStockDataMapper {

    public void batchInsert(List<AutoNotifyStockData> stockDailyAverageDatas);

    public List<AutoNotifyStockData> selectByMaxId(@Param("id")Long id);

}




