/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package DashboardController;

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

/**
 *
 * @author Gigabyte
 */
@WebServlet(name="dashRevenue", urlPatterns={"/dashRevenue"})
public class dashRevenue extends HttpServlet {
   
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        InvoiceDAO inDAO = new InvoiceDAO();
        List<Invoice> list = inDAO.getAllInvoice();
        request.setAttribute("sumInvoice", list.size());
        double sum  = 0;
        int numberPaid = 0;
        for(Invoice in : list){
            if(in.getPaymentStatus().equals("Paid")){
                sum += in.getTotalAmount();
                numberPaid++;
            }
        }
        ValidFunction valid = new ValidFunction();
        List<Invoice> listInvoice = inDAO.getTop3Invoice();
        for(Invoice i : listInvoice){
            i.setCreatedAt(valid.formatDateNews(i.getCreatedAt()));
        }
        request.setAttribute("listInvoice", listInvoice);
        request.setAttribute("sumAmount", sum);
        request.setAttribute("numberPaid", numberPaid);
        request.setAttribute("numberPending", list.size()-numberPaid);
        request.getRequestDispatcher("dashboardRevenue.jsp").forward(request, response);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

   

}
