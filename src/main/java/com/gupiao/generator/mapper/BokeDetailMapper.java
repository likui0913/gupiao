package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.BokeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuili
* @description 针对表【boke_detail(博客明细表)】的数据库操作Mapper
* @createDate 2022-11-05 16:47:10
* @Entity generator.domain.BokeDetail
*/
@Mapper
public interface BokeDetailMapper {

    void insert(BokeDetail bokeDetail);

    List<BokeDetail> selectByTitleType(@Param("titleType")Integer titleType);

    BokeDetail selectByID(@Param("id")Long id);

    BokeDetail selectNextByID(@Param("id")Long id);

}




