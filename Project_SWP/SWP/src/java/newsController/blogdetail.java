package newsController;

import dao.NewsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.News;

@WebServlet(name = "blogdetail", urlPatterns = {"/blogdetail"})
public class blogdetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String postIdParam = request.getParameter("postId");

    if (postIdParam != null) {
        try {
            int id = Integer.parseInt(postIdParam); // Chuyển đổi đúng cách
            NewsDAO dao = new NewsDAO();
            News blog = dao.getBlogById(id); // Đảm bảo tên phương thức đúng với BlogDAO

            if (blog != null) {
                request.setAttribute("blogdetail", blog);
                request.getRequestDispatcher("blog-detail.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.jsp"); // Nếu không tìm thấy bài viết
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("homeblogseverlet"); // Nếu postId không hợp lệ
        }
    } else {
        response.sendRedirect("homeblogseverlet"); // Nếu thiếu postId, quay về danh sách blog
    }
}


    @Override
    public String getServletInfo() {
        return "Blog Detail Servlet";
    }
}
