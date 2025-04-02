/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DashboardController;

import util.ValidFunction;
import dao.DashboardDAO;
import dao.MedicalExaminationDAO;
import dao.ProfessionalDAO;
import dao.VisitCounterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.MedicalExamination;
import model.Professional;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "dashboard", urlPatterns = {"/dashboard"})
public class dashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();
        DashboardDAO dashDAO = new DashboardDAO();
        ProfessionalDAO proDAO = new ProfessionalDAO();
        MedicalExaminationDAO meDAO = new MedicalExaminationDAO();
        VisitCounterDAO visitDAO = new VisitCounterDAO();

        List<Customer> listCus = dashDAO.getNewCustomer();
        List<Professional> listDoc = proDAO.getAllProfessionals();
        List<MedicalExamination> listMe = meDAO.getAllMedicalExamination();
        int countPending = dashDAO.countPendingExaminations();

        for (MedicalExamination med : listMe) {
            med.setExaminationDate(valid.convertDateString(med.getExaminationDate(), "dd/MM/yyyy HH:mm"));
        }

        request.setAttribute("todayCount", visitDAO.getVisitCount("DAY"));
        request.setAttribute("monthCount", visitDAO.getVisitCount("MONTH"));
        request.setAttribute("yearCount", visitDAO.getVisitCount("YEAR"));

        request.setAttribute("listCustomer", listCus);
        request.setAttribute("listDoctor", listDoc);
        request.setAttribute("listAppointment", listMe);
        request.setAttribute("pending", countPending);
//        request.setAttribute("visitCount", visitCount);
        request.setAttribute("docCount", listDoc != null ? listDoc.size() : 0);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
