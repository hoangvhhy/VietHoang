/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package InvoiceController;

import util.ValidFunction;
import dao.InvoiceDAO;
import dao.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Invoice;
import model.Permission;
import model.Role;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "invoice", urlPatterns = {"/invoice"})
public class invoice extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet invoice</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet invoice at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        ValidFunction valid = new ValidFunction();
        List<Invoice> listInvoice = invoiceDAO.getAllInvoice();
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String status = request.getParameter("status");
        if(from != null && !from.isEmpty() && to != null && !to.isEmpty()  && ( status != null && !status.isEmpty())){
            listInvoice = invoiceDAO.getInvoiceByStatusandDate(from, to, status);
            request.setAttribute("from", from);
            request.setAttribute("to", to);
            request.setAttribute("status", status);
        }
        if(from != null&& !from.isEmpty() && to != null && !to.isEmpty() && (  status == null || status.isEmpty())){
            listInvoice = invoiceDAO.getInvoiceByBetweenDate(from, to);
            request.setAttribute("from", from);
            request.setAttribute("to", to);
        }
        if((from == null || from.isEmpty()) && to != null && !to.isEmpty()  && ( status != null && !status.isEmpty() )){
            listInvoice = invoiceDAO.getInvoiceByStatusandToDate(to, status);
            request.setAttribute("to", to);
            request.setAttribute("status", status);
        }
        if(from != null && !from.isEmpty() && (to == null || to.isEmpty())   && ( status != null && !status.isEmpty() )){
            listInvoice = invoiceDAO.getInvoiceByStatusandFromDate(from, status);
            request.setAttribute("from", from);
            request.setAttribute("status", status);
        }
        if((from == null || from.isEmpty()) && (to == null || to.isEmpty())   && ( status != null && !status.isEmpty() )){
            listInvoice = invoiceDAO.getInvoiceByStatus(status);
            request.setAttribute("status", status);
        }
        if((from == null || from.isEmpty()) && to != null && !to.isEmpty() && ( status == null || status.isEmpty())){
            listInvoice = invoiceDAO.getInvoiceByToDate(to);
            request.setAttribute("to", to);
        }
        if(from != null && !from.isEmpty() && (to == null || to.isEmpty())   && ( status == null || status.isEmpty()  )){
            listInvoice = invoiceDAO.getInvoiceByFromDate(from);
            request.setAttribute("from", from);
        }
        double totalAmount = 0;
        for(Invoice in : listInvoice){
            totalAmount += in.getTotalAmount();
        }
        request.setAttribute("totalAmount", totalAmount);
        int page = 1;
        int recordsPerPage = 5;

        int totalRecords = listInvoice.size();
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                // Ngăn truy cập trang không hợp lệ
                if (page < 1) {
                    page = 1;
                }
                if (page > totalPages) {
                    page = totalPages;
                }
            } catch (NumberFormatException e) {
                page = 1; // Nếu nhập sai định dạng, về trang đầu
            }
        }
        int start = (page - 1) * recordsPerPage;
        List<Invoice> invoices = invoiceDAO.getInvoices(start, recordsPerPage);
        if(from != null && !from.isEmpty() && to != null && !to.isEmpty()  && ( status != null && !status.isEmpty())){
            invoices = invoiceDAO.getInvoiceByStatusandDatePaging(from, to, status,start,recordsPerPage);
        }
        if(from != null&& !from.isEmpty() && to != null && !to.isEmpty() && (  status == null || status.isEmpty() )){
            invoices = invoiceDAO.getInvoiceByBetweenDatePaging(from, to,start,recordsPerPage);
        }
        if((from == null || from.isEmpty()) && to != null && !to.isEmpty() && ( status != null && !status.isEmpty())){
            invoices = invoiceDAO.getInvoiceByStatusandToDatePaging(to, status,start,recordsPerPage);
        }
        if(from != null && !from.isEmpty() && (to == null || to.isEmpty())   && ( status != null && !status.isEmpty() )){
            invoices = invoiceDAO.getInvoiceByStatusandFromDatePaging(from, status,start,recordsPerPage);
        }
        if((from == null || from.isEmpty())  && (to == null || to.isEmpty()) && ( status != null && !status.isEmpty() )){
            invoices = invoiceDAO.getInvoiceByStatusPaging(status,start,recordsPerPage);
        }
        if((from == null || from.isEmpty()) && to != null && !to.isEmpty() && ( status == null || status.isEmpty())){
            invoices = invoiceDAO.getInvoiceByToDatePaging(to,start,recordsPerPage);
        }
        if(from != null && !from.isEmpty() && (to == null || to.isEmpty())  && ( status == null || status.isEmpty()  )){
            invoices = invoiceDAO.getInvoiceByFromDatePaging(from,start,recordsPerPage);
        }
        int i = 0;
        for (Invoice inv : invoices) {

            invoices.get(i).setCreatedAt(valid.formatDateNews(inv.getCreatedAt()));
            if (inv.getPaymentDate() != null) {
                invoices.get(i).setPaymentDate(valid.formatDateNews(inv.getPaymentDate()));
            }
            i++;

        }
        
        
        RoleDAO roleDAO = new RoleDAO();
        HttpSession session = request.getSession();
        if (session.getAttribute("staffAccount") != null) {
            Staff s = (Staff) session.getAttribute("staffAccount");
            Role role = roleDAO.getRoleByID(s.getRoleID());
            List<Permission> listPermission = role.getPermission();
            request.setAttribute("listPermission", listPermission);
        }
        request.setAttribute("listInvoice", invoices);
        request.setAttribute("show", invoices.size());
        request.setAttribute("size", listInvoice.size());
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("invoices.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
