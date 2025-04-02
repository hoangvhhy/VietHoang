/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package StaffController;

import util.ValidFunction;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import model.Staff;

/**
 *
 * @author Gigabyte
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "profileStaff", urlPatterns = {"/profileStaff"})
public class profileStaff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        String fullName = request.getParameter("fullName");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        StaffDAO staffDAO = new StaffDAO();
        ValidFunction valid = new ValidFunction();
        if (valid.containsDigitOrSpecialChar(fullName) || fullName.isEmpty()) {
            request.setAttribute("error", "Name cannot contain digit or special character");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
            return;
        }
        if (!valid.isValidPhoneNumber(phone)) {
            request.setAttribute("error", "Phone number is not exist, please check again");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        Staff s = (Staff) session.getAttribute("staffAccount");
        session.removeAttribute("staffAccount");

        staffDAO.updateStaffInfo(s.getStaffID(), valid.normalizeName(fullName), email, phone, dateOfBirth, gender, address, imageStream);
        Staff staff = staffDAO.getStaffByID(s.getStaffID());
        Staff st = new Staff(staff.getStaffID(), staff.getFullName(), staff.getEmail(), staff.getPassword(), staff.getPhone(), staff.getGender(), valid.formatDate(staff.getDateOfBirth()), staff.getAddress(), valid.formatDate(staff.getHireDate()), staff.getRoleID(), staff.getStatus(), staff.getProfilePicture());
        session.setAttribute("staffAccount", st);
        request.setAttribute("mess", "Update staff succesfully");

        request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
    }

}
