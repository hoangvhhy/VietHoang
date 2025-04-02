package model;

import java.util.List;
import model.Customer;
import model.Professional;
import model.Service;

/**
 *
 * @author Gigabyte
 */
public class MedicalExamination {

    private int examinationID;
    private String examinationDate;
    private Customer customerId;
    private String status;
    private Professional consultantId;
    private String note;
    private String createdAt;
    private List<Service> list;

    public MedicalExamination() {
    }

    public MedicalExamination(int examinationID, String examinationDate, Customer customerId, String status, Professional consultantId, String note, String createdAt, List<Service> list) {
        this.examinationID = examinationID;
        this.examinationDate = examinationDate;
        this.customerId = customerId;
        this.status = status;
        this.consultantId = consultantId;
        this.note = note;
        this.createdAt = createdAt;
        this.list = list;
    }
    
    

    public int getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(int examinationID) {
        this.examinationID = examinationID;
    }

    public String getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Professional getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Professional consultantId) {
        this.consultantId = consultantId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Service> getList() {
        return list;
    }

    public void setList(List<Service> list) {
        this.list = list;
    }

}
