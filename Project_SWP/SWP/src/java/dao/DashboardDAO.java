/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.io.InputStream;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import model.Customer;
import model.Professional;

/**
 *
 * @author Hoang
 */
public class DashboardDAO extends DBContext {

    public List<Customer> getNewCustomer() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT TOP 4 *\n"
                + "FROM Customer where accountStatus = 'Active'\n"
                + "ORDER BY registrationDate DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String accountStatus = rs.getString("accountStatus");
                String registrationDate = rs.getString("registrationDate");
                String dateOfBirth = rs.getString("dateOfBirth");
                String gender = rs.getString("gender");
                String profilePicture = rs.getString("profilePicture");

                Customer customer = new Customer(customerID, username, password, fullName, email, phone, address,
                        accountStatus, registrationDate, dateOfBirth, gender, profilePicture);
                customers.add(customer); // Thêm customer vào danh sách
            }

        } catch (Exception e) {
        }
        return customers;
    }

    public int countPendingExaminations() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS PendingCount FROM MedicalExamination WHERE status = 'Pending'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("PendingCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        DashboardDAO dao = new DashboardDAO();
        int pendingCount = dao.countPendingExaminations();
        System.out.println("Số lịch khám đang chờ xử lý: " + pendingCount);
    }
}
