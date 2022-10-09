package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【stock_detail(股票详细信息表)】的数据库操作Mapper
* @createDate 2022-09-11 08:42:13
* @Entity generator.domain.StockDetail
*/
@Mapper
public interface StockDetailMapper {

    void insert(StockDetail stockDetail);

    StockDetail selectByCode(@Param("code") String code);

    List<StockDetail> selectByCodeAndPages(
            @Param("param") String param,
            @Param("limitStart") Integer limitStart);

    List<StockDetail> selectAll();//BaseResultMap

    void updateById(StockDetail stockDetail);

}




