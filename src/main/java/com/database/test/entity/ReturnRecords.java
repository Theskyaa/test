package com.database.test.entity;


import com.sun.xml.internal.ws.server.ServerRtException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "returnrecord")
public class ReturnRecords {

    @Id
    @Column(name = "return_id")
    private Integer returnID;

    @Column(name = "return_time")
    private String returnTime;

    public Integer getReturnID() {
        return returnID;
    }

    public void setReturnID(Integer returnID) {
        this.returnID = returnID;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
}
