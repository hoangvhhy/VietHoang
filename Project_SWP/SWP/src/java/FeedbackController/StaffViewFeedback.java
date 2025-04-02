/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package FeedbackController;

import dao.FeedbackDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;
import org.apache.el.util.Validation;
import util.ValidFunction;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "Feedback", urlPatterns = {"/feedback"})
public class StaffViewFeedback extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO dao = new FeedbackDAO();
        ValidFunction valid = new ValidFunction();
        String indexPage = request.getParameter("page");
        String rate = request.getParameter("rate");
        if (indexPage == null || indexPage.trim().isEmpty()) {
            indexPage = "1";
        }
        if (rate == null || rate.isEmpty()) {
            rate = "";
        }
        int page;
        int pageSize = 10;
        int totalFeedback = 0;
        try {
            page = Integer.parseInt(indexPage.trim());
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        List<Feedback> list = new ArrayList<>();
        
        if (!rate.isEmpty()) {
            if (rate.equals("1")) {
                list = dao.getAllFeedback1StarByCustomer(page, pageSize);
                totalFeedback = dao.getAllFeedback1StarByCustomer().size();
            } else if (rate.equals("2")) {
                list = dao.getAllFeedback2StarByCustomer(page, pageSize);
                totalFeedback = dao.getAllFeedback2StarByCustomer().size();
            } else if (rate.equals("3")) {
                list = dao.getAllFeedback3StarByCustomer(page, pageSize);
                totalFeedback = dao.getAllFeedback3StarByCustomer().size();
            } else if (rate.equals("4")) {
                list = dao.getAllFeedback4StarByCustomer(page, pageSize);
                totalFeedback = dao.getAllFeedback4StarByCustomer().size();
            } else if (rate.equals("5")) {
                list = dao.getAllFeedback5StarByCustomer(page, pageSize);
                totalFeedback = dao.getAllFeedback5StarByCustomer().size();
            }
        } else {
            list = dao.getAllFeedbackByCustomer(page, pageSize);
            totalFeedback = dao.getAllFeedbackByCustomer().size(); // Lấy tổng số feedback
        }
        
        for (Feedback feedback : list) {
            feedback.setDate(valid.formatDateFeedback(feedback.getDate()));
        }
        
        int endPage = (int) Math.ceil((double) totalFeedback / pageSize);
        request.setAttribute("listFeedback", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);
        request.setAttribute("rate", rate);
        request.setAttribute("currentEntries", list.size());
        request.setAttribute("totalFeedback", totalFeedback);

        request.getRequestDispatcher("view-feedback.jsp").forward(request, response);
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
