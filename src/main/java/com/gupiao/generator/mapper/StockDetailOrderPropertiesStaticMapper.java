package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockDetailOrderPropertiesStatic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockDetailOrderPropertiesStaticMapper {

    void insert(StockDetailOrderPropertiesStatic propertiesStatic);

}
