/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package InvoiceController;

import util.ValidFunction;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Invoice;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "invoiceCustomer", urlPatterns = {"/invoiceCustomer"})
public class invoiceCustomer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InvoiceDAO invDAO = new InvoiceDAO();
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customerAccount");
        if (cus != null) {
            List<Invoice> listInvoice = invDAO.getAllInvoice();
            List<Invoice> newList = new ArrayList<>();
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String status = request.getParameter("status");
            if (from != null && !from.isEmpty() && to != null && !to.isEmpty() && (status != null && !status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByStatusandDate(from, to, status);
                request.setAttribute("from", from);
                request.setAttribute("to", to);
                request.setAttribute("status", status);
            }
            if (from != null && !from.isEmpty() && to != null && !to.isEmpty() && (status == null || status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByBetweenDate(from, to);
                request.setAttribute("from", from);
                request.setAttribute("to", to);
            }
            if ((from == null || from.isEmpty()) && to != null && !to.isEmpty() && (status != null && !status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByStatusandToDate(to, status);
                request.setAttribute("to", to);
                request.setAttribute("status", status);
            }
            if (from != null && !from.isEmpty() && (to == null || to.isEmpty()) && (status != null && !status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByStatusandFromDate(from, status);
                request.setAttribute("from", from);
                request.setAttribute("status", status);
            }
            if ((from == null || from.isEmpty()) && (to == null || to.isEmpty()) && (status != null && !status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByStatus(status);
                request.setAttribute("status", status);
            }
            if ((from == null || from.isEmpty()) && to != null && !to.isEmpty() && (status == null || status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByToDate(to);
                request.setAttribute("to", to);
            }
            if (from != null && !from.isEmpty() && (to == null || to.isEmpty()) && (status == null || status.isEmpty())) {
                listInvoice = invDAO.getInvoiceByFromDate(from);
                request.setAttribute("from", from);
            }
            ValidFunction valid = new ValidFunction();
            int i = 0;
            for (Invoice in : listInvoice) {
                if (in.getExaminationID().getCustomerId().getCustomerID() == cus.getCustomerID()) {
                    in.setCreatedAt(valid.formatDateNews(in.getCreatedAt()));
                    if (in.getPaymentDate() != null) {
                        in.setPaymentDate(valid.formatDateNews(in.getPaymentDate()));
                    }
                    i++;
                    newList.add(in);
                }
            }
            request.setAttribute("listInvoice", newList);
            request.getRequestDispatcher("invoiceCustomer.jsp").forward(request, response);
        } else {
            response.sendRedirect("index_1.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
