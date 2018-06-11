package com.codingapi.mybaties.provider;

import com.codingapi.mybaties.entity.BaseEntity;
import com.codingapi.mybaties.threadlocal.DbMethodLocal;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lorne
 * @date 2018/5/29
 * @description
 */

public final class SqlMapperProvider {

    public final static String UPDATE = "updateSql";
    public final static String INSERT = "insertSql";
    public final static String FINDALL = "findAllSql";
    public final static String DELETEBYID = "deleteByIdSql";

    public final static String UPDATEWHERE = "updateWhere";

    private final static Logger logger = LoggerFactory.getLogger(SqlMapperProvider.class);

    public final  <T extends BaseEntity> String updateSql(T t) {
        String sql = new SQL().UPDATE(t.loadTableName()).SET(t.loadSetColumns()).WHERE(t.idWhere()).toString();
        logger.debug("updateSql->{}", sql);
        return sql;
    }

    public final  <T extends BaseEntity> String updateWhere(T t) {
        DbMethodLocal methodLocal = DbMethodLocal.current();
        String sql;
        if(methodLocal!=null){
             sql = new SQL().UPDATE(t.loadTableName()).SET(t.loadSetColumns()).WHERE(methodLocal.getWhereSql()).toString();
        }else {
             sql = new SQL().UPDATE(t.loadTableName()).SET(t.loadSetColumns()).WHERE(t.idWhere()).toString();
        }
        logger.debug("updateSql->{}", sql);
        return sql;
    }


    public final <T extends BaseEntity> String insertSql(T t) {
        String sql = new SQL().INSERT_INTO(t.loadTableName()).INTO_COLUMNS(t.loadInsertColumns()).INTO_VALUES(t.loadInsertValues()).toString();
        logger.debug("insertSql->{}", sql);
        return sql;
    }

    public final <T extends BaseEntity> String findAllSql(Class<T> clazz) {
        String sql = new SQL().SELECT("*").FROM(BaseEntity.loadTableName(clazz)).toString();
        logger.debug("findAllSql->{}", sql);
        return sql;
    }

    public final <T extends BaseEntity> String deleteByIdSql(T t) {
        String sql = new SQL().DELETE_FROM(t.loadTableName()).WHERE(t.idWhere()).toString();
        logger.debug("deleteByIdSql->{}", sql);
        return sql;
    }
}
