/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;

import dao.ProfessionalDAO;
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
import model.Professional;
import model.Role;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "roleStaff", urlPatterns = {"/roleStaff"})
public class roleStaff extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet roleStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet roleStaff at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Staff s = (Staff) session.getAttribute("staffAccount");
        RoleDAO rDAO = new RoleDAO();
        Role r = rDAO.getRoleByID(s.getRoleID());
        session.setAttribute("role", r);
        if (s.getRoleID() == 4 || s.getRoleID() == 5) {
            response.sendRedirect("viewpersonalschedule?professionalID=" + s.getStaffID());
            return;
        }
        
        List<Permission> listPer = r.getPermission();
        boolean dashRevenue = false;
        boolean dash = false;
        boolean staff = false;
        boolean patient = false;
        boolean manageAppointment = false;
        boolean loadSchedule = false;
        boolean loadService = false;
        boolean invoice = false;
        boolean feedback = false;
        boolean blog = false;
        boolean rolePermission = false;
        for (Permission p : listPer){
            if (p.getPermissionID() == 21) {
                dashRevenue = true;
            }
            if (p.getPermissionID() == 22) {
                dash = true;
            }
            if (p.getPermissionID() == 24) {
                staff = true;
            }

            if (p.getPermissionID() == 28) {
                patient = true;
            }
            if (p.getPermissionID() == 1) {
                manageAppointment = true;
            }
            if (p.getPermissionID() == 15) {
                loadSchedule = true;
            }
            if (p.getPermissionID() == 18) {
                loadService = true;
            }

            if (p.getPermissionID() == 4) {
                invoice = true;
            }
            if (p.getPermissionID() == 29) {
                feedback = true;
            }
            if (p.getPermissionID() == 8) {
                blog = true;
            }
            if (p.getPermissionID() == 27) {
                rolePermission = true;
            }
        }
        
            if (dashRevenue) {
                response.sendRedirect("dashRevenue");
                return;
            }
            if (dash) {
                response.sendRedirect("dashboard");
                return;
            }
            if (staff) {
                response.sendRedirect("staff");
                return;
            }

            if (patient) {
                response.sendRedirect("patient");
                return;
            }
            if (manageAppointment) {
                response.sendRedirect("manage_appointment");
                return;
            }
            if (loadSchedule) {
                response.sendRedirect("loadstaffforschedule");
                return;
            }
            if (loadService) {
                response.sendRedirect("loadmanage");
                return;
            }

            if (invoice) {
                response.sendRedirect("invoice");
                return;
            }
            if (feedback) {
                response.sendRedirect("feedback");
                return;
            }
            if (blog) {
                response.sendRedirect("homeblogseverlet");
                return;
            }
            if (rolePermission) {
                response.sendRedirect("rolePermission");
                return;
            }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
