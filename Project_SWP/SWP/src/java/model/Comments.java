package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comments {

    private int commentId;
    private String senderEmail;
    private String receiveEmail;
    private String commentText;
    private Date date;
    private String topic;
    private Integer replyId;  // Có thể null
    public List<Comments> replies = null;
    private Integer CustomerID; // Có thể null
    private Integer StaffID; // Có thể null

    // Constructor đầy đủ
    public Comments(int commentId, String senderEmail, String receiveEmail, String commentText,
            Date date, String topic, Integer replyId, Integer CustomerID, Integer StaffID) {
        this.commentId = commentId;
        this.senderEmail = senderEmail;
        this.receiveEmail = receiveEmail;
        this.commentText = commentText;
        this.date = date;
        this.topic = topic;
        this.replyId = replyId;
        this.CustomerID = CustomerID;
        this.StaffID = StaffID;
        this.replies = new ArrayList<>();
    }

    // Constructor nếu không có replyId
    public Comments(int commentId, String senderEmail, String receiveEmail, String commentText,
            Date date, String topic, Integer CustomerID, Integer StaffID) {
        this(commentId, senderEmail, receiveEmail, commentText, date, topic, null, CustomerID, StaffID);
    }

    // Getters và Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer CustomerID) {
        this.CustomerID = CustomerID;
    }

    public Integer getStaffID() {
        return StaffID;
    }

    public void setStaffID(Integer StaffID) {
        this.StaffID = StaffID;
    }

    public List<Comments> getReplies() {
        return replies;
    }

    public void setReplies(List<Comments> replies) {
        this.replies = replies;
    }
}
