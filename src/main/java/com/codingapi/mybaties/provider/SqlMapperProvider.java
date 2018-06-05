package com.codingapi.mybaties.provider;

import com.codingapi.mybaties.entity.BaseEntity;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lorne
 * @date 2018/5/29
 * @description
 */

public class SqlMapperProvider<T extends BaseEntity> {

    private final static Logger logger = LoggerFactory.getLogger(SqlMapperProvider.class);

    public String updateSql(T t) {
        String sql = new SQL().UPDATE(t.getTableName()).SET(t.getSetColumns()).WHERE(t.idWhere()).toString();
        logger.debug("updateSql->{}", sql);
        return sql;
    }

    public final String insertSql(T t) {
        String sql = new SQL().INSERT_INTO(t.getTableName()).INTO_COLUMNS(t.getInsertColumns()).INTO_VALUES(t.getInsertValues()).toString();
        logger.debug("insertSql->{}", sql);
        return sql;
    }

    public final String findAllSql(Class<? extends BaseEntity> clazz) {
        String sql = new SQL().SELECT("*").FROM(BaseEntity.getTableName(clazz)).toString();
        logger.debug("findAllSql->{}", sql);
        return sql;
    }

    public final String deleteByIdSql(T t) {
        String sql = new SQL().DELETE_FROM(t.getTableName()).WHERE(t.idWhere()).toString();
        logger.debug("updatesql->{}", sql);
        logger.debug("deleteByIdSql->{}", sql);
        return sql;
    }
}
