/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DashboardController;

import com.google.gson.Gson;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "CustomerStatisticsServlet", urlPatterns = {"/dashboardCustomer"})
public class CustomerStatistics extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Khởi tạo DAO để lấy dữ liệu
        CustomerDAO customerDAO = new CustomerDAO();
        int year = Integer.parseInt(request.getParameter("year"));
        // Lấy dữ liệu từ DAO
        Map<Integer, Integer> stats = customerDAO.getCustomerStatistics(year);
        // Đảm bảo dữ liệu đủ 12 tháng (nếu tháng nào không có thì gán 0)
        List<Integer> counts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            counts.add(stats.getOrDefault(i, 0));
        }
        // Tạo JSON trả về
        Map<String, Object> result = new HashMap<>();
        result.put("month", new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        result.put("customerCount", counts);

        // Chuyển đổi Map thành JSON và trả về
        String json = new Gson().toJson(result);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
