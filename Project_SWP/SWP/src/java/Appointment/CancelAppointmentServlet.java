package Appointment;

import dao.MedicalExaminationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MedicalExamination;

import java.io.IOException;

@WebServlet(name = "CancelAppointmentServlet", urlPatterns = {"/cancel-appointment"})
public class CancelAppointmentServlet extends HttpServlet {

    private MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));

        // Lấy thông tin cuộc hẹn để kiểm tra trạng thái
        MedicalExamination exam = medicalExaminationDAO.getMedicalExaminationByID(examId);
        if (exam == null) {
            request.getSession().setAttribute("error", "Appointment not found.");
            response.sendRedirect("customer-medical-records");
            return;
        }

        // Kiểm tra trạng thái, chỉ cho phép hủy nếu là "Pending"
        if ("Pending".equals(exam.getStatus())) {
            boolean success = medicalExaminationDAO.cancelAppointment(examId);
            if (success) {
                request.getSession().setAttribute("message", "Appointment canceled successfully!");
            } else {
                request.getSession().setAttribute("error", "Failed to cancel appointment.");
            }
        } else {
            request.getSession().setAttribute("error", "Cannot cancel appointment. Only 'Pending' appointments can be canceled.");
        }

        response.sendRedirect("customer-medical-records");
    }
}