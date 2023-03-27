package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.StockDetailOrderAll;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockDetailOrderAllMapper {

    void batchInsert(List<StockDetailOrderAll> StockDetailOrderAllDatas);

}
