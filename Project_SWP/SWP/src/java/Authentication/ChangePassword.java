package Authentication;

import util.ValidFunction;
import dao.CustomerDAO;
import dao.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import model.Staff;

@WebServlet(name = "ChangePassword", urlPatterns = {"/changePassword"})
public class ChangePassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customerAccount");
        Staff staff = (Staff) session.getAttribute("staffAccount");
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_new_password");

        if (customer == null && staff == null) {
            request.setAttribute("error", "You must login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else if (staff != null) {
            StaffDAO staffDAO = new StaffDAO();
            int staffID = staff.getStaffID();
            ValidFunction valid = new ValidFunction();
            if (!staffDAO.checkOldPassword(staffID, oldPassword)) {
                request.setAttribute("error", "Old Password is not correct");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            if (newPassword == null || newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "The new password doesn't match or is empty!");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }
            if (!valid.isValidPassword(newPassword)) {
                request.setAttribute("error", "Password must contains at least 8 character and consist digit, special character, uppercase character");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            staffDAO.changeStaffPassword(staffID, newPassword);

            staff.setPassword(newPassword);

            response.sendRedirect("profile.jsp");

        } else {
            CustomerDAO dao = new CustomerDAO();
            int customerID = customer.getCustomerID();
            ValidFunction valid = new ValidFunction();


            String hashedOldPassword = dao.getCustomerHashedPassword(customerID);

            if (hashedOldPassword == null || !valid.checkPassword(oldPassword, hashedOldPassword)) {
                request.setAttribute("error", "Old Password is not correct");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            if (newPassword == null || newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "The new password doesn't match or is empty!");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }

            if (!valid.isValidPassword(newPassword)) {
                request.setAttribute("error", "Password must contain at least 8 characters, including digits, special characters, and uppercase letters.");
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                return;
            }


            String hashedNewPassword = valid.hashPassword(newPassword);
            dao.changeCustomerPassword(customerID, hashedNewPassword);


            customer.setPassword(hashedNewPassword);

            response.sendRedirect("profile.jsp");

        }

    }
}
