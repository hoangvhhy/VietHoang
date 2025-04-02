/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DashboardController;

import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "dashboardRevenue", urlPatterns = {"/dashboardRevenue"})
public class dashboardRevenue extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String year = request.getParameter("year");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        InvoiceDAO dao = new InvoiceDAO();
        double[] array = new double[13];
        for (int i = 0; i < 12; i++) {
            array[i] = dao.OrderbyDate(i + 1 + "", year);
        }
        // Dữ liệu mẫu
        JSONObject json = new JSONObject();
        json.put("month", new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        json.put("revenue", array);
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
