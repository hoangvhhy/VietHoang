package serviceController;

import util.ValidFunction;
import dao.ServiceDAO;
import java.io.IOException;
import java.io.InputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "Update_Service", urlPatterns = {"/updateservice"})
@MultipartConfig(maxFileSize = 16177215) // Giới hạn ảnh tối đa ~16MB
public class Update_Service extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        ValidFunction val = new ValidFunction();
        try {
            // Lấy thông tin từ request
            int packageID = Integer.parseInt(request.getParameter("packageID"));
            String packageName = request.getParameter("packageName");
            packageName = val.normalizeName(packageName);
            String description = request.getParameter("description");
            description = val.normalizeName(description);
            String type = request.getParameter("type");
            double price = Double.parseDouble(request.getParameter("price"));
            int duration = Integer.parseInt(request.getParameter("duration"));

            // Lấy file ảnh (nếu có)
            Part filePart = request.getPart("service_image");
            InputStream imageStream = (filePart != null && filePart.getSize() > 0) ? filePart.getInputStream() : null;

            // Cập nhật vào database
            ServiceDAO dao = new ServiceDAO();
            dao.updateService(packageID, packageName, description, imageStream, type, price, duration);

            // Chuyển hướng về trang quản lý dịch vụ
            response.sendRedirect("loadmanage");

        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating service details";
    }
}
