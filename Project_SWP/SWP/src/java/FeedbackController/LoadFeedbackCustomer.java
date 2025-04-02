/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package FeedbackController;

import dao.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "LoadFeedbackCustomer", urlPatterns = {"/loadfeedback"})
public class LoadFeedbackCustomer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO dao = new FeedbackDAO();
        String invoiceID = request.getParameter("invoiceID");

        String pageStr = request.getParameter("page");
        if (pageStr == null || pageStr.isEmpty()) {
            pageStr = "1";
        }
        int page;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            page = 1; // Đặt mặc định về trang đầu tiên nếu không hợp lệ
        }

        int pageSize = 5;
        int totalFeedback = dao.getAllFeedbackByCustomer().size();
        List<model.Feedback> list = dao.getAllFeedbackByCustomer(page, pageSize);
        int endPage = (int) Math.ceil((double) totalFeedback / pageSize);

        HttpSession session = request.getSession();
        String msg = (String) session.getAttribute("msg");
        session.removeAttribute("msg"); // Xóa msg sau khi lấy

        request.setAttribute("msg", msg);
        request.setAttribute("invoiceID", invoiceID); // Truyền invoiceID vào JSP
        request.setAttribute("listFeed", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);

        request.getRequestDispatcher("feedback.jsp").forward(request, response);
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
