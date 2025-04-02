/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package RolePermissionController;

import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name="deleteRole", urlPatterns={"/deleteRole"})
public class deleteRole extends HttpServlet {
   
 

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RoleDAO roleDAO = new RoleDAO();
       String roleID_raw = request.getParameter("roleID");
       int roleID = Integer.parseInt(roleID_raw);
       roleDAO.deleteByRoleID(roleID);
       response.sendRedirect("rolePermission");
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    }

  

}
