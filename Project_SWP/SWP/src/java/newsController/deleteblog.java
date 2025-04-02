package newsController;

import dao.NewsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="deleteblog", urlPatterns={"/deleteblog"})
public class deleteblog extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NewsDAO blogDAO = new NewsDAO();

    // Phương thức xử lý yêu cầu POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("postId");

        if (idParam != null) {
            int postId = Integer.parseInt(idParam);
            try {
                blogDAO.deleteBlogPost(postId);
                response.sendRedirect("homeblogseverlet?message=Deleted Successfully");
            } catch (Exception e) {
                // Nếu có lỗi, chuyển hướng với thông báo lỗi
                response.sendRedirect("homeblogseverlet?message=Deletion Failed: " + e.getMessage());
            }
        } else {
            response.sendRedirect("blogList.jsp?message=Invalid Post ID");
        }
    }

    @Override
    public String getServletInfo() {
        return "Delete blog post servlet";
    }
}
