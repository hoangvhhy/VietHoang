/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DiscountController;

import util.ValidFunction;
import dao.DiscountDAO;
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
@WebServlet(name = "addDiscount", urlPatterns = {"/addDiscount"})
public class addDiscount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String discountName = request.getParameter("discountName");
        String percentage_raw = request.getParameter("percentage");
        String status = request.getParameter("status");
        ValidFunction valid = new ValidFunction();
        if(valid.containsSpecialCharacter(discountName)){
            request.setAttribute("error", "Name can not contains special character");
            request.getRequestDispatcher("add-discount.jsp").forward(request, response);
            return;
        }
        try {
            double percentage = Double.parseDouble(percentage_raw);
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Percentage must be between 0 and 100");
            }else{
                DiscountDAO disDAO = new DiscountDAO();
                disDAO.addDiscount(valid.normalizeName(discountName), percentage, status);
                request.setAttribute("mess", "Add discount successfully");
            request.getRequestDispatcher("add-discount.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please enter a valid number");
            request.getRequestDispatcher("add-discount.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("add-discount.jsp").forward(request, response);
        }
    }

}
