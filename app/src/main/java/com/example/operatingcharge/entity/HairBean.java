package com.example.operatingcharge.entity;

import java.io.Serializable;

/**
 * Author : yanftch
 * Date : 2018/3/21
 * Time : 10:23
 * Desc :
 */
public class HairBean implements Serializable {

    private String Name;                  // 姓名
    private String Sex;                   // 性别
    private String Position;
    private String Department;            // 部门
    private String ID;
    private String WorkNo;                // 工号
    private String ProjectName;           // 预定项目
    private String OrderDate;             // 预定时间
    private String OrderTime;             // 预定时间段
    private String CurrentNum;
    private String TelePhone;             // 联系电话
    private String IsHandle;              // 状态
    private String Barber_Set;
    private String Barber_Actual;
    private String Grade;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getWorkNo() {
        return WorkNo;
    }

    public void setWorkNo(String workNo) {
        WorkNo = workNo;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

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

    public String getTelePhone() {
        return TelePhone;
    }

    public void setTelePhone(String telePhone) {
        TelePhone = telePhone;
    }

    public String getIsHandle() {
        return IsHandle;
    }

    public void setIsHandle(String isHandle) {
        IsHandle = isHandle;
    }

    public String getBarber_Set() {
        return Barber_Set;
    }

    public void setBarber_Set(String barber_Set) {
        Barber_Set = barber_Set;
    }

    public String getBarber_Actual() {
        return Barber_Actual;
    }

    public void setBarber_Actual(String barber_Actual) {
        Barber_Actual = barber_Actual;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }
}
