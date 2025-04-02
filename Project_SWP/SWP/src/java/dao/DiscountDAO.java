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
import model.Discount;

/**
 *
 * @author Gigabyte
 */
public class DiscountDAO extends DBContext{
    public List<Discount> getAllDiscountStatus() {
        List<Discount> discountList = new ArrayList<>();
        String sql = "SELECT * FROM Discount where status=N'Active'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int discountID = rs.getInt("discountID");
                String discountName= rs.getString("discountName");
                double percentage = rs.getDouble("percentage");
                String status = rs.getString("status");
                Discount dis = new Discount(discountID, discountName,percentage, status);
                discountList.add(dis);
            }

        } catch (SQLException e) {
        }
        return discountList; // Trả về danh sách khách hàng
    }
    
    public List<Discount> getAllDiscount() {
        List<Discount> discountList = new ArrayList<>();
        String sql = "SELECT * FROM Discount";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int discountID = rs.getInt("discountID");
                String discountName= rs.getString("discountName");
                double percentage = rs.getDouble("percentage");
                String status = rs.getString("status");
                Discount dis = new Discount(discountID, discountName,percentage, status);
                discountList.add(dis);
            }

        } catch (SQLException e) {
        }
        return discountList; // Trả về danh sách khách hàng
    }
    
    public Discount getDiscountByID(int discountID) {
        String sql = "select * from Discount where discountID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, discountID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Discount(
                        rs.getInt("discountID"),
                        rs.getString("discountName"),
                        rs.getDouble("percentage"),
                        rs.getString("status"));            
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void addDiscount(String discountName, double percentage, String status) {
        String sql = "insert Discount(discountName,percentage,status) values (?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, discountName);
            st.setDouble(2, percentage);
            st.setString(3, status);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public void updateDiscount(int discountID, String discountName, double percentage, String status) {
        String sql = "update Discount set discountName = ?, percentage = ?, status = ? where discountID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, discountName);
            st.setDouble(2, percentage);
            st.setString(3, status);
            st.setInt(4, discountID);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public void deleteDiscount(int discountID) {
        String sql = "delete  from Discount where discountID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, discountID);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public static void main(String[] args) {
        DiscountDAO disDAO = new DiscountDAO();
        System.out.println(disDAO.getAllDiscount().get(0).getDiscountName());
    }
    
}
