package com.codingapi.mybaties.threadlocal;

/**
 * @author lorne
 * @date 2018/6/11
 * @description
 */
public class DbUpdateWhereLocal {


    private final static ThreadLocal<DbUpdateWhereLocal> currentLocal = new InheritableThreadLocal<DbUpdateWhereLocal>();

    public static DbUpdateWhereLocal current() {
        return currentLocal.get();
    }

    public static void setCurrent(DbUpdateWhereLocal current) {
        currentLocal.set(current);
    }


    private String whereSql;



    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }
}
