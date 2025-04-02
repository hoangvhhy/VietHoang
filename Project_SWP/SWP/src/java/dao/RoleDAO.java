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
import static java.util.Collections.list;
import java.util.List;
import model.Permission;
import model.Role;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
public class RoleDAO extends DBContext {

    public List<Role> getAllRole() {
        PermissionDAO dao = new PermissionDAO();
        List<Role> listRole = new ArrayList<>();
        String sql = "SELECT * FROM role";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int roleID = rs.getInt("roleID");
                String roleName = rs.getString("roleName");
                String description = rs.getString("description");
                List<Permission> list = dao.getPermissionByRoleID(roleID);
                Role role = new Role(roleID, roleName, description, list);
                listRole.add(role);
            }

        } catch (SQLException e) {
        }
        return listRole;
    }

    public Role getRoleByID(int roleID) {
        PermissionDAO dao = new PermissionDAO();
        String sql = "select * from role where roleID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return new Role(
                        rs.getInt("roleID"),
                        rs.getString("roleName"),
                        rs.getString("description"),
                        dao.getPermissionByRoleID(roleID));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateRole(int roleID, String roleName, String description) {
        String sql = "UPDATE role SET roleName = ?, description = ?  WHERE roleID = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, roleName);
            st.setString(2, description);
            st.setInt(3, roleID);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
    }

    public void createRole(String roleName, String description) {
        String sql = "insert role (roleName, description) values(?, ?) ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, roleName);
            st.setString(2, description);
            st.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void deleteByRoleID(int roleID) {
        String sql = "delete from RolePermissions where roleID = ? delete from Role where roleID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, roleID);
            pre.setInt(2, roleID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        RoleDAO dao = new RoleDAO();
        System.out.println(dao.getRoleByID(7).getPermission().get(0).getPermissionName());
        
    }
}
