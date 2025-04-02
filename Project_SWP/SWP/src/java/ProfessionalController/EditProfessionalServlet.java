package ProfessionalController;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import model.Professional;
import util.FileUploadHelper;

/**
 *
 * @author Win11
 */


@WebServlet(name = "EditProfessionalServlet", urlPatterns = {"/EditProfessionalServlet"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class EditProfessionalServlet extends HttpServlet {

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
            out.println("<title>Servlet EditProfessionalServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditProfessionalServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProfessionalDAO pdao = new ProfessionalDAO();
        Professional pro = pdao.getProfessionalbyID(id);
        HttpSession session = request.getSession();
        session.setAttribute("pro", pro);
        request.getRequestDispatcher("edit-doctor.jsp").forward(request, response);
    }


    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int staffID = Integer.parseInt(request.getParameter("id"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String status = request.getParameter("status");
        String specialization = request.getParameter("specialization");
        String officeHours = request.getParameter("officeHours");
        String qualification = request.getParameter("qualification");
        String biography = request.getParameter("biography");
        String dateOfBirth = request.getParameter("dateOfBirth");
        boolean success=false;
         ProfessionalDAO professionalDAO = new ProfessionalDAO();
         int roleID = professionalDAO.getRoleIDByName(specialization);
if (roleID == -1) {
    request.setAttribute("errorMessage", "Lỗi: Không tìm thấy Role ID cho chuyên môn này!");
    request.getRequestDispatcher("edit-doctor.jsp").forward(request, response);
    return;
}
        try {
            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                  // Kiểm tra định dạng file
            if (!isValidImageFile(extractFileName(filePart))) {
                    request.setAttribute("errorMessage", "Chỉ được tải lên file ảnh có định dạng JPG, JPEG, PNG, GIF, WEBP!");
                request.getRequestDispatcher("edit-doctor.jsp").forward(request, response);
                return;
            }
                String imagePath = FileUploadHelper.saveProfilePicture(filePart); // Lưu file
              
        //nt staffID, String fullName, String email, String password, Date dateOfBirth, String gender, String address, String phone, Date hireDate, String status, byte[] profilePicture, String specialization, String officeHours, String qualification, String biography, Date createdA
        Professional professional = new Professional(staffID, fullName, email, "", Date.valueOf(dateOfBirth), 
    null, address, phone, new Date(System.currentTimeMillis()), status,
    imagePath.getBytes(), specialization, officeHours, qualification, biography, 
    new Date(System.currentTimeMillis()),roleID );
         success = professionalDAO.updateProfessional(professional);

            // Lấy danh sách cập nhật và lưu vào session
            HttpSession session = request.getSession();
            session.setAttribute("professionals", professionalDAO.getAllProfessionals());
                response.getWriter().write("Upload thành công: " + imagePath);
                } else {
                    response.getWriter().write("Vui lòng chọn ảnh!");
                }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Lỗi upload file: " + e.getMessage());
        }
        
        List<Professional> list = professionalDAO.getAllProfessionals();
        HttpSession session = request.getSession();
        if (success) {
             session.setAttribute("specializations", professionalDAO.getallSpecialization());
            session.setAttribute("professionals", list);
            response.sendRedirect("manage-doctor.jsp");
        } else {
            request.setAttribute("errorMessage", "Update failed!");
            request.getRequestDispatcher("edit-doctor.jsp").forward(request, response);
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

    private byte[] processProfilePicture(Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }
        try (InputStream inputStream = filePart.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }
private boolean isValidImageFile(String fileName) {
    String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
    String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    
    return Arrays.asList(allowedExtensions).contains(fileExtension);
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

