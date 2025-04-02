package CategoryController;

import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;




@WebServlet(name = "AddCategory", urlPatterns = {"/addCategory"})

public class AddCategory extends HttpServlet {
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to the add category page when GET request is received
        request.getRequestDispatcher("/add-category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get form parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String statusStr = request.getParameter("status");
        
        // Convert status to integer (1 for active, 0 for inactive)
        int status = "option1".equals(statusStr) ? 1 : 0;

        // Create new category object
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);

        // Add category to database
        try {
            categoryDAO.addCategory(category);
            // Set success message
            request.setAttribute("message", "Category added successfully!");
            // Redirect to category list or back to form with success message
            response.sendRedirect("categoryList"); // Assuming you have a category list servlet
        } catch (Exception e) {
            // Set error message
            request.setAttribute("error", "Error adding category: " + e.getMessage());
            request.getRequestDispatcher("/add-category.jsp").forward(request, response);
        }
    }
}