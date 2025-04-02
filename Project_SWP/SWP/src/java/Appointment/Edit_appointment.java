package Appointment;

import dao.MedicalExaminationDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import model.MedicalExamination;
import model.Professional;
import model.Service;

@WebServlet(name = "Edit_appointment", urlPatterns = {"/edit_appointment"})
public class Edit_appointment extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentIdStr = request.getParameter("appointmentId");
        if (appointmentIdStr == null || appointmentIdStr.isEmpty()) {
            response.sendRedirect("appointment_list.jsp?error=invalidid");
            return;
        }

        int appointmentId = Integer.parseInt(appointmentIdStr);
        MedicalExamination appointment = medicalExaminationDAO.getMedicalExaminationByID(appointmentId);

        if (appointment != null) {
            // Lấy danh sách tất cả dịch vụ và bác sĩ để hiển thị trong form
            List<Service> allServices = medicalExaminationDAO.getAllServices();
            List<Professional> allProfessionals = medicalExaminationDAO.getAllProfessionals();

            request.setAttribute("appointment", appointment);
            request.setAttribute("services", allServices); // Đổi tên thành "services" để khớp với JSP
            request.setAttribute("allProfessionals", allProfessionals);
            request.getRequestDispatcher("edit-appointment.jsp").forward(request, response);
        } else {
            response.sendRedirect("appointment_list.jsp?error=notfound");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        String examinationDate = request.getParameter("date");
        String status = request.getParameter("status");
        String consultantName = request.getParameter("doctor");
        String note = request.getParameter("message");
        String[] serviceIds = request.getParameterValues("serviceIds[]"); // Lấy mảng các service ID đã chọn

        MedicalExamination appointment = medicalExaminationDAO.getMedicalExaminationByID(appointmentId);
        if (appointment != null) {
            // Cập nhật thông tin từ form
            appointment.setExaminationDate(examinationDate);
            appointment.setStatus(status);
            appointment.setNote(note);

            // Cập nhật bác sĩ (consultant)
            List<Professional> professionals = medicalExaminationDAO.getAllProfessionals();
            for (Professional p : professionals) {
                if (p.getFullName().equals(consultantName)) {
                    appointment.setConsultantId(p);
                    break;
                }
            }

            // Cập nhật danh sách dịch vụ
            List<Service> selectedServices = new ArrayList<>();
            List<Service> allServices = medicalExaminationDAO.getAllServices();
            if (serviceIds != null) {
                for (String serviceId : serviceIds) {
                    int id = Integer.parseInt(serviceId);
                    for (Service s : allServices) {
                        if (s.getPackageID() == id) {
                            selectedServices.add(s);
                            break;
                        }
                    }
                }
            }
            appointment.setList(selectedServices);

            // Gọi phương thức cập nhật
            boolean isUpdated = medicalExaminationDAO.updateMedicalExamination(appointment);

            if (isUpdated) {
                response.sendRedirect("manage_appointment?success=true");
            } else {
                response.sendRedirect("edit-appointment.jsp?error=updatefailed");
            }
        } else {
            response.sendRedirect("edit-appointment.jsp?error=notfound");
        }
    }
}