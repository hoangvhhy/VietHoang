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
import model.Discount;

/**
 *
 * @author Gigabyte
 */
@WebServlet(name = "editDiscount", urlPatterns = {"/editDiscount"})
public class editDiscount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DiscountDAO disDAO = new DiscountDAO();
        String discountID = request.getParameter("discountID");
        Discount dis = disDAO.getDiscountByID(Integer.parseInt(discountID));
        request.setAttribute("discount", dis);
        request.getRequestDispatcher("edit-discount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String discountID = request.getParameter("discountID");
        String discountName = request.getParameter("discountName");
        String percentage_raw = request.getParameter("percentage");
        String status = request.getParameter("status");
        ValidFunction valid = new ValidFunction();
        DiscountDAO disDAO = new DiscountDAO();
        Discount dis = disDAO.getDiscountByID(Integer.parseInt(discountID));
        if (valid.containsSpecialCharacter(discountName)) {
            request.setAttribute("error", "Name can not contains special character");
            request.setAttribute("discount", dis);
            request.getRequestDispatcher("edit-discount.jsp").forward(request, response);
            return;
        } else {
            dis.setDiscountName(discountName);
        }
        request.setAttribute("discount", dis);
        try {
            double percentage = Double.parseDouble(percentage_raw);
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Percentage must be between 0 and 100");
            } else {
                request.removeAttribute("discount");

                disDAO.updateDiscount(Integer.parseInt(discountID), valid.normalizeName(discountName), percentage, status);
                request.setAttribute("mess", "Edit discount successfully");
                request.getRequestDispatcher("edit-discount.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please enter a valid number");
            request.getRequestDispatcher("edit-discount.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("edit-discount.jsp").forward(request, response);
        }
    }

}
