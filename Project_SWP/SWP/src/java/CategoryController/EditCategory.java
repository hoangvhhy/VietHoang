package CategoryController;

import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(name = "EditCategory", urlPatterns = {"/editCategory"})

public class EditCategory extends HttpServlet {
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get category ID from request parameter
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            Category category = categoryDAO.getCategoryById(id);
            
            if (category != null) {
                request.setAttribute("category", category);
                request.getRequestDispatcher("/edit-category.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Category not found");
                response.sendRedirect("categoryList"); // Assuming you have a category list page
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid category ID");
            response.sendRedirect("categoryList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get form parameters
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String statusStr = request.getParameter("status");

        try {
            int id = Integer.parseInt(idStr);
            int status = "option1".equals(statusStr) ? 1 : 0;

            // Create category object with updated values
            Category category = new Category();
            category.setCategory_id(id);
            category.setName(name);
            category.setDescription(description);
            category.setStatus(status);

            // Update category in database
            categoryDAO.updateCategory(category);
            
            // Set success message and redirect
            request.getSession().setAttribute("message", "Category updated successfully!");
            response.sendRedirect("categoryList");
        } catch (Exception e) {
            request.setAttribute("error", "Error updating category: " + e.getMessage());
            request.setAttribute("category", categoryDAO.getCategoryById(Integer.parseInt(idStr)));
            request.getRequestDispatcher("/edit-category.jsp").forward(request, response);
        }
    }
}