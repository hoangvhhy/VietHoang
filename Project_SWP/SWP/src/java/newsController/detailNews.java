
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newsController;

import util.ValidFunction;
import dao.CommentDAO;
import dao.NewsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Comment;
import model.News;

/**
 *
 * @author Hoang
 */
@WebServlet(name = "detailNews", urlPatterns = {"/detailNews"})
public class detailNews extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ValidFunction valid = new ValidFunction();
        NewsDAO dao = new NewsDAO();
        CommentDAO commentDAO = new CommentDAO();
        String newsIdStr = request.getParameter("newsID");
        String parentCommentIdStr = request.getParameter("parent_comment_id");

        if (newsIdStr == null || newsIdStr.isEmpty()) {
            response.sendRedirect("allNews");
            return;
        }

        int newsID = Integer.parseInt(newsIdStr);
        News news = dao.getNewsByID(newsID);

        if (news == null) {
            response.sendRedirect("allNews");
            return;
        }

        List<Comment> comments = commentDAO.getCommentsByPostId(newsID);
        List<Category> listCate = dao.getAllCategoryNews();

        for (Comment comment : comments) {
            comment.setCreate_at(valid.formatDateNews(comment.getCreate_at()));
        }
        news.setCreated_at(valid.formatDateNews(news.getCreated_at()));
        news.setUpdated_at(valid.formatDateTime(news.getUpdated_at(), "dd/MM/yyyy HH:mm"));

        int parentCommentId = 0;

        try {
            if (parentCommentIdStr != null && !parentCommentIdStr.isEmpty()) {
                parentCommentId = Integer.parseInt(parentCommentIdStr);
                Comment parentComment = commentDAO.getCommentById(parentCommentId);
                if (parentComment != null) {
                    if (parentComment.getCustomerID() != null) {
                        request.setAttribute("parent_comment_name", parentComment.getCustomerID().getFullName());
                    } else {
                        request.setAttribute("parent_comment_name", parentComment.getStaff_id().getFullName());
                    }
                    request.setAttribute("parent_comment_id", parentCommentId);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error_message", "Invalid comment ID format.");
        } catch (Exception e) {
            request.setAttribute("error_message", "An error occurred while fetching the comment.");
        }
        request.setAttribute("newsDetail", news);
        request.setAttribute("listCate", listCate);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("detail-news.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
