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
import model.Permission;

/**
 *
 * @author Gigabyte
 */
public class PermissionDAO extends DBContext {

    public List<Permission> getAllPermission() {
        List<Permission> listPermission = new ArrayList<>();
        String sql = "SELECT * FROM permission";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int permissionID = rs.getInt("permissionID");
                String permissionName = rs.getString("permissionName");
                String description = rs.getString("description");
                Permission permission = new Permission(permissionID, permissionName, description);
                listPermission.add(permission); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listPermission; // Trả về danh sách khách hàng
    }

    public List<Permission> getPermissionByRoleID(int roleID) {
        List<Permission> listPermission = new ArrayList<>();
        String sql = "select permission.permissionID, permissionName, description \n"
                + "from RolePermissions join permission on permission.permissionID = RolePermissions.permissionID\n"
                + "where roleID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int permissionID = rs.getInt("permissionID");
                String permissionName = rs.getString("permissionName");
                String description = rs.getString("description");
                Permission permission = new Permission(permissionID, permissionName, description);
                listPermission.add(permission); // Thêm customer vào danh sách
            }

        } catch (SQLException e) {
        }
        return listPermission; // Trả về danh sách khách hàng
    }
    
     public void updateRolePermissions(int roleID, String[] selectedPermissions) {
        try {
            // 1. Xóa toàn bộ quyền cũ của role
            String deleteSQL = "DELETE FROM RolePermissions WHERE roleID = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, roleID);
                deleteStmt.executeUpdate();
            }

            // 2. Thêm quyền mới nếu có
            if (selectedPermissions != null) {
                String insertSQL = "INSERT INTO RolePermissions (roleID, permissionID) VALUES (?, ?)";
                try (PreparedStatement insertSt = connection.prepareStatement(insertSQL)) {
                    for (String permissionID : selectedPermissions) {
                        insertSt.setInt(1, roleID);
                        insertSt.setInt(2, Integer.parseInt(permissionID));
                        insertSt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PermissionDAO dao = new PermissionDAO();

        System.out.println(dao.getPermissionByRoleID(1).get(0).getPermissionName());
    }
}
