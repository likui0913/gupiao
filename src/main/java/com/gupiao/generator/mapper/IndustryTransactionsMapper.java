package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.IndustryTransactions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kuili
* @description 针对表【industry_transactions(行业交易信息)】的数据库操作Mapper
* @createDate 2022-09-12 15:21:03
* @Entity generator.domain.IndustryTransactions
*/
@Mapper
public interface IndustryTransactionsMapper {

    void insert(IndustryTransactions stockDetail);

    IndustryTransactions selectByCode(@Param("tradeDate") String tradeDate,@Param("name") String name);


}




