/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;


import util.ValidFunction;
import dao.RoleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Role;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "editStaff", urlPatterns = {"/editStaff"})
public class editStaff extends HttpServlet {

    

    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();
        String staffID_raw = request.getParameter("staffID");
        StaffDAO staffDAO = new StaffDAO();
        int staffID = Integer.parseInt(staffID_raw);
        Staff s = staffDAO.getStaffByID(staffID);
        String fullName = s.getFullName();
        String[] split = fullName.split("\\s+");
        int i = 0;
        String firstName = "";
        String lastName = split[split.length - 1];
        for (String str : split) {
            if (i != str.length() - 1) {
                firstName += " " + str;
            }
        }
        request.setAttribute("staffID", staffID);
        request.setAttribute("firstName", firstName.trim());
        request.setAttribute("lastName", lastName.trim());
        request.setAttribute("phone", s.getPhone());
        request.setAttribute("email", s.getEmail());
        request.setAttribute("roleID", s.getRoleID());
        request.setAttribute("status", s.getStatus());
        request.setAttribute("hireDate", valid.formatDate(s.getHireDate()));

        RoleDAO roleDAO = new RoleDAO();
        List<Role> listRole = roleDAO.getAllRole();
        request.setAttribute("listRole", listRole);
        request.getRequestDispatcher("edit-staff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();
        String staffID_raw = request.getParameter("staffID");
        int staffID = Integer.parseInt(staffID_raw);
        StaffDAO staffDAO = new StaffDAO();
        Staff s = new Staff();
        s = staffDAO.getStaffByID(staffID);
        RoleDAO roleDAO = new RoleDAO();
        List<Role> listRole = roleDAO.getAllRole();
        request.setAttribute("listRole", listRole);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String roleID = request.getParameter("roleID");
        String hireDate = request.getParameter("hireDate");

        String status = request.getParameter("status");
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("phone", phone);
        request.setAttribute("email", email);
        request.setAttribute("roleID", roleID);
        request.setAttribute("status", status);
        request.setAttribute("hireDate", hireDate);

        request.setAttribute("staffID", staffID_raw);
        if (valid.containsDigitOrSpecialChar(firstName) || valid.containsDigitOrSpecialChar(lastName)) {
            request.setAttribute("error", "First Name or Last Name cannot contain digit or special character");
            request.getRequestDispatcher("edit-staff.jsp").forward(request, response);
            return;
        }

        if (!valid.isValidPhoneNumber(phone)) {
            request.setAttribute("error", "Phone number is not exist, please check again");
            request.getRequestDispatcher("edit-staff.jsp").forward(request, response);
            return;
        }
        String fullName = valid.normalizeName(firstName) + " " + valid.normalizeName(lastName);
        staffDAO.updateStaff(staffID, fullName, email, phone, hireDate, Integer.parseInt(roleID), status);
        request.setAttribute("mess", "Update staff succesfully");
        request.getRequestDispatcher("edit-staff.jsp").forward(request, response);
    }

}
