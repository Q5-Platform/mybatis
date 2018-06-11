package com.codingapi.mybaties.db.mapper;

import com.codingapi.mybaties.mapper.BaseMapper;
import com.codingapi.mybaties.provider.SqlMapperProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author lorne
 * @date 2018/5/29
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM T_USER WHERE name = #{name}")
    List<User> findByName(@Param("name") String name);


    @UpdateProvider(type = SqlMapperProvider.class,method = SqlMapperProvider.UPDATEWHERE)
    int newUpdate(User user);



}
