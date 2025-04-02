package model;

import java.sql.Time;

public class WorkingSchedule {

    private int scheduleID;
    private int professionalID;
    private String fullName;
    private int dayOfWeek;
    private Time startTime;
    private Time endTime;
    private String shift;
    private String status;

    // Constructor đầy đủ
    public WorkingSchedule(int scheduleID, int professionalID, int dayOfWeek, Time startTime, Time endTime, String shift) {
        this.scheduleID = scheduleID;
        this.professionalID = professionalID;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shift = shift;
    }

    public WorkingSchedule(int professionalID, String fullName, int dayOfWeek, String shift, Time startTime, Time endTime, String status) {
        this.professionalID = professionalID;
        this.fullName = fullName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shift = shift;
        this.status = status;
    }

    // Constructor không có scheduleID (dùng khi thêm mới)
    public WorkingSchedule(int professionalID, int dayOfWeek, Time startTime, Time endTime, String shift) {
        this.professionalID = professionalID;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shift = shift;
    }

    // không scheduleID, thêm fullName để hiển thị danh sách lịch làm việc
    public WorkingSchedule(int professionalID, String fullName, int dayOfWeek, String shift, Time startTime, Time endTime) {
        this.professionalID = professionalID;
        this.fullName = fullName;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor mặc định
    public WorkingSchedule() {
    }

    // Getters và Setters
    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Phương thức chuyển đổi dayOfWeek thành tên ngày
    public String getDayName() {
        switch (dayOfWeek) {
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            case 8:
                return "Sunday";
            default:
                return "Không xác định";
        }
    }

    @Override
    public String toString() {
        return "WorkingSchedule{" + "scheduleID=" + scheduleID + ", professionalID=" + professionalID + ", fullName=" + fullName + ", dayOfWeek=" + dayOfWeek + ", startTime=" + startTime + ", endTime=" + endTime + ", shift=" + shift + ", status=" + status + '}';
    }

}
