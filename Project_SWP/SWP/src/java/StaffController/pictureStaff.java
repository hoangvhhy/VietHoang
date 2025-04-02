/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;

import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "pictureStaff", urlPatterns = {"/pictureStaff"})
public class pictureStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String staffID = request.getParameter("staffID");
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByID(Integer.parseInt(staffID));

        if (staff != null && staff.getProfilePicture() != null) {

            response.setContentType("image/jpeg");
            response.getOutputStream().write(staff.getProfilePicture());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
