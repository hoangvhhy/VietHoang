/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package InvoiceController;

import util.ValidFunction;
import dao.InvoiceDAO;
import dao.MedicalExaminationDAO;
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
@WebServlet(name = "viewInvoice", urlPatterns = {"/viewInvoice"})
public class viewInvoice extends HttpServlet {

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
        request.setAttribute("createdDate", valid.formatDateInvoice(inv.getCreatedAt()));
        request.setAttribute("subtotal", total);
        request.setAttribute("discount", total - inv.getTotalAmount());
        request.setAttribute("invoice", inv);
        request.getRequestDispatcher("invoice-view.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
