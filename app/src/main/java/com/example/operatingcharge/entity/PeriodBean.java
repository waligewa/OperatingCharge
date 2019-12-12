package com.example.operatingcharge.entity;

import java.io.Serializable;

/**
 * Author : yanftch
 * Date : 2018/3/21
 * Time : 10:23
 * Desc :
 */
public class PeriodBean implements Serializable {

    private String OrderTime;                  // 姓名
    private String CurrentNum;                 // 性别
    private String ID;

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getCurrentNum() {
        return CurrentNum;
    }

    public void setCurrentNum(String currentNum) {
        CurrentNum = currentNum;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
