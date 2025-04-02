package Appointment;

import dao.MedicalExaminationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MedicalExamination;
import model.MedicalRecord;

import java.io.IOException;
import model.Customer;

@WebServlet(name = "ViewMedicalRecordServlet", urlPatterns = {"/view-medical-record"})
public class ViewMedicalRecordServlet extends HttpServlet {

    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customerAccount");

        // Kiểm tra xem khách hàng đã đăng nhập chưa
        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int examId = Integer.parseInt(request.getParameter("examId"));
        MedicalExamination exam = medicalExaminationDAO.getMedicalExaminationByID(examId);
        
        // Chỉ hiển thị hồ sơ bệnh án nếu trạng thái là "Confirmed" hoặc "Completed"
        MedicalRecord medicalRecord = null;
        if ("Confirmed".equals(exam.getStatus()) || "Completed".equals(exam.getStatus())) {
            medicalRecord = medicalExaminationDAO.getMedicalRecordByExamId(examId);
        }

        request.setAttribute("exam", exam);
        request.setAttribute("medicalRecord", medicalRecord);
        request.getRequestDispatcher("view-medical-record.jsp").forward(request, response);
    }
}