package serviceController;

import util.ValidFunction;
import dao.ServiceDAO;
import model.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Load_Manage", urlPatterns = {"/loadmanage"})
public class Load_Manage extends HttpServlet {

    // Thiết lập số dịch vụ mỗi trang
    private static final int PAGE_SIZE = 6;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServiceDAO serviceDao = new ServiceDAO();
        List<Service> serviceList = new ArrayList<>();
        int pageNumber = 1;
        String action = request.getParameter("action");
        ValidFunction val = new ValidFunction();

        // Xử lý phân trang
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }

        if ("search".equalsIgnoreCase(action)) {
            String searchKeyword = request.getParameter("searchKeyword");
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                serviceList = serviceDao.getAllServicesWithSearch(val.normalizeName(searchKeyword));
            } else {
                serviceList = serviceDao.getAllService();
            }
        } else if ("filter".equalsIgnoreCase(action)) {
            String sortBy = request.getParameter("sortBy");
            if (sortBy != null && !sortBy.isEmpty()) {
                serviceList = serviceDao.getAllServicesWithFilter(sortBy);
            } else {
                serviceList = serviceDao.getAllService();
            }
        } else {
            serviceList = serviceDao.getAllService();
        }

        // Tính số lượng trang
        int totalServices = serviceList.size();
        int totalPages = (int) Math.ceil((double) totalServices / PAGE_SIZE);
        int startIndex = (pageNumber - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalServices);

        // Lấy danh sách dịch vụ cho trang hiện tại
        List<Service> pagedServices = serviceList.subList(startIndex, endIndex);

        // Đặt các thuộc tính cho JSP
        request.setAttribute("serviceList", pagedServices);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("searchKeyword", request.getParameter("searchKeyword"));
        request.setAttribute("sortBy", request.getParameter("sortBy"));

        // Chuyển đến JSP
        request.getRequestDispatcher("manage_Service.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing services with pagination and filter.";
    }
}
