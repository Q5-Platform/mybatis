package com.codingapi.mybaties.mapper;


import com.codingapi.mybaties.entity.BaseEntity;
import com.codingapi.mybaties.provider.SqlMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author lorne
 * @date 2018/5/29
 * @description
 */
public interface BaseMapper<T extends BaseEntity> {

    @SelectProvider(type = SqlMapperProvider.class, method = SqlMapperProvider.FINDALL)
    List<T> findAll(Class<T> clazz);

    @InsertProvider(type = SqlMapperProvider.class, method = SqlMapperProvider.INSERT)
    int insert(T t);

    @UpdateProvider(type = SqlMapperProvider.class, method = SqlMapperProvider.UPDATE)
    int update(T t);

    @UpdateProvider(type = SqlMapperProvider.class, method = SqlMapperProvider.DELETEBYID)
    int deleteById(T t);

}
