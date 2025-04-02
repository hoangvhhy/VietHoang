/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Professional;
import model.Service;

/**
 *
 * @author Hoang
 */
public class HomePageDAO extends DBContext {

    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        return new Service(
                rs.getInt("packageID"),
                rs.getString("packageName"),
                rs.getString("description"),
                rs.getBytes("service_image"),
                rs.getString("type"),
                rs.getDouble("price"),
                rs.getInt("duration"),
                rs.getString("status"),
                rs.getString("createdAt")
        );
    }
    
    public List<Service> get4Service() {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT top 4* FROM ServicePackage";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                serviceList.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> get6Service() {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT top 6* FROM ServicePackage";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                serviceList.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Professional> getAllDoctors() {
        List<Professional> list = new ArrayList<>();
        String sql = "SELECT s.staffID, s.fullName, s.email,s.password,s.phone,s.gender,s.dateOfBirth, s.address,s.hireDate,s.roleID,\n"
                + "s.status AS staffStatus,s.profilePicture AS staffProfilePicture,p.professionalID,p.specialization,p.officeHours,p.qualification,\n"
                + "p.biography,p.profilePicture AS professionalProfilePicture,p.status AS professionalStatus,p.createdAt\n"
                + "FROM Staff s\n"
                + "JOIN Professional p ON s.staffID = p.staffID\n"
                + "WHERE p.status = 'Active'"; // Chỉ lấy bác sĩ có trạng thái Active

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Professional doctor = extractProfessional(rs);
                list.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Professional extractProfessional(ResultSet rs) throws SQLException {
        byte[] staffProfilePic = rs.getBytes("staffProfilePicture");
        byte[] professionalProfilePic = rs.getBytes("professionalProfilePicture");
        return new Professional(
                rs.getInt("staffID"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDate("dateOfBirth"),
                rs.getString("gender"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getDate("hireDate"),
                rs.getString("staffStatus"),
                professionalProfilePic != null ? professionalProfilePic : new byte[0], // Tránh lỗi NULL
                rs.getString("specialization"),
                rs.getString("officeHours"),
                rs.getString("qualification"),
                rs.getString("biography"),
                rs.getDate("createdAt"),
                rs.getInt("roleId")
        );
    }

    public static void main(String[] args) {
        HomePageDAO dao = new HomePageDAO();
//        List<Service> list = dao.get4Service();
//        for (Service service : list) {
//            System.out.println(service);
//        }

        List<Professional> list = dao.getAllDoctors();
        for (Professional doc : list) {
            System.out.println("🆔 ID: " + doc.getStaffID()
                    + " | ?‍⚕️ Tên: " + doc.getFullName()
                    + " | 📞 SĐT: " + doc.getPhone()
                    + " | 🎓 Chuyên môn: " + doc.getSpecialization());
        }
    }
}
