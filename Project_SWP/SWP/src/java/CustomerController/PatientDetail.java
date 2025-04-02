/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CustomerController;

import util.ValidFunction;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "PatientDetail", urlPatterns = {"/patientDetail"})
public class PatientDetail extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PatientDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientDetail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();
        String customerIdStr = request.getParameter("patientId");

        if (customerIdStr == null || customerIdStr.isEmpty()) {
            response.sendRedirect("patient");
            return;
        }

        int customerId = Integer.parseInt(customerIdStr);

        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.getCustomerByID(customerId);

        if (customer.getDateOfBirth() != null && !customer.getDateOfBirth().isEmpty()) {
            String formattedDob = valid.convertDateString(customer.getDateOfBirth(), "dd/MM/yyyy");
            customer.setDateOfBirth(formattedDob);
        }
        if (customer == null) {
            response.sendRedirect("patient");
            return;
        }

        request.setAttribute("customer", customer);
        request.getRequestDispatcher("view-patientDetail.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
