package newsController;

import dao.NewsDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.News;

@WebServlet(name = "Search_block", urlPatterns = {"/Search_block"})
public class Search_block extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String keyword = request.getParameter("keyword");
        
        // Check if keyword is null, empty, or only whitespace
        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a search keyword.");
            request.getRequestDispatcher("blog.jsp").forward(request, response);
            return;
        }

        // Trim whitespace from the keyword
        keyword = keyword.trim();

        // Additional validation
        // Check minimum length (e.g., at least 2 characters)
        if (keyword.length() < 2) {
            request.setAttribute("error", "Search keyword must be at least 2 characters long.");
            request.getRequestDispatcher("blog.jsp").forward(request, response);
            return;
        }

        // Check if keyword contains only whitespace or special characters
        if (keyword.matches("^\\s+$") || keyword.matches("[!@#$%^&*()_+={}\\[\\]|\\\\:;\"'<>,.?/]+")) {
            request.setAttribute("error", "Search keyword cannot consist only of spaces or special characters.");
            request.getRequestDispatcher("blog.jsp").forward(request, response);
            return;
        }

        // Optional: Limit maximum length to prevent overly long queries
        if (keyword.length() > 50) {
            request.setAttribute("error", "Search keyword cannot exceed 50 characters.");
            request.getRequestDispatcher("blog.jsp").forward(request, response);
            return;
        }

        NewsDAO blogDAO = new NewsDAO();
        List<News> searchResults = blogDAO.searchBlogs(keyword);
        
        // Optional: Check if no results were found
        if (searchResults == null || searchResults.isEmpty()) {
            request.setAttribute("message", "No results found for '" + keyword + "'.");
        }
        
        request.setAttribute("blogs", searchResults);
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
        return "Servlet for searching blog posts";
    }
}