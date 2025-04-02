/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author laptop 368
 */
public class ProfessionalLeave {
    private int leaveID;
    private int professionalID;
    private Date leaveDate;
    private String reason;
    private String status;

    public ProfessionalLeave() {
    }

    public ProfessionalLeave(int leaveID, int professionalID, Date leaveDate, String reason, String status) {
        this.leaveID = leaveID;
        this.professionalID = professionalID;
        this.leaveDate = leaveDate;
        this.reason = reason;
        this.status = status;
    }

    public int getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(int leaveID) {
        this.leaveID = leaveID;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProfessionalLeave{" + "leaveID=" + leaveID + ", professionalID=" + professionalID + ", leaveDate=" + leaveDate + ", reason=" + reason + ", status=" + status + '}';
    }

    
}
