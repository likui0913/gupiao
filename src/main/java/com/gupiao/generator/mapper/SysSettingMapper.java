package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.SysSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kuili
* @description 针对表【sys_setting(配置表)】的数据库操作Mapper
* @createDate 2022-09-14 21:32:47
* @Entity com.gupiao.generator.domain.com.gupiao.generator.SysSetting
*/
@Mapper
public interface SysSettingMapper {

    void insert(SysSetting sysSetting);

    SysSetting selectByCode(@Param("key") String key);

    void updateByKey(@Param("key") String key,@Param("value") String value);

}




