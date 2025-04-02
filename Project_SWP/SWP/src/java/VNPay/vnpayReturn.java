/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package VNPay;

import util.ValidFunction;
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
 * @author Gigabyte
 */
@WebServlet(name = "vnpayReturn", urlPatterns = {"/vnpayReturn"})
public class vnpayReturn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceID = request.getParameter(("vnp_TxnRef")).substring(8);
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        InvoiceDAO invDAO = new InvoiceDAO();
         ValidFunction valid = new ValidFunction();
        if ("00".equals(vnp_ResponseCode)) {
            invDAO.updateInvoiceOnline(Integer.parseInt(invoiceID));
            response.sendRedirect("loadfeedback?invoiceID=" + invoiceID + "&success=" + valid.hashPassword(vnp_ResponseCode));
        }else{
            response.sendRedirect("viewInvoiceCustomer?invoiceID="+ invoiceID + "&success=" + valid.hashPassword(vnp_ResponseCode));
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
