package com.database.test.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PrimaryKey implements Serializable {
    private Integer group_id;
    private String email;

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}