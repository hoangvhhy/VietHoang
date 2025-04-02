package ProfessionalController;

import context.DBContext;
import dao.ProfessionalDAO;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.xml.bind.DatatypeConverter.parseDate;
import model.Professional;
import util.FileUploadHelper;
import util.ValidFunction;

/**
 *
 * @author Win11
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class AddProfessionalServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProfessionalServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProfessionalServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        ValidFunction valid = new ValidFunction();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String status = request.getParameter("status");
        String specialization = request.getParameter("specialization");
        String officeHours = request.getParameter("officeHours");
        String qualification = request.getParameter("qualification");
        String biography = request.getParameter("biography");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String hireDate = request.getParameter("hireDate");

        Date createdAt = new Date(System.currentTimeMillis());
        boolean success = false;
        ProfessionalDAO dbHelper = new ProfessionalDAO();

        int roleID = dbHelper.getRoleIDByName(specialization);
        if (roleID == -1) {
            request.setAttribute("errorMessage", "Lỗi: Không tìm thấy Role ID cho chuyên môn " + specialization);
            request.getRequestDispatcher("add-doctor.jsp").forward(request, response);
            return;
        }

        byte[] profilePicture = null;
        try {
            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                if (!isValidImageFile(extractFileName(filePart))) {
                    request.setAttribute("errorMessage", "Chỉ được tải lên file ảnh có định dạng JPG, JPEG, PNG, GIF, WEBP!");
                    request.getRequestDispatcher("add-doctor.jsp").forward(request, response);
                    return;
                }
                profilePicture = FileUploadHelper.saveProfilePicture(filePart).getBytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi upload file: " + e.getMessage());
            request.getRequestDispatcher("add-doctor.jsp").forward(request, response);
            return;
        }

        Professional newProfessional = new Professional(
                -1, fullName, email, valid.hashPassword(password),
                Date.valueOf(dateOfBirth), gender, address, phone,
                Date.valueOf(hireDate), status, profilePicture,
                specialization, officeHours, qualification, biography,
                createdAt, roleID
        );

        success = dbHelper.addProfessional(newProfessional);

        HttpSession session = request.getSession();
        session.setAttribute("specializations", dbHelper.getallSpecialization());
        session.setAttribute("professionals", dbHelper.getAllProfessionals());

        if (success) {
            response.sendRedirect("manage-doctor.jsp");
        } else {
            request.setAttribute("errorMessage", "Thêm bác sĩ thất bại!");
            request.getRequestDispatcher("add-doctor.jsp").forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "1.jpg";
    }

    public static boolean isValidImageFile(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return false; // Tên file không hợp lệ
        }

        String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webp"};

        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return false; // Không có phần mở rộng hoặc chỉ có dấu "."
        }

        String fileExtension = fileName.substring(lastDotIndex).toLowerCase();
        return Arrays.asList(allowedExtensions).contains(fileExtension);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding professionals";
    }
}
