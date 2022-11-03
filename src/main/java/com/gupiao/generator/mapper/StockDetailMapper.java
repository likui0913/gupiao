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

    StockDetail selectNextByCode(@Param("code") String code, @Param("focus") String focus,@Param("industry") String industry);

    StockDetail selectLastByCode(@Param("code") String code, @Param("focus") String focus,@Param("industry") String industry);

    List<StockDetail> selectByCodeAndPages(
            @Param("code") String code,
            @Param("focus") String focus,
            @Param("industry") String industry,
            @Param("limitStart") Integer limitStart);

    List<StockDetail> selectAll();//BaseResultMap

    void updateById(StockDetail stockDetail);

    void updateFocusByCode(
            @Param("code") String code,
            @Param("isFocus") Integer isFocus);


}




