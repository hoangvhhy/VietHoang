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
 * @author Hoang
 */
@WebServlet(name = "listDoctorAppointment", urlPatterns = {"/listDoctorAppointment"})
public class listDoctorAppointment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet listDoctorAppointment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listDoctorAppointment at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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

        // Bỏ ageSort trong getTotalFilteredRecords
        int totalRecords = medicalExaminationDAO.getTotalFilteredRecords(
                patientName, doctorName, appointmentDate, timeCreatedSort, status);

        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        if (page < 1) {
            page = 1;
        }

        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        HttpSession session = request.getSession();

        // Bỏ ageSort trong getFilteredExaminations
        Staff doctorID = (Staff) session.getAttribute("staffAccount");

        List<MedicalExamination> list = medicalExaminationDAO.getFilteredExaminations2(
                patientName, doctorName, appointmentDate, timeCreatedSort, status, doctorID.getStaffID(), page, pageSize);

        List<Professional> allProfessionals = medicalExaminationDAO.getAllProfessionals();

        request.setAttribute("list", list);

        request.setAttribute("allProfessionals", allProfessionals);

        request.setAttribute("currentPage", page);

        request.setAttribute("totalPages", totalPages);

        request.setAttribute("pageSize", pageSize);

        request.getRequestDispatcher("listDoctorAppointment.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
