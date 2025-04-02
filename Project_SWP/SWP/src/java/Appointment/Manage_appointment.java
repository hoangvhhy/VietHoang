/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Appointment;

import dao.MedicalExaminationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.MedicalExamination;
import model.Professional;
import model.Staff;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Manage_appointment", urlPatterns = {"/manage_appointment"})
public class Manage_appointment extends HttpServlet {

    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
    
    String patientName = request.getParameter("patientName");
    String doctorName = request.getParameter("doctorName");
    String appointmentDate = request.getParameter("appointmentDate");
    String timeCreatedSort = request.getParameter("timeCreatedSort");
    String status = request.getParameter("status");

    int page = 1; 
    int pageSize = 10; 
    String pageStr = request.getParameter("page");
    if (pageStr != null && !pageStr.isEmpty()) {
        page = Integer.parseInt(pageStr);
    }

    
    int totalRecords = medicalExaminationDAO.getTotalFilteredRecords(
            patientName, doctorName, appointmentDate, timeCreatedSort, status);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

    if (page < 1) page = 1;
    if (page > totalPages && totalPages > 0) page = totalPages;
    HttpSession session = request.getSession();
    
        Staff doctorID = (Staff) session.getAttribute("staffAccount");
    List<MedicalExamination> list = medicalExaminationDAO.getFilteredExaminations(patientName, doctorName, appointmentDate, timeCreatedSort, status, page, pageSize);
    
    List<Professional> allProfessionals = medicalExaminationDAO.getAllProfessionals();
    
    request.setAttribute("list", list);
    request.setAttribute("allProfessionals", allProfessionals);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("pageSize", pageSize);

    request.getRequestDispatcher("manage_appointment.jsp").forward(request, response);
}
}
