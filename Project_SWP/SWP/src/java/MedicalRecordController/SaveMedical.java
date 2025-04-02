/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package MedicalRecordController;

import dao.MedicalExaminationDAO;
import dao.MedicalRecordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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
 * @author Win11
 */
public class SaveMedical extends HttpServlet {
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
            out.println("<title>Servlet SaveMedical</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveMedical at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int examinationID = Integer.parseInt(request.getParameter("examinationID"));
        String diagnosis = request.getParameter("diagnosis");
        String treatmentPlan = request.getParameter("treatmentPlan");
        String medications = request.getParameter("medicationsPrescribed");

        MedicalRecordDAO dao = new MedicalRecordDAO();
        boolean success = dao.saveOrUpdateRecord(examinationID, diagnosis, treatmentPlan, medications);
         String patientName = request.getParameter("patientName");
        String ageSort = request.getParameter("ageSort");
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
        HttpSession session =request.getSession();
        Staff st = (Staff) session.getAttribute("staffAccount");
        
        // Tính tổng số bản ghi để xác định số trang
        int totalRecords = medicalExaminationDAO.getTotalFilteredRecords2(
                patientName, ageSort,st.getStaffID() , appointmentDate, timeCreatedSort, status);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Đảm bảo page không vượt quá giới hạn
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;
        
        // Lấy danh sách đã lọc với phân trang
        List<MedicalExamination> list = medicalExaminationDAO.getFilteredExaminations2(
                patientName, ageSort, st.getStaffID(), appointmentDate, timeCreatedSort, status, page, pageSize);

        // Lấy danh sách tất cả bác sĩ để hiển thị trong dropdown
        List<Professional> allProfessionals = medicalExaminationDAO.getAllProfessionals();

        // Đặt dữ liệu vào request
        request.setAttribute("list", list);
        request.setAttribute("allProfessionals", allProfessionals);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);

        // Chuyển hướng đến JSP
        request.getRequestDispatcher("manage_appointment.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
