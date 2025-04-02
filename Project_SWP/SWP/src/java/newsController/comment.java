/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newsController;

import dao.CommentDAO;
import dao.CustomerDAO;
import dao.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Comment;
import model.Customer;
import model.Staff;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "comment", urlPatterns = {"/comment"})
public class comment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = request.getParameter("content");
        String postIdStr = request.getParameter("newsID");
        String customerIdStr = request.getParameter("customerId");
        String staffIdStr = request.getParameter("staffId");
        String parentCommentIdStr = request.getParameter("parent_comment_id");

        try {
            if (postIdStr == null || postIdStr.isEmpty() || (customerIdStr == null || customerIdStr.isEmpty()) && (staffIdStr == null || staffIdStr.isEmpty())) {
                response.getWriter().println("Post ID, Customer ID, or Staff ID is missing.");
                return;
            }

            int postId = Integer.parseInt(postIdStr);
            int parentCommentId = (parentCommentIdStr != null && !parentCommentIdStr.isEmpty()) ? Integer.parseInt(parentCommentIdStr) : 0;

            Customer customer = null;
            Staff staff = null;

            if (customerIdStr != null && !customerIdStr.isEmpty()) {
                int customerId = Integer.parseInt(customerIdStr);
                CustomerDAO customerDAO = new CustomerDAO();
                customer = customerDAO.getCustomerByID(customerId);
            }

            if (staffIdStr != null && !staffIdStr.isEmpty()) {
                int staffId = Integer.parseInt(staffIdStr);
                StaffDAO staffDAO = new StaffDAO();
                staff = staffDAO.getStaffByID(staffId);
            }

            if (customer != null || staff != null) {
                Comment comment = new Comment();
                comment.setPost_id(postId);
                comment.setCustomerID(customer);
                comment.setStaff_id(staff);
                comment.setContent(content.trim());
                comment.setParent_comment_id(parentCommentId);
                comment.setStatus(1);

                CommentDAO dao = new CommentDAO();

                if (parentCommentId != 0) {
                    Comment parentComment = dao.getCommentById(parentCommentId);
                    if (parentComment != null) {
                        request.setAttribute("parent_comment_name", parentComment.getCustomerID() != null
                                ? parentComment.getCustomerID().getFullName()
                                : parentComment.getStaff_id().getFullName());
                    }
                }
                boolean isAdded = dao.addComment(comment);
                if (isAdded) {
                    response.sendRedirect("detailNews?newsID=" + postId + "&commentSuccess=true");
                } else {
                    request.setAttribute("errorMessage", "Unable to add your comment. Please try again.");
                    request.getRequestDispatcher("detailNews?newsID=" + postId).forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Invalid customer or staff. Please login.");
                request.getRequestDispatcher("detailNews?newsID=" + postId).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input: Post ID, Customer ID, or Staff ID is not valid.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
