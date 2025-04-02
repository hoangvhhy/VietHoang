/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hoang
 */
public class Feedback {

    private int feedbackID;
    private Invoice invoice;
    private int rating;
    private String comment;
    private String date;

    public Feedback() {
    }

    public Feedback(int feedbackID, Invoice invoice, int rating, String comment, String date) {
        this.feedbackID = feedbackID;
        this.invoice = invoice;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    

    @Override
    public String toString() {
        return "Feedback{"
                + "feedbackID=" + feedbackID
                + ", invoiceID=" + (invoice != null ? invoice.getInvoiceID() : "null")
                + ", customerName=" + (invoice != null && invoice.getExaminationID() != null
                && invoice.getExaminationID().getCustomerId() != null
                ? invoice.getExaminationID().getCustomerId().getFullName() : "Unknown")
                + ", rating=" + rating
                + ", comment='" + comment + '\''
                + ", date='" + date + '\''
                + '}';
    }

}
