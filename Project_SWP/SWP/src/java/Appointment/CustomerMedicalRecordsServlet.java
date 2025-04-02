package Appointment;

import dao.MedicalExaminationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import model.MedicalExamination;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerMedicalRecordsServlet", urlPatterns = {"/customer-medical-records"})
public class CustomerMedicalRecordsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customerAccount");

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy tham số tìm kiếm từ request
        String dateFilter = request.getParameter("date");
        String pageStr = request.getParameter("page");

        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
        int pageSize = 10; // Số bản ghi trên mỗi trang

        // Tính tổng số bản ghi
        int totalRecords = medicalExaminationDAO.getTotalAppointmentsByCustomerId(
                customer.getCustomerID(), dateFilter
        );

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Lấy danh sách cuộc hẹn cho trang hiện tại
        List<MedicalExamination> appointments = medicalExaminationDAO.getAppointmentsByCustomerId(
                customer.getCustomerID(), dateFilter, page, pageSize
        );

        // Kiểm tra trạng thái thay đổi của đơn khám
        

        // Truyền dữ liệu vào request
        request.setAttribute("appointments", appointments);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);

        // Forward đến JSP
        request.getRequestDispatcher("medical_records.jsp").forward(request, response);
    }
}
