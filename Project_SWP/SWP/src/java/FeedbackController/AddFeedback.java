/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package FeedbackController;

import dao.FeedbackDAO;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "AddFeedback", urlPatterns = {"/addFeedback"})
public class AddFeedback extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int invoiceID = Integer.parseInt(request.getParameter("invoiceId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            FeedbackDAO feedbackDAO = new FeedbackDAO();

            boolean isAdded = feedbackDAO.addFeedback(invoiceID, rating, comment);

            if (isAdded) {
                request.getSession().setAttribute("msg", "Cảm ơn bạn đã để lại phản hồi.");
            } else {
                request.getSession().setAttribute("msg", "Có lỗi xảy ra khi gửi Feedback. Vui lòng thử lại sau.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("msg", "Dữ liệu không hợp lệ. Vui lòng thử lại.");
            e.printStackTrace();
        } catch (Exception e) {
            request.getSession().setAttribute("msg", "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại.");
            e.printStackTrace();
        }
        response.sendRedirect("loadfeedback");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
