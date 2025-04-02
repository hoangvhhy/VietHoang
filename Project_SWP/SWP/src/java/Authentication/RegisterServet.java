/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Authentication;

import util.ValidFunction;
import dao.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author jaxbo
 */
public class RegisterServet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ValidFunction valid = new ValidFunction();
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String address = request.getParameter("address");
        String dateOfBirthStr = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //dinh dang ngay thang theo yyyy-MM-dd
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, format); //chuyen doi tu Str sang LocalDate
            LocalDate today = LocalDate.now(); // lay ngay hien tai

            if (dateOfBirth.isAfter(today)) {
                request.setAttribute("error", "Date of Birth cannot be in the future!");
                setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            //bat dien het thong tin
            if (username.isEmpty() || fullname.isEmpty() || email.isEmpty() || phone.isEmpty()
                    || password.isEmpty() || confirm_password.isEmpty() || address.isEmpty() || dateOfBirthStr.isEmpty() || gender.isEmpty()) {
                request.setAttribute("error", "Please enter all fields !");
                setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            //sdt bat dau tu so khong, bat buoc 10 so
//            if (!phone.matches("^0\\d{9}$")) {
//                request.setAttribute("error", "Invalid phone number !");
//                request.getRequestDispatcher("register.jsp").forward(request, response);
//                return;
//            }
            //mat khau lon hon 8 ky tu, bao gom chu in hoa, ky tu dac biet
            String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$";
            if (!password.matches(passwordPattern)) {
                request.setAttribute("error", "Passwords must be at least 8 characters, including uppercase letters, numbers, and special characters !");
                setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            if (!password.equals(confirm_password)) {
                request.setAttribute("error", "Passwords do not match !");
                setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                CustomerDAO dao = new CustomerDAO();
                boolean usernameExists = dao.isUsernameExist(username);
                boolean emailExists = dao.isEmailExist(email);
                boolean phoneNumberExists = dao.isPhoneNumberExist(phone);
                if (usernameExists) {
                    request.setAttribute("error", "Username already exists!");
                    setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else if (emailExists) {
                    request.setAttribute("error", "Email already exists!");
                    setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else if (phoneNumberExists) {
                    request.setAttribute("error", "Phone number already exists!");
                    setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else {
                    String hashedPassword = valid.hashPassword(password);
                    dao.customerSignup(username, hashedPassword, fullname, email, phone, address, dateOfBirthStr, gender);
                    response.sendRedirect("login.jsp");
                }
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Nhap ngay sinh theo dinh dang YYYY-MM-DD.");
            setFormAttributes(request, username, fullname, email, phone, address, dateOfBirthStr, gender);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private void setFormAttributes(HttpServletRequest request, String username, String fullname, String email, String phone, String address, String dateOfBirthStr, String gender) {
        request.setAttribute("username", username);
        request.setAttribute("fullname", fullname);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        request.setAttribute("dateOfBirth", dateOfBirthStr);
        request.setAttribute("gender", gender);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
