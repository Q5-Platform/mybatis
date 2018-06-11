package com.codingapi.mybaties.Interceptor;

import com.codingapi.mybaties.threadlocal.DbUpdateWhereLocal;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author lorne
 * @date 2018/6/11
 * @description
 */

@Intercepts({@Signature(
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
@Component
public class DbUpdateAroundInterceptor implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object obj =  invocation.proceed();
        DbUpdateWhereLocal.setCurrent(null);
        return obj;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }



}
