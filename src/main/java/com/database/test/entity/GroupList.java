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

    @Column(name = "group_introduction")
    private String groupIntroduction;

    @Column(name = "group_number")
    private Integer groupNumber;

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

    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public void setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }
}
