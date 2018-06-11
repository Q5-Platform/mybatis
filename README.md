# mybatis

对mybatis @annotation 做了简单的封装。提供BaseEntity 与 BaseMapper


配置文件说明：

```$xslt

#驼峰命名与_转换
mybatis.configuration.map-underscore-to-camel-case=true
#id自动返回，前提id的自增字段只能是id
mybatis.configuration.use-generated-keys=true
#若id字段是其他字段。见参考1


#handler扫码的包路径 见参考2
mybatis.type-handlers-package=com.example.demo.db.mapper.handler

```


使用说明：


1. 每个数据库表类(entity)必须有@Table 和 @Id 注解且必须集成BaseEntity。

引入如下包的(当然本项目的pom下是有的)。

```$xslt

   <dependency>
        <groupId>javax.persistence</groupId>
        <artifactId>persistence-api</artifactId>
        <version>1.0.2</version>
    </dependency>
        
```

代码见参考2 User类


2. 每个Mapper若想使用框架提供的方法，则必须让Mapper集成BaseMapper

代码见参考2 UserMapper类




## 参考1

当insert update 的主键id不是"id"时，可通过若下面的方式实现。

```$xslt

    @Override
    @InsertProvider(type = SqlMapperProvider.class,method = "insertSql")
    @Options(useGeneratedKeys = true,keyProperty = "ids")
    int insert(Test test);
    

```



## 参考2

例如有数据状态字段:0,1 分别表示禁用与正常。

例如User

```$xslt


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.mybaties.entity.BaseEntity;
import com.example.demo.dto.entity.em.DbStateEnum;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lorne
 * @date 2018/5/29
 * @description
 */
@Table(name = "t_user")
public class User extends BaseEntity {

    private Long id;

    private String name;

    private String pwd;

    private Long deportId;

    private DbStateEnum state;

    public DbStateEnum getState() {
        return state;
    }

    public void setState(DbStateEnum state) {
        this.state = state;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getDeportId() {
        return deportId;
    }

    public void setDeportId(Long deportId) {
        this.deportId = deportId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}



/**
 * @author lorne
 * @date 2018/6/9
 * @description
 */
public enum DbStateEnum {

    禁用(0),正常(1);

    private int code;

    DbStateEnum(int code) {
        this.code = code;
    }

    public static DbStateEnum valueOfCode(int code) {
        DbStateEnum[] states = DbStateEnum.values();
        for (DbStateEnum type : states) {
            if (type.getCode() == code) {
                return type;
            }
        }

        return null;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}


```

那么需要定义handler，如下:

```$xslt

package com.example.demo.db.mapper.handler;

import com.example.demo.dto.entity.em.DbStateEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lorne
 * @date 2018/6/9
 * @description
 */
public class DbStateEnumHandler extends BaseTypeHandler<DbStateEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DbStateEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getCode());
    }

    @Override
    public DbStateEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code =  rs.getInt(columnName);
        return DbStateEnum.valueOfCode(code);
    }

    @Override
    public DbStateEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code =  rs.getInt(columnIndex);
        return DbStateEnum.valueOfCode(code);
    }

    @Override
    public DbStateEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return DbStateEnum.valueOfCode(code);
    }
}


```

`mybatis.type-handlers-package=com.example.demo.db.mapper.handler` 用于指定handler的包路径即可。

