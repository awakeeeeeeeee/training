package com.training.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class OrderResponse implements Serializable {

    private String code;

    private Double total;

    private String userName;

    private Date creationTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
