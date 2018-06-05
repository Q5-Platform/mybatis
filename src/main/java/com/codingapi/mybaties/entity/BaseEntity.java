package com.codingapi.mybaties.entity;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lorne
 * @date 2018/6/5
 * @description
 */
public class BaseEntity implements Serializable {

    private String generatorName;

    private String idName;

    private List<String> columns;

    private List<PropertyDescriptor> propertys;

    public BaseEntity() {
        columns = new ArrayList<>();
        propertys = new ArrayList<>();

        reloadProperty();
    }

    public final static String getTableName(Class<?> clazz){
        Table table = clazz.getAnnotation(Table.class);
        if(table!=null) {
            return table.name().toUpperCase();
        }else{
            return clazz.getSimpleName().toUpperCase();
        }
    }


    public String getTableName(){
        return getTableName(getClass());
    }

    public String getIdName() {
        return idName;
    }

    private void reloadProperty(){
        PropertyDescriptor[] propertyDescriptors = null;
        try {
            propertyDescriptors = propertyDescriptors(getClass());
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method gmethod = propertyDescriptor.getReadMethod();
                Method smethod = propertyDescriptor.getWriteMethod();

                if (null != gmethod && smethod != null) {

                    Transient aTransient = gmethod.getAnnotation(Transient.class);
                    if (aTransient != null) {
                        continue;
                    }

                    Column column = gmethod.getAnnotation(Column.class);

                    GeneratedValue generator = gmethod.getAnnotation(GeneratedValue.class);
                    if (generator != null) {
                        generatorName = propertyDescriptor.getName();
                    }
                    String columnName;
                    if (column == null||StringUtils.isEmpty(column.name())) {
                        columnName = propertyToColumn(propertyDescriptor.getName());
                    } else {
                        columnName = column.name();
                    }
                    Id id = gmethod.getAnnotation(Id.class);
                    if (id != null) {
                        idName = columnName;
                    }
                    columnName = columnName.toUpperCase();

                    columns.add(columnName);

                    propertys.add(propertyDescriptor);
                }
            }

            if(idName==null){
                throw new RuntimeException(getTableName()+" not exits @Id.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String propertyToColumn(String propertyName) {
        char[] chs = propertyName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];
            int index = c;
            if (index < 97) {
                sb.append("_");
                sb.append((char) (c + 32));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private PropertyDescriptor[] propertyDescriptors(Class<?> c) throws SQLException {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(c);
        } catch (IntrospectionException var4) {
            throw new SQLException("Bean introspection failed: " + var4.getMessage());
        }
        return beanInfo.getPropertyDescriptors();
    }


    public String[] getInsertColumns(){
        List<String> list =  new ArrayList<>();
        for(int i=0;i<propertys.size();i++) {
            PropertyDescriptor propertyDescriptor = propertys.get(i);
            String propertyName = propertyDescriptor.getName();
            if(propertyName.equals(generatorName)){
                continue;
            }
            list.add(columns.get(i));
        }
        return list.toArray(new String[list.size()]);
    }


    public String[] getInsertValues(){
        List<String> list =  new ArrayList<>();
        for(int i=0;i<propertys.size();i++) {
            PropertyDescriptor propertyDescriptor = propertys.get(i);
            String propertyName = propertyDescriptor.getName();
            if(propertyName.equals(generatorName)){
                continue;
            }
            list.add(String.format("#{%s}",propertyName));
        }
        return list.toArray(new String[list.size()]);
    }

    public String[] getSetColumns(){
        List<String> list =  new ArrayList<>();
        for(int i=0;i<propertys.size();i++){
            PropertyDescriptor propertyDescriptor =  propertys.get(i);
            String propertyName = propertyDescriptor.getName();
            if(propertyName.equals(getIdName())){
                continue;
            }
            try {
                Object obj = propertyDescriptor.getReadMethod().invoke(this);
                if(obj!=null){
                    list.add(String.format("%s=#{%s}",propertyName,propertyName));
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list.toArray(new String[list.size()]);
    }


    public String idWhere(){
        return String.format("%s=#{%s}",idName,idName);
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<PropertyDescriptor> getPropertys() {
        return propertys;
    }
}
