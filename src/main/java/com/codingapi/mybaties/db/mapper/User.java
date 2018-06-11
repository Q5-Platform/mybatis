package com.codingapi.mybaties.db.mapper;

import com.codingapi.mybaties.entity.BaseEntity;

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


}
