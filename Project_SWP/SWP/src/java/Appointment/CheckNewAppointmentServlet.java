package Appointment;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import dao.MedicalExaminationDAO;

@WebServlet("/CheckNewAppointmentServlet")
public class CheckNewAppointmentServlet extends HttpServlet {
    private MedicalExaminationDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new MedicalExaminationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int newCount = appointmentDAO.getNewAppointmentsCount();
        
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(new ResponseObj(newCount));
        response.getWriter().write(jsonResponse);
    }

    private static class ResponseObj {
        private int newCount;
        public ResponseObj(int newCount) {
            this.newCount = newCount;
        }
    }
}