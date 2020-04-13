package com.database.test.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grouplist")
public class GroupList {
    @Id
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_founder")
    private String groupFounder;

    @Column(name = "group_foundingtime")
    private String groupFoundingTime;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupFounder() {
        return groupFounder;
    }

    public void setGroupFounder(String groupFounder) {
        this.groupFounder = groupFounder;
    }

    public String getGroupFoundingTime() {
        return groupFoundingTime;
    }

    public void setGroupFoundingTime(String groupFoundingTime) {
        this.groupFoundingTime = groupFoundingTime;
    }
}
