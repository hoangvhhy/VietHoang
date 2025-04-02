/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package RolePermissionController;

import dao.PermissionDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Permission;
import model.Role;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "rolePermission", urlPatterns = {"/rolePermission"})
public class rolePermission extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO roleDAO = new RoleDAO();
        PermissionDAO permissionDAO = new PermissionDAO();
        String roleID = request.getParameter("roleID");
        if (roleID != null) {
            List<Permission> list = permissionDAO.getPermissionByRoleID(Integer.parseInt(roleID));
            request.setAttribute("listRolePermission", list);
            request.setAttribute("roleID", roleID);
        }else{
            List<Permission> list = permissionDAO.getPermissionByRoleID(1);
            request.setAttribute("listRolePermission", list);
            request.setAttribute("roleID", 1);
        }
        
        List<Permission> listPermission = permissionDAO.getAllPermission();
        List<Role> listRole = roleDAO.getAllRole();
        request.setAttribute("listRole", listRole);
        request.setAttribute("listPermission", listPermission);
        request.getRequestDispatcher("roles-permissions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int roleID = Integer.parseInt(request.getParameter("roleID"));
        String[] selectedPermissions = request.getParameterValues("selectedPermissions");
        RoleDAO rDAO = new RoleDAO();
        PermissionDAO permissionDAO = new PermissionDAO();
        permissionDAO.updateRolePermissions(roleID, selectedPermissions);
        HttpSession session = request.getSession();
        session.removeAttribute("role");

        Staff s = (Staff)session.getAttribute("staffAccount");
        session.setAttribute("role", rDAO.getRoleByID(s.getRoleID()));
        session.removeAttribute("staffAccount");
        
        session.setAttribute("staffAccount", s);
        response.sendRedirect("rolePermission?roleID=" + roleID);
    }

}
