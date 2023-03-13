package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.ImitateTradeData;
import com.gupiao.generator.domain.StockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kuili
* @description 针对表【imitate_trade_data(模拟交易数据)】的数据库操作Mapper
* @createDate 2023-03-13 13:14:39
* @Entity generator.domain.ImitateTradeData
*/
@Mapper
public interface ImitateTradeDataMapper {

    /**
     * 插入
     * @param imitateTradeData
     */
    void insert(ImitateTradeData imitateTradeData);

    /**
     * 更新
     * @param stockDetail
     */
    void updateById(StockDetail stockDetail);

    /**
     * 删除
     * @param id
     */
    void deleteById( @Param("code") Integer id );

}




