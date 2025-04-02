/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import util.ValidFunction;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Staff;
import java.sql.Blob;

/**
 *
 * @author jaxbo
 */
public class StaffDAO extends DBContext {

    public Staff staffLogin(String email) {
        String sql = "select * from Staff where email = ?";
        ValidFunction valid = new ValidFunction();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                return new Staff(
                        rs.getInt("staffID"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("address"),
                        rs.getString("hireDate"),
                        rs.getInt("roleID"),
                        rs.getString("status"),
                        profilePicture);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Staff> getStaffs(int start, int total) {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM Staff ORDER BY staffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, start);
            st.setInt(2, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                staffList.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return staffList; // Trả về danh sách khách hàng
    }

    public Staff getStaffByID(int staffID) {
        String sql = "select * from Staff where staffID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, staffID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                return new Staff(
                        rs.getInt("staffID"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("address"),
                        rs.getString("hireDate"),
                        rs.getInt("roleID"),
                        rs.getString("status"),
                        profilePicture);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateStaff(int staffID, String fullName, String email, String phone, String hireDate, int roleID, String status) {
        String sql = "update staff set fullName = ?, email = ?, phone= ?, hireDate = CONVERT(DATETIME, ?, 103), roleID = ?, status = ? where staffID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, fullName);
            pre.setString(2, email);
            pre.setString(3, phone);
            pre.setString(4, hireDate);
            pre.setInt(5, roleID);
            pre.setString(6, status);
            pre.setInt(7, staffID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateStaffInfo(int staffID, String fullName, String email, String phone, String dateOfBirth, String gender, String address,InputStream profilePicture) {
        String sql;
        if (profilePicture != null) {
            sql = "update staff set fullName = ?, email = ?, phone= ?, dateOfBirth = CONVERT(DATETIME, ?, 103), gender = ?, address = ?,profilePicture=? where staffID = ?";
        } else {
            sql = "update staff set fullName = ?, email = ?, phone= ?, dateOfBirth = CONVERT(DATETIME, ?, 103), gender = ?, address = ? where staffID = ?";
        }
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, fullName);
            pre.setString(2, email);
            pre.setString(3, phone);
            pre.setString(4, dateOfBirth);
            pre.setString(5, gender);
            pre.setString(6, address);
            if (profilePicture != null) {
                pre.setBlob(7, profilePicture);
                pre.setInt(8, staffID);
            } else {
                pre.setInt(7, staffID);
            }
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Staff> getStaffByName(String name) {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "select * from staff where fullName like ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng

    }

    public List<Staff> getStaffByRole(int number_roleID) {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "select * from staff where roleID = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, number_roleID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng

    }

    public List<Staff> getStaffByNameandRole(int number_roleID, String name) {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "select * from staff where fullName like ? and roleID = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setInt(2, number_roleID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng

    }

    public List<Staff> getStaffByNameandRolePaging(int role, String name, int start, int total) {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "select * from staff where fullName like ? and roleID = ? ORDER BY staffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setInt(2, role);
            st.setInt(3, start);
            st.setInt(4, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng

    }

    public List<Staff> getStaffByNamePaging(String name, int start, int total) {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "select * from staff where fullName like ? ORDER BY staffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setInt(2, start);
            st.setInt(3, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng

    }

    public List<Staff> getStaffByRolePaging(int number_roleID, int start, int total) {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "select * from staff where roleID = ? ORDER BY staffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, number_roleID);
            st.setInt(2, start);
            st.setInt(3, total);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng

    }

    public void deleteByStaffID(int staffID) {
        String sql = "delete from staff where staffID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, staffID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> listStaff = new ArrayList<>();
        String sql = "SELECT * FROM Staff";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                byte[] profilePicture = (blob != null) ? blob.getBytes(1, (int) blob.length()) : null; // Chuyển BLOB thành byte[]

                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("fullName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String hireDate = rs.getString("hireDate");
                int roleID = rs.getInt("roleID");
                String status = rs.getString("status");
                Staff staff = new Staff(staffID, fullName, email, password, phone, gender, dateOfBirth, address, hireDate, roleID, status, profilePicture);
                listStaff.add(staff); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listStaff; // Trả về danh sách khách hàng
    }

    public void createStaff(String fullName, String email, String password, String phone, String hireDate, int roleID, String status) {
        String sql = "INSERT [dbo].[Staff] ( [fullName], [email], [password], [phone], [hireDate], [roleID], [status], [profilePicture]) VALUES ( ?, ?, ?, ?, CONVERT(DATETIME, ?, 103), ?, ?, NULL)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, fullName);
            st.setString(2, email);
            st.setString(3, password);
            st.setString(4, phone);
            st.setString(5, hireDate);
            st.setInt(6, roleID);
            st.setString(7, status);

            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public boolean checkOldPassword(int staffID, String oldPassword) {
        String sql = "SELECT password FROM Staff WHERE staffID = ?";
        ValidFunction valid = new ValidFunction();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, staffID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Here you may want to hash 'oldPassword' and compare with 'storedPassword'.
                    // Assuming passwords are stored as plain text (not recommended), we directly compare.
                    return valid.checkPassword(oldPassword, storedPassword);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking old password: " + e.getMessage());
        }

        return false; // return false if customer not found or any error occurs
    }

    public void changeStaffPassword(int staffID, String password) {
        String sql = "UPDATE Staff SET password = ? WHERE staffID = ?";
        ValidFunction valid = new ValidFunction();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, valid.hashPassword(password));
            st.setInt(2, staffID);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        System.out.println(dao.getStaffByID(2).getPassword());
    }
}
