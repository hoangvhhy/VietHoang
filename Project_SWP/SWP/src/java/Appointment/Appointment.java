package Appointment;

import com.google.gson.Gson;
import dao.MedicalExaminationDAO;
import dao.ProfessionalDAO;
import dao.ServiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Customer;
import model.MedicalExamination;
import model.Professional;
import model.Service;

/**
 *
 * @author ADMIN
 */
public class Appointment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDAO serviceDAO = new ServiceDAO();
        ProfessionalDAO professionalDAO = new ProfessionalDAO();
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();

        List<Service> services = serviceDAO.getAllService();
        List<Professional> doctors = professionalDAO.getAllDoctors();


       

        request.setAttribute("services", services);
        request.setAttribute("doctors", doctors);
       

        request.getRequestDispatcher("appointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServiceDAO serviceDAO = new ServiceDAO();
        ProfessionalDAO professionalDAO = new ProfessionalDAO();
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        Customer customerProfile = (Customer) session.getAttribute("customerAccount");

        if (customerProfile == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String[] serviceIds = request.getParameterValues("serviceIds[]");
        String doctorId = request.getParameter("doctorId");
        String dateStr = request.getParameter("date");
        String timeStr = request.getParameter("time");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");

        try {

            if (serviceIds == null || serviceIds.length == 0) {
                request.setAttribute("error", "Please select at least one service.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
                return;
            }
            if (doctorId == null || doctorId.trim().isEmpty()) {
                request.setAttribute("error", "Please select a doctor.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
                return;
            }
            System.out.println(doctorId);

            String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Professional doctor = professionalDAO.getProfessionalbyID(Integer.parseInt(doctorId));
            if (doctor == null) {
                request.setAttribute("error", "Selected doctor does not exist.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
                return;
            }

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String examinationDateTimeStr = dateStr + " " + timeStr;
            LocalDateTime examinationDateTime = LocalDateTime.parse(examinationDateTimeStr, inputFormatter);
            LocalDateTime now = LocalDateTime.now();

            if (examinationDateTime.isBefore(now)) {
                request.setAttribute("error", "Appointment date must be in the future.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
                return;
            }

            String examinationDate = examinationDateTime.format(outputFormatter);
            createdAt = LocalDateTime.now().format(outputFormatter);

            if (!medicalExaminationDAO.isDoctorAvailable(Integer.parseInt(doctorId), examinationDate)) {
                request.setAttribute("error", "The selected doctor is not available at the specified time.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
                return;
            }

            if (!medicalExaminationDAO.isCustomerAvailable(customerProfile.getCustomerID(), examinationDate)) {
                request.setAttribute("error", "You already have an appointment at the specified time.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
                return;
            }

            List<Service> selectedServices = new ArrayList<>();
            for (String serviceId : serviceIds) {
                Service service = serviceDAO.getServiceById(Integer.parseInt(serviceId));
                if (service != null) {
                    selectedServices.add(service);
                }
            }

            MedicalExamination examination = new MedicalExamination();
            examination.setExaminationID(0);
            examination.setExaminationDate(examinationDate);
            examination.setCustomerId(customerProfile);
            examination.setStatus("Pending");
            examination.setConsultantId(doctor);
            examination.setNote(message);
            examination.setCreatedAt(createdAt);
            examination.setList(selectedServices);

            boolean success = medicalExaminationDAO.saveMedicalExamination(examination);
            if (success) {
                response.sendRedirect("confirmation.jsp");
            } else {
                request.setAttribute("error", "Failed to book appointment due to a server error. Please try again.");
                reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Invalid date or time format. Please use DD/MM/YYYY and HH:MM.");
            reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid doctor or service ID format.");
            reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            reloadFormData(request, response, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
        }
    }

    private void preserveFormData(HttpServletRequest request, String[] serviceIds, String doctorId,
            String dateStr, String timeStr, String name, String phone, String message) {
        request.setAttribute("selectedServiceIds", serviceIds);
        request.setAttribute("selectedDoctorId", doctorId);
        request.setAttribute("date", dateStr);
        request.setAttribute("time", timeStr);
        request.setAttribute("name", name);
        request.setAttribute("phone", phone);
        request.setAttribute("message", message);
    }

// Helper method to reload form data
    private void reloadFormData(HttpServletRequest request, HttpServletResponse response,
            String[] serviceIds, String doctorId, String dateStr, String timeStr,
            String name, String phone, String message) throws ServletException, IOException {
        preserveFormData(request, serviceIds, doctorId, dateStr, timeStr, name, phone, message);
        ServiceDAO serviceDAO = new ServiceDAO();
        ProfessionalDAO professionalDAO = new ProfessionalDAO();
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        request.setAttribute("services", serviceDAO.getAllService());
        request.setAttribute("doctors", professionalDAO.getAllDoctors());
        request.getRequestDispatcher("appointment.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles appointment booking for medical examinations";
    }
}
