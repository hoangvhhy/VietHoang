/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package HomePageController;

import util.ValidFunction;
import dao.FeedbackDAO;
import dao.HomePageDAO;
import dao.ProfessionalDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Feedback;
import model.Professional;
import model.Service;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "About", urlPatterns = {"/about"})
public class About extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();

        HomePageDAO dao = new HomePageDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ProfessionalDAO proDAO = new ProfessionalDAO();

        List<Feedback> listFeedback = feedbackDAO.getAllFeedback5StarByCustomer();
        List<Professional> listPro = proDAO.get4Professionals();
        List<Service> listService = dao.get4Service();

        for (Feedback feedback : listFeedback) {
            feedback.setDate(valid.formatDateNews(feedback.getDate()));
        }
        request.setAttribute("listService", listService);
        request.setAttribute("listFeedback", listFeedback);
        request.setAttribute("listProfessional", listPro);

        request.getRequestDispatcher("about.jsp").forward(request, response);
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
