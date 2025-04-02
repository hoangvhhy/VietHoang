/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newsController;

import dao.CommentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Comment;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "EditComment", urlPatterns = {"/editComment"})
public class EditComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commentIdStr = request.getParameter("comment_id");
        String content = request.getParameter("content");
        if (commentIdStr == null || commentIdStr.isEmpty()) {
            response.getWriter().println("Comment ID is missing or invalid.");
            return;
        }

        int commentId = Integer.parseInt(commentIdStr);
        CommentDAO commentDAO = new CommentDAO();
        Comment comment = commentDAO.getCommentById(commentId);
        if (comment != null) {
            comment.setContent(content);
            boolean isUpdated = commentDAO.updateComment(comment);
            if (isUpdated) {
                response.sendRedirect("detailNews?newsID=" + comment.getPost_id() + "&commentSuccess=true");
            } else {
                response.sendRedirect("detailNews?newsID=" + comment.getPost_id() + "&commentSuccess=false");
            }
        } else {
            response.getWriter().println("Comment not found.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
