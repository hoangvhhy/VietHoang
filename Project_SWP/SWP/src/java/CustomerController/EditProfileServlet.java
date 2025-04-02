package CustomerController;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Customer;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)

@WebServlet(name = "EditProfileServlet", urlPatterns = {"/editprofile"})
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        


        Part filePart = request.getPart("profileImage");
        InputStream imageStream = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileType = filePart.getContentType();
            List<String> allowedTypes = Arrays.asList("image/png", "image/jpeg", "image/gif");

            if (!allowedTypes.contains(fileType)) {
                request.setAttribute("error", "Only PNG, JPEG, and GIF files are allowed!");
                request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
                return;
            }

            imageStream = filePart.getInputStream();
        }

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName").replaceAll("\\s+", " ").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String address = request.getParameter("address").replaceAll("\\s+", " ").trim();
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            request.setAttribute("error", "Invalid email format! (ex: example@gmail.com)");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("^0[0-9]{9}$")) {
            request.setAttribute("error", "Invalid phone number! Must be 10 digits and start with 0.");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Customer customerProfile = (Customer) session.getAttribute("customerAccount");

        if (customerProfile == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int customerID = customerProfile.getCustomerID();
        CustomerDAO dao = new CustomerDAO();

        try {
            dao.updateCustomerProfile(fullName, email, phone, address, dateOfBirth, gender, imageStream, customerID);
        } catch (Exception ex) {
            Logger.getLogger(EditProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Cập nhật thông tin trong session
        customerProfile.setFullName(fullName);
        customerProfile.setEmail(email);
        customerProfile.setPhone(phone);
        customerProfile.setAddress(address);
        customerProfile.setDateOfBirth(dateOfBirth);
        customerProfile.setGender(gender);

        session.setAttribute("customerAccount", customerProfile);
        response.sendRedirect("profile.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing user profile.";
    }
}
