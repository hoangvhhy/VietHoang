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
import java.util.List;
import model.Invoice;
import model.MedicalExamination;
import model.Service;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "viewInvoiceCustomer", urlPatterns = {"/viewInvoiceCustomer"})
public class viewInvoiceCustomer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceID = request.getParameter("invoiceID");
        InvoiceDAO invDAO = new InvoiceDAO();
        Invoice inv = invDAO.getInvoiceByID(Integer.parseInt(invoiceID));
        ValidFunction valid = new ValidFunction();
        double discount = 0;
        if (inv.getDiscountID() != null) {
            discount = inv.getDiscountID().getPercentage();
        }
        MedicalExamination med = inv.getExaminationID();
        double total = 0;
        List<Service> listService = med.getList();
        for (Service s : listService) {
            total += s.getPrice();
        }
        if (inv.getPaymentStatus().equals("Paid")) {
            request.setAttribute("mess", "The invoice has been paid.");
        }
        String success = request.getParameter("success");
        if (success != null ) {
            if (valid.checkPassword("00", success)) {
                request.setAttribute("success", "Payment invoice successfully");
            } else {
                request.setAttribute("success", "Payment invoice unsuccessfully");
            }
        }

        request.setAttribute("createdDate", valid.formatDateInvoice(inv.getCreatedAt()));
        request.setAttribute("subtotal", total);
        request.setAttribute("discount", total - inv.getTotalAmount());
        request.setAttribute("invoice", inv);
        request.getRequestDispatcher("invoice-view-customer.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
