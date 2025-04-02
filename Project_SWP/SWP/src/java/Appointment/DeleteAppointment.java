package Appointment;

import dao.MedicalExaminationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteAppointment", urlPatterns = {"/deleteappointment"})
public class DeleteAppointment extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        try {
            String examIdStr = request.getParameter("examinationID");
            if (examIdStr != null && !examIdStr.trim().isEmpty()) {
                int examinationId = Integer.parseInt(examIdStr);
                boolean deleted = medicalExaminationDAO.deleteMedicalExamination(examinationId);
                
                if (deleted) {
                    request.getSession().setAttribute("message", "Medical examination deleted successfully");
                } else {
                    request.getSession().setAttribute("error", "Failed to delete medical examination or it doesn't exist");
                }
            } else {
                request.getSession().setAttribute("error", "Invalid examination ID");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid examination ID format");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("manage_appointment");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}