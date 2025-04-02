package serviceController;

import util.ValidFunction;
import dao.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Service;

@WebServlet(name = "loadService", urlPatterns = {"/loadservice"})
public class loadService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServiceDAO serviceDao = new ServiceDAO();
        List<Service> serviceList = null;
        ValidFunction val = new ValidFunction();

        String action = request.getParameter("action");
        String searchKeyword = request.getParameter("searchKeyword");
        String sortBy = request.getParameter("sortBy");

        // Nếu có hành động lọc (tìm kiếm hoặc sắp xếp)
        if ("search".equalsIgnoreCase(action) && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            // Tiến hành lọc theo từ khóa
            serviceList = serviceDao.getAllOnServicesWithSearch(val.normalizeName(searchKeyword));
        } else if ("filter".equalsIgnoreCase(action) && sortBy != null && !sortBy.isEmpty()) {
            // Tiến hành lọc theo tiêu chí sắp xếp
            serviceList = serviceDao.getAllOnServicesWithFilter(sortBy);
            request.getSession().setAttribute("filteredServiceList", serviceList);  // Lưu vào session
        } else {
            // Nếu không có hành động lọc, lấy toàn bộ danh sách dịch vụ
            serviceList = serviceDao.getAll_ON_Service();
            request.getSession().removeAttribute("filteredServiceList");  // Xóa danh sách lọc khỏi session
        }

        // Lấy số dịch vụ hiển thị trên mỗi trang và tổng số trang
        int pageSize = 6;
        int totalServices = serviceList.size();
        int totalPages = (int) Math.ceil((double) totalServices / pageSize);

        // Lấy trang hiện tại từ request, mặc định là trang 1
        int pageNumber = 1;
        String pageParam = request.getParameter("pageNumber");
        if (pageParam != null) {
            try {
                pageNumber = Integer.parseInt(pageParam);
                if (pageNumber < 1 || pageNumber > totalPages) {
                    pageNumber = 1;
                }
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }

        // Xác định chỉ mục bắt đầu và kết thúc của danh sách con đã lọc
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalServices);
        List<Service> paginatedList = serviceList.subList(startIndex, endIndex);

        // Gửi dữ liệu đến JSP
        request.setAttribute("serviceList", paginatedList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("searchKeyword", searchKeyword);  // Giữ lại từ khóa tìm kiếm
        request.setAttribute("sortBy", sortBy);  // Giữ lại tiêu chí sắp xếp
        request.getRequestDispatcher("service.jsp").forward(request, response);
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
        return "Load, filter, search and paginate services";
    }
}
