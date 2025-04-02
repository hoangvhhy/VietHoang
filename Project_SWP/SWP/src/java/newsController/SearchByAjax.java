/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newsController;

import dao.NewsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.News;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchByAjax", urlPatterns = {"/SearchByAjax"})
public class SearchByAjax extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            response.getWriter().write("");
            return;
        }

        NewsDAO dao = new NewsDAO();
        List<News> list = dao.searchBlogs(keyword);

        PrintWriter out = response.getWriter();
        if (list.isEmpty()) {
            out.println("<div class=\"col-12\">");
            out.println("    <p style=\"color: red;\">⚠️ No blogs found matching your search!</p>");
            out.println("</div>");
        } else {
            for (News o : list) {
                out.println("<div class=\"col-12\">");
                out.println("    <div class=\"blog-list-item\">");
                out.println("        <a href=\"blogdetail?postId=" + o.getPost_id() + "\">");
                out.println("            <img src=\"" + o.getImage() + "\" alt=\"" + o.getTitle() + "\">");
                out.println("        </a>");
                out.println("        <div class=\"blog-content\">");
                out.println("            <h3 class=\"blog-title\">");
                out.println("                <a href=\"blogdetail?postId=" + o.getPost_id() + "\">" + o.getTitle() + "</a>");
                out.println("            </h3>");
                out.println("            <p>" + o.getContent() + "</p>");
                out.println("            <a href=\"blogdetail?postId=" + o.getPost_id() + "\" class=\"read-more\">");
                out.println("                <i class=\"fa fa-long-arrow-right\"></i> Read More");
                out.println("            </a>");
                out.println("            <a href=\"editblog?postId=" + o.getPost_id() + "\" class=\"btn btn-warning btn-sm\">");
                out.println("                Update");
                out.println("            </a>");
                out.println("            <form action=\"deleteblog\" method=\"post\" style=\"display: inline;\" onsubmit=\"return confirm('Bạn có chắc chắn muốn xóa bài viết này?');\">");
                out.println("                <input type=\"hidden\" name=\"postId\" value=\"" + o.getPost_id() + "\">");
                out.println("                <button type=\"submit\" class=\"btn btn-danger btn-sm\">🗑 Xóa</button>");
                out.println("            </form>");
                out.println("        </div>");
                out.println("    </div>");
                out.println("</div>");
            }
        }
    }
}
