package newsController;

import dao.CategoryDAO;
import dao.NewsDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.News;

/**
 * 
 * @author ADMIN
 */
@WebServlet(name = "HomeBlogSeverlet", urlPatterns = {"/homeblogseverlet"})
public class HomeBlogSeverlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        NewsDAO dao = new NewsDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        int page = 1;
        int recordsPerPage = 8;

        // Lấy tham số "page"
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Lấy tham số category_id
        int categoryId = -1;
        String categoryParam = request.getParameter("category");
        if (categoryParam != null && !categoryParam.isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryParam);
            } catch (NumberFormatException e) {
                categoryId = -1;
            }
        }

        // Lấy danh sách blog theo danh mục và phân trang
        List<News> blogs;
        int totalRecords;
        if (categoryId != -1) {
            blogs = dao.getBlogsByCategoryAndPage(categoryId, (page - 1) * recordsPerPage, recordsPerPage);
            totalRecords = dao.getTotalBlogCountByCategory(categoryId);
        } else {
            blogs = dao.getBlogsByPage((page - 1) * recordsPerPage, recordsPerPage);
            totalRecords = dao.getTotalBlogCount();
        }

        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        if (totalRecords == 0) {
            totalPages = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        
        List<Category> categoryList = categoryDAO.getAllCategories();

        // Set attributes cho JSP
        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("selectedCategory", categoryId);
        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("blog.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách blog có phân trang và lọc theo danh mục";
    }
}
