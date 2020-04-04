package com.database.test.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "publishinghouse")
public class PublishingHouse {

    @Id
    @Column(name = "house_id")
    private Integer houseId;

    @Column(name = "house_name")
    private String houseName;

    @Column(name = "house_address")
    private String houseAddress;

    @Column(name = "house_telephone")
    private String houseTelephone;

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getHouseTelephone() {
        return houseTelephone;
    }

    public void setHouseTelephone(String houseTelephone) {
        this.houseTelephone = houseTelephone;
    }
}
