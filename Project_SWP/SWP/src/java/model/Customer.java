/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author jaxbo
 */
public class Customer {

    private int customerID;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String accountStatus;
    private String registrationDate;
    private String dateOfBirth;
    private String gender;
    private String profilePicture;

    public Customer() {
    }

    public Customer(int customerID) {
        this.customerID = customerID;
    }

    public Customer(int customerID, String fullName) {
        this.customerID = customerID;
        this.fullName = fullName;
    }
    
    

    public Customer(int customerID, String username, String password, String fullName, String email, String phone, String address, String accountStatus, String registrationDate, String dateOfBirth, String gender, String profilePicture) {
        this.customerID = customerID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.accountStatus = accountStatus;
        this.registrationDate = registrationDate;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerID=" + customerID + ", username=" + username + ", password=" + password + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", address=" + address + ", accountStatus=" + accountStatus + ", registrationDate=" + registrationDate + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", profilePicture=" + profilePicture + '}';
    }

    
    

}