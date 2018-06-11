package com.codingapi.mybaties.aspectj;

import com.codingapi.mybaties.annotation.UpdateWhere;
import com.codingapi.mybaties.threadlocal.DbMethodLocal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lorne
 * @date 2018/6/11
 * @description
 */
@Component
@Aspect
public class DbAroundAspectj {

    private final static Logger logger = LoggerFactory.getLogger(DbAroundAspectj.class);

    @Around("@annotation(com.codingapi.mybaties.annotation.UpdateWhere)")
    public Object updateWhereAround(ProceedingJoinPoint point)throws Throwable{
        logger.debug("annotation-updateWhereAround-start---->");
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        UpdateWhere where =  method.getAnnotation(UpdateWhere.class);
        if(where!=null){
            DbMethodLocal dbMethodLocal = new DbMethodLocal();
            dbMethodLocal.setWhereSql(where.value());
            DbMethodLocal.setCurrent(dbMethodLocal);
        }
        Object obj =  point.proceed();
        DbMethodLocal.setCurrent(null);
        logger.debug("annotation-updateWhereAround-end---->");
        return obj;
    }

}
