/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package RolePermissionController;

import util.ValidFunction;
import dao.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name="editRole", urlPatterns={"/editRole"})
public class editRole extends HttpServlet {
   


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String roleID = request.getParameter("roleID");
        RoleDAO roleDAO = new RoleDAO();
        Role role = roleDAO.getRoleByID(Integer.parseInt(roleID));
        request.setAttribute("roleName", role.getRoleName());
        request.setAttribute("description", role.getDescription());
        request.setAttribute("roleID", roleID);
        request.getRequestDispatcher("edit-role.jsp").forward(request, response);
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String roleID = request.getParameter("roleID");
        String roleName = request.getParameter("roleName");
        String description = request.getParameter("description");
        ValidFunction valid = new ValidFunction();
        request.setAttribute("roleID", roleID);
        request.setAttribute("roleName", roleName);
        request.setAttribute("description", description);
        if(valid.containsDigitOrSpecialChar(roleName)){
            request.setAttribute("error", "Role name can not contain digit or special character");
            request.getRequestDispatcher("edit-role.jsp").forward(request, response);
            return;
        }
        RoleDAO roleDAO = new RoleDAO();
        roleDAO.updateRole(Integer.parseInt(roleID),valid.normalizeName(roleName), description);
        
        request.setAttribute("mess", "Edit role succesfully");
        request.getRequestDispatcher("edit-role.jsp").forward(request, response);
        
    }


}
