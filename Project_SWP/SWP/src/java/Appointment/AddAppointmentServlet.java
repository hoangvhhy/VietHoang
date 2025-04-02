package Appointment;

import dao.MedicalExaminationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import model.MedicalExamination;
import model.Professional;
import model.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddAppointmentServlet", urlPatterns = {"/add_appointment"})
public class AddAppointmentServlet extends HttpServlet {

    private MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();

    @Override   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Customer> customers = medicalExaminationDAO.getAllCustomers();
        List<Professional> professionals = medicalExaminationDAO.getAllProfessionals();
        List<Service> services = medicalExaminationDAO.getAllServices();

        request.setAttribute("customers", customers);
        request.setAttribute("professionals", professionals);
        request.setAttribute("services", services);
        request.getRequestDispatcher("add-appointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String patientName = request.getParameter("patientName");
        String[] serviceIds = request.getParameterValues("serviceIds[]");
        String doctorName = request.getParameter("doctorName");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");
        String status = request.getParameter("status");

        try {
            String fullDateTime = date + " " + time;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String formattedDateTime = dateFormat.format(dateFormat.parse(fullDateTime));
            String createdAt = dateFormat.format(new java.util.Date());

            MedicalExamination exam = new MedicalExamination();

            Customer customer = new Customer();
            customer.setFullName(patientName);
            customer.setCustomerID(medicalExaminationDAO.getCustomerIdByName(patientName));
            exam.setCustomerId(customer);

            List<Service> selectedServices = new ArrayList<>();
            if (serviceIds != null) {
                for (String serviceId : serviceIds) {
                    Service service = new Service();
                    service.setPackageID(Integer.parseInt(serviceId));
                    selectedServices.add(service);
                }
            }
            exam.setList(selectedServices);

            Professional professional = new Professional();
            professional.setFullName(doctorName);
            professional.setStaffID(medicalExaminationDAO.getStaffIdByName(doctorName));
            exam.setConsultantId(professional);

            exam.setExaminationDate(formattedDateTime);
            exam.setNote(message);
            exam.setStatus(status);
            exam.setCreatedAt(createdAt);

            boolean success = medicalExaminationDAO.addMedicalExamination(exam);
            if (success) {
                request.setAttribute("message", "Appointment created successfully!");
                response.sendRedirect("manage_appointment");
            } else {
                request.setAttribute("error", "Failed to create appointment.");
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}
