/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package InvoiceController;

import util.ValidFunction;
import dao.DiscountDAO;
import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Discount;
import model.Invoice;
import model.Service;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "editInvoice", urlPatterns = {"/editInvoice"})
public class editInvoice extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceID = request.getParameter("invoiceID");
        String discount = request.getParameter("discount");
        String paymentStatus = request.getParameter("paymentStatus");
        InvoiceDAO invDAO = new InvoiceDAO();
        String mess = request.getParameter("mes");
        if(mess != null){
            request.setAttribute("mess", mess);
        }
        if(discount != null && paymentStatus != null){
            if(discount.isEmpty()){
                    discount = null;
                }
            if(paymentStatus.equals("Pending")){
                invDAO.updateInvoiceDicount(Integer.parseInt(invoiceID), discount);
            }else{
                invDAO.updateInvoiceOffline(Integer.parseInt(invoiceID), discount);
            }
            String s = "Update succesfully";
            response.sendRedirect("editInvoice?invoiceID="+invoiceID+"&mes="+s);
        }
        
        DiscountDAO disDAO = new DiscountDAO();
        List<Discount> listDis = disDAO.getAllDiscountStatus();
        request.setAttribute("listDis", listDis);
        Invoice in = invDAO.getInvoiceByID(Integer.parseInt(invoiceID));
        String status = request.getParameter("status");
        if (status == null) {
            request.setAttribute("status", in.getPaymentStatus());
        } else {
            request.setAttribute("status", status);
        }
        ValidFunction valid = new ValidFunction();
        in.getExaminationID().getCustomerId().setDateOfBirth(valid.formatDateNews(in.getExaminationID().getCustomerId().getDateOfBirth()));
        request.setAttribute("invoice", in);
        double total = 0;
        for (Service s : in.getExaminationID().getList()) {
            total += s.getPrice();
        }
        request.setAttribute("total", total);
        request.setAttribute("discount", total - in.getTotalAmount());
        request.getRequestDispatcher("edit-invoice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceID = request.getParameter("invoiceID");
        InvoiceDAO invDAO = new InvoiceDAO();
        DiscountDAO disDAO = new DiscountDAO();
        List<Discount> listDis = disDAO.getAllDiscountStatus();
        request.setAttribute("listDis", listDis);
        String discount = request.getParameter("discount");
        Invoice in = invDAO.getInvoiceByID(Integer.parseInt(invoiceID));

        ValidFunction valid = new ValidFunction();
        in.getExaminationID().getCustomerId().setDateOfBirth(valid.formatDateNews(in.getExaminationID().getCustomerId().getDateOfBirth()));

        double total = 0;
        for (Service s : in.getExaminationID().getList()) {
            total += s.getPrice();
        }
        String status = request.getParameter("status");
        if (status == null) {
            request.setAttribute("status", in.getPaymentStatus());
        } else {
            request.setAttribute("status", status);
        }

        if (discount.equals("0")) {
            in.setDiscountID(null);
            in.setTotalAmount(total);
        }
        if (discount != null && !discount.equals("0")) {
            Discount d = disDAO.getDiscountByID(Integer.parseInt(discount));
            in.setDiscountID(d);
            in.setTotalAmount(total - total * d.getPercentage() / 100);
        }
        request.setAttribute("invoice", in);
        request.setAttribute("total", total);
        request.getRequestDispatcher("edit-invoice.jsp").forward(request, response);
    }

}
