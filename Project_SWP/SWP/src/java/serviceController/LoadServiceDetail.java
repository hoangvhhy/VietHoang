package serviceController;

import dao.ProfessionalDAO;
import dao.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.util.List;
import model.Professional;
import model.Service;

@WebServlet(name = "LoadServiceDetail", urlPatterns = {"/loadservicedetail"})
public class LoadServiceDetail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int packageID = Integer.parseInt(request.getParameter("packageID"));
            ServiceDAO dao = new ServiceDAO();
            ProfessionalDAO proDAO = new ProfessionalDAO();
            Service service = dao.getServiceById(packageID);
            List<Professional> listPro = proDAO.getAllProfessionals();

            if (service != null) {
                request.setAttribute("service", service);
                request.setAttribute("listPro", listPro);

                RequestDispatcher dispatcher = request.getRequestDispatcher("ServiceDetail.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "Service not found!");
                response.sendRedirect("loadservice"); // Quay về trang danh sách dịch vụ nếu không tìm thấy
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Package ID!");
            response.sendRedirect("loadservice");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "LoadServiceDetail Servlet";
    }
}
