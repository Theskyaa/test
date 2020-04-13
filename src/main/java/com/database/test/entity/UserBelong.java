package com.database.test.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userbelong")
public class UserBelong {

    @EmbeddedId
    @Column(name = "email")
    private String email;

    @Column(name = "group_id")
    private Integer groupId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
