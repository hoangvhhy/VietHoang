/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gigabyte
 */
public class Invoice {
    private int invoiceID;
    private MedicalExamination examinationID;
    private double totalAmount;
    private String paymentStatus;
    private String paymentDate;
    private String paymentMethod;
    private String createdAt;
    private Discount discountID;

    public Invoice() {
    }

    public Invoice(int invoiceID, MedicalExamination examinationID, double totalAmount, String paymentStatus, String paymentDate, String paymentMethod, String createdAt, Discount discountID) {
        this.invoiceID = invoiceID;
        this.examinationID = examinationID;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.discountID = discountID;
    }

    public Invoice(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }


    public MedicalExamination getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(MedicalExamination examinationID) {
        this.examinationID = examinationID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Discount getDiscountID() {
        return discountID;
    }

    public void setDiscountID(Discount discountID) {
        this.discountID = discountID;
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceID=" + invoiceID + ", examinationID=" + examinationID + ", totalAmount=" + totalAmount + ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate + ", paymentMethod=" + paymentMethod + ", createdAt=" + createdAt + ", discountID=" + discountID + '}';
    }

    

    
    
}
