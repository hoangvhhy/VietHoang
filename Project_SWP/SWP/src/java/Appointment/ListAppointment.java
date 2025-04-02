/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Appointment;

import dao.MedicalExaminationDAO;
import dao.ProfessionalDAO;
import dao.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.MedicalExamination;
import model.Professional;
import model.Service;

/**
 *
 * @author Phan Huu Kien
 */
public class ListAppointment extends HttpServlet {
    private MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListAppointment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListAppointment at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
          HttpSession session = request.getSession();
         String patientName = request.getParameter("patientName");
        String ageSort = request.getParameter("ageSort");
        String doctorName = request.getParameter("doctorName");
        String appointmentDate = request.getParameter("appointmentDate");
        String timeCreatedSort = request.getParameter("timeCreatedSort");
        String status = request.getParameter("status");

        // Tham số phân trang
        int page = 1; // Trang mặc định
        int pageSize = 10; // Số bản ghi mỗi trang (có thể thay đổi)
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        // Tính tổng số bản ghi để xác định số trang
        int totalRecords = medicalExaminationDAO.getTotalFilteredRecords(
                patientName, ageSort, doctorName, appointmentDate, timeCreatedSort, status);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Đảm bảo page không vượt quá giới hạn
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        // Lấy danh sách đã lọc với phân trang
        List<MedicalExamination> list = medicalExaminationDAO.getFilteredExaminations(
                patientName, ageSort, doctorName, appointmentDate, timeCreatedSort, status, page, pageSize);

        // Lấy danh sách tất cả bác sĩ để hiển thị trong dropdown
        List<Professional> allProfessionals = medicalExaminationDAO.getAllProfessionals();

        // Đặt dữ liệu vào request
        request.setAttribute("list", list);
        request.setAttribute("allProfessionals", allProfessionals);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);

        // Chuyển hướng đến JSP
        request.getRequestDispatcher("view-appointment.jsp").forward(request, response);
    }
   

}
