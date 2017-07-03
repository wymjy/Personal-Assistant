package com.zucc.wl1145_mjy1136.personalassistant.db;

/**
 * Created by mjy on 2017/7/2.
 */

public class MyCalendar {
    private String calendarNo; //id
    private String calendarName;//标题
    private String date;
    private String time;
    private String place; //地点
    private String description;//描述
    private String repetition; //提醒次数
    private String advanceTime;//提醒时间
    private String valid;


    public String getCalendarNo() {
        return calendarNo;
    }
    public void setCalendarNo(String calendarNo) {
        this.calendarNo = calendarNo;
    }
    public String getCalendarName() {
        return calendarName;
    }
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRepetition() {
        return repetition;
    }
    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }
    public String getAdvanceTime() {
        return advanceTime;
    }
    public void setAdvanceTime(String advanceTime) {
        this.advanceTime = advanceTime;
    }
    public String isValid() {
        return valid;
    }
    public void setValid(String valid) {
        this.valid = valid;
    }
}