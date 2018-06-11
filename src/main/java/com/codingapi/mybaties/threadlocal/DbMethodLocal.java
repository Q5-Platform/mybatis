package com.codingapi.mybaties.threadlocal;

/**
 * @author lorne
 * @date 2018/6/11
 * @description
 */
public class DbMethodLocal {


    private final static ThreadLocal<DbMethodLocal> currentLocal = new InheritableThreadLocal<DbMethodLocal>();

    public static DbMethodLocal current() {
        return currentLocal.get();
    }

    public static void setCurrent(DbMethodLocal current) {
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
