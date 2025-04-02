package CategoryController;

import dao.CategoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "DeleteCategory", urlPatterns = {"/deleteCategory"})

public class DeleteCategory extends HttpServlet {
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Optional: You could show a confirmation page here
        // For now, we'll redirect to the list if accessed directly
        response.sendRedirect("categoryList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get the category ID from the request
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Category ID is required");
            }

            int id = Integer.parseInt(idStr);
            
            // Delete the category
            categoryDAO.deleteCategory(id);
            
            // Set success message in session
            request.getSession().setAttribute("message", "Category deleted successfully!");
            
            // Redirect to category list
            response.sendRedirect("categoryList");
            
        } catch (NumberFormatException e) {
            // Handle invalid ID format
            request.getSession().setAttribute("error", "Invalid category ID format");
            response.sendRedirect("categoryList");
            
        } catch (Exception e) {
            // Handle other errors
            request.getSession().setAttribute("error", "Error deleting category: " + e.getMessage());
            response.sendRedirect("categoryList");
        }
    }
}