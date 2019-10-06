package com.example.operatingcharge.entity;

import java.io.Serializable;

/**
 * Author : yanftch
 * Date : 2018/3/21
 * Time : 10:23
 * Desc :
 */
public class HairBean implements Serializable {

    private String username;              // 姓名
    private String sex;                   // 性别
    private String workNumber;            // 工号
    private String apartment;             // 部门
    private String bookProject;           // 预定项目
    private String time;                  // 预定时间
    private String phone;                 // 联系电话
    private String state;                 // 状态

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBookProject() {
        return bookProject;
    }

    public void setBookProject(String bookProject) {
        this.bookProject = bookProject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
