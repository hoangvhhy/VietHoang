/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hoang
 */
public class Comment {

    private int comment_id;
    private int post_id;
    private Customer customerID; 
    private Staff staff_id; 
    private String content;
    private int status;
    private String create_at;
    private int parent_comment_id;

    public Comment() {
    }

    public Comment(int comment_id, int post_id, Customer customerID, Staff staff_id, String content, int status, String create_at, int parent_comment_id) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.customerID = customerID;
        this.staff_id = staff_id;
        this.content = content;
        this.status = status;
        this.create_at = create_at;
        this.parent_comment_id = parent_comment_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public Staff getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Staff staff_id) {
        this.staff_id = staff_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public int getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(int parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    @Override
    public String toString() {
        return "Comment{" + "comment_id=" + comment_id + ", post_id=" + post_id + ", customerID=" + customerID + ", staff_id=" + staff_id + ", content=" + content + ", status=" + status + ", create_at=" + create_at + ", parent_comment_id=" + parent_comment_id + '}';
    }
}
