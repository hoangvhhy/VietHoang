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

/**
 *
 * @author Gigabyte
 */
@WebServlet(name="addRole", urlPatterns={"/addRole"})
public class addRole extends HttpServlet {
   
    
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        String description = request.getParameter("description");
        ValidFunction valid = new ValidFunction();
        request.setAttribute("roleName", roleName);
        request.setAttribute("description", description);
        if(valid.containsDigitOrSpecialChar(roleName)){
            request.setAttribute("error", "Role name can not contain digit or special character");
            request.getRequestDispatcher("add-role.jsp").forward(request, response);
            return;
        }
        RoleDAO roleDAO = new RoleDAO();
        roleDAO.createRole(valid.normalizeName(roleName), description);
        
        request.setAttribute("mess", "Add role succesfully");
        request.getRequestDispatcher("add-role.jsp").forward(request, response);
    }

  
}
