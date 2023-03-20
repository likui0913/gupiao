package com.gupiao.generator.mapper;

import com.gupiao.generator.domain.SysSetting;
import com.gupiao.generator.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kuili
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2022-10-10 21:45:06
* @Entity com.gupiao.generator.domain.SysUser
*/
@Mapper
public interface SysUserMapper {

    void insert(SysUser sysUser);

    SysUser selectByUserName(@Param("userName") String userName);

    void deleteByUser(@Param("userName") String userName);

}




