/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class MedicalRecord {
    
    
    private int examinationID;
    private int recordID;
    private String diagnosis;
    private String treatmentPlan;
    private String medicationsPrescribed;
    private String createdAt; // Sử dụng String để hiển thị định dạng dd/MM/yyyy HH:mm
    private String notes;
    private String doctorName;
    private String phone;

    public MedicalRecord() {
    }

    public MedicalRecord(int examinationID, String diagnosis, String treatmentPlan, String medicationsPrescribed, String createdAt, String notes) {
        this.examinationID = examinationID;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.medicationsPrescribed = medicationsPrescribed;
        this.createdAt = createdAt;
        this.notes = notes;
    }
    public MedicalRecord(int examinationID,int recordID, String diagnosis, String treatmentPlan, String medicationsPrescribed, String notes) {
        this.examinationID = examinationID;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.medicationsPrescribed = medicationsPrescribed;
        this.createdAt = createdAt;
        this.notes = notes;
        this.recordID=recordID;
        
    }
    public MedicalRecord(int examinationID,int recordID, String diagnosis, String treatmentPlan, String medicationsPrescribed, String notes, String name, String phone) {
        this.examinationID = examinationID;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.medicationsPrescribed = medicationsPrescribed;
        this.createdAt = createdAt;
        this.notes = notes;
        this.recordID=recordID;
        this.doctorName=name;
        this.phone = phone;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(int examinationID) {
        this.examinationID = examinationID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getMedicationsPrescribed() {
        return medicationsPrescribed;
    }

    public void setMedicationsPrescribed(String medicationsPrescribed) {
        this.medicationsPrescribed = medicationsPrescribed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" + "examinationID=" + examinationID + ", diagnosis=" + diagnosis + ", treatmentPlan=" + treatmentPlan + ", medicationsPrescribed=" + medicationsPrescribed + ", createdAt=" + createdAt + ", notes=" + notes + '}';
    }
    
    
    
    
}
